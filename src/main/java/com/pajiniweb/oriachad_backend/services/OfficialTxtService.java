package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.OfficialTxtDto;
import com.pajiniweb.oriachad_backend.domains.entities.OfficialTxt;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.OfficialTxtRepository;
import com.pajiniweb.oriachad_backend.repositories.OfficielTextCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficialTxtService {

	private static final Logger log = LoggerFactory.getLogger(OfficialTxtService.class);

	@Autowired
	OfficialTxtRepository officialTxtRepository;

	@Autowired
	OfficielTextCategoryRepository officielTextCategoryRepository;

	// Convert OfficialTxt entity to DTO
	private OfficialTxtDto toDto(OfficialTxt officialTxt) {
		return OfficialTxtDto.builder()
				.id(officialTxt.getId())
				.number(officialTxt.getNumber())
				.title(officialTxt.getTitle())
				.date(officialTxt.getDate())
				.idOfficielTextCategory(officialTxt.getIdOfficielTextCategory())
				.build();
	}

	// Convert DTO to OfficialTxt entity
	private OfficialTxt toEntity(OfficialTxtDto officialTxtDto) {
		OfficialTxt officialTxt = new OfficialTxt();
		officialTxt.setId(officialTxtDto.getId());
		officialTxt.setNumber(officialTxtDto.getNumber());
		officialTxt.setTitle(officialTxtDto.getTitle());
		officialTxt.setDate(officialTxtDto.getDate());
		officialTxt.setOfficielTextCategory(officielTextCategoryRepository.getReferenceById(officialTxtDto.getIdOfficielTextCategory()));
		return officialTxt;
	}

	public List<OfficialTxtDto> getAllOfficialTxts() {
		log.info(Messages.START_FUNCTION, "getAllOfficialTxts");
		return officialTxtRepository.findAll()
				.stream().map(this::toDto)
				.toList();
	}

	public Optional<OfficialTxtDto> getOfficialTxtById(Long id) {
		log.info(Messages.START_FUNCTION, "getOfficialTxtById");
		try {
			return officialTxtRepository.findById(id).map(this::toDto);
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "getOfficialTxtById", e.getMessage());
			throw e;
		}
	}

	public OfficialTxtDto createOfficialTxt(OfficialTxtDto officialTxtDto) {
		log.info(Messages.START_FUNCTION, "createOfficialTxt");
		try {
			OfficialTxt officialTxt = toEntity(officialTxtDto);
			OfficialTxt savedOfficialTxt = officialTxtRepository.save(officialTxt);
			log.info(Messages.PROCESS_SUCCESSFULLY, "createOfficialTxt");
			return toDto(savedOfficialTxt);
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "createOfficialTxt", e.getMessage());
			throw e;
		}
	}

	public OfficialTxtDto updateOfficialTxt(Long id, OfficialTxtDto officialTxtDto) {
		log.info(Messages.START_FUNCTION, "updateOfficialTxt");
		try {
			return officialTxtRepository.findById(id).map(existingOfficialTxt -> {
				existingOfficialTxt.setNumber(officialTxtDto.getNumber());
				existingOfficialTxt.setTitle(officialTxtDto.getTitle());
				existingOfficialTxt.setDate(officialTxtDto.getDate());
				existingOfficialTxt.setOfficielTextCategory(officielTextCategoryRepository.getReferenceById(officialTxtDto.getIdOfficielTextCategory()));
				OfficialTxt updatedOfficialTxt = officialTxtRepository.save(existingOfficialTxt);
				log.info(Messages.PROCESS_SUCCESSFULLY, "updateOfficialTxt");
				return toDto(updatedOfficialTxt);
			}).orElseThrow(() -> {
				log.warn(Messages.ENTITY_IS_NOT_FOUND, "OfficialTxt");
				return new RuntimeException(Messages.ENTITY_IS_NOT_FOUND);
			});
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "updateOfficialTxt", e.getMessage());
			throw e;
		}
	}

	public boolean deleteOfficialTxt(Long id) {
		log.info(Messages.START_FUNCTION, "deleteOfficialTxt");
		try {
			if (officialTxtRepository.existsById(id)) {
				officialTxtRepository.deleteById(id);
				log.info(Messages.DELETE_SUCCESSFULLY);
				return true;
			} else {
				log.warn(Messages.ENTITY_IS_NOT_FOUND, "OfficialTxt");
				throw new RuntimeException(Messages.ENTITY_IS_NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "deleteOfficialTxt", e.getMessage());
			throw e;
		}
	}
}
