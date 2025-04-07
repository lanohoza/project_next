package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.EstablishmentDto;
import com.pajiniweb.oriachad_backend.domains.entities.Commune;
import com.pajiniweb.oriachad_backend.domains.entities.Establishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.CommuneRepository;
import com.pajiniweb.oriachad_backend.repositories.EstablishmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstablishmentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EstablishmentService.class);

	@Autowired
	private EstablishmentRepository establishmentRepository;

	@Autowired
	private CommuneRepository communeRepository;

	public List<EstablishmentDto> findAll() {
		LOGGER.info(Messages.START_FUNCTION, "findAll Establishment");
		try {
			List<Establishment> etablissements = establishmentRepository.findAll();
			return etablissements.stream().map(this::convertToDTO).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "Establishment", e.getMessage());
			throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
		}
	}

	public EstablishmentDto findById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "findById Establishment");
		try {
			Optional<Establishment> etablissementOptional = establishmentRepository.findById(id);
			Establishment etablissement = etablissementOptional.orElseThrow(() -> new RuntimeException("Establishment not found with id: " + id));
			return convertToDTO(etablissement);
		} catch (Exception e) {
			LOGGER.error("Error fetching etablissement by id", e);
			throw e;
		}
	}

	public List<EstablishmentDto> findByIdCommune(Long idCommune) {
		LOGGER.info(Messages.START_FUNCTION, "findByIdCommune Establishment");
		try {
			List<Establishment> etablissements = establishmentRepository.findByIdCommune(idCommune);
			return etablissements.stream().map(this::convertToDTO).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error("Error fetching etablissements by idCommune", e);
			throw e;
		}
	}

	public EstablishmentDto save(EstablishmentDto etablissementDTO) {
		LOGGER.info(Messages.START_FUNCTION, "save Establishment");
		try {
			Establishment etablissement = convertToEntity(etablissementDTO);
			Establishment savedEstablishment = establishmentRepository.save(etablissement);
			return convertToDTO(savedEstablishment);
		} catch (Exception e) {
			LOGGER.error("Error saving etablissement", e);
			throw e;
		}
	}

	public EstablishmentDto update(Long id, EstablishmentDto etablissementDTO) {
		LOGGER.info(Messages.START_FUNCTION, "update Establishment");
		try {
			if (!establishmentRepository.existsById(id)) {
				LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "Establishment");
				throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "Establishment"));
			}
			Establishment etablissementToUpdate = convertToEntity(etablissementDTO);
			etablissementToUpdate.setId(id);
			Establishment updatedEstablishment = establishmentRepository.save(etablissementToUpdate);
			return convertToDTO(updatedEstablishment);
		} catch (Exception e) {
			LOGGER.error("Error updating etablissement", e);
			throw e;
		}
	}

	public boolean deleteById(Long id) {
		LOGGER.info(Messages.START_FUNCTION, "deleteById Establishment");
		try {
			if (establishmentRepository.existsById(id)) {
				establishmentRepository.deleteById(id);
				LOGGER.info(Messages.DELETE_SUCCESSFULLY, "Establishment");
				return true;
			} else {
				LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "Establishment");
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("Error deleting etablissement", e);
			throw e;
		}
	}

	private EstablishmentDto convertToDTO(Establishment etablissement) {
		return EstablishmentDto.builder().id(etablissement.getId()).name(etablissement.getName()).faxNumber(etablissement.getFaxNumber()).adresse(etablissement.getAdresse()).idCommune(etablissement.getIdCommune()).mobileNumber(etablissement.getMobileNumber()).nameAdministrator(etablissement.getNameAdministrator()).facebook(etablissement.getFacebook()).youtube(etablissement.getYoutube()).regime(etablissement.getRegime()).type(etablissement.getType()).email(etablissement.getEmail()).build();
	}

	public Establishment convertToEntity(EstablishmentDto etablissementDTO) {
		Commune commune = communeRepository.findById(etablissementDTO.getIdCommune()).orElseThrow(() -> new IllegalArgumentException("Commune not found with id: " + etablissementDTO.getIdCommune()));

		return Establishment.builder().id(etablissementDTO.getId()).name(etablissementDTO.getName()).faxNumber(etablissementDTO.getFaxNumber()).adresse(etablissementDTO.getAdresse()).commune(commune).mobileNumber(etablissementDTO.getMobileNumber()).nameAdministrator(etablissementDTO.getNameAdministrator()).facebook(etablissementDTO.getFacebook()).youtube(etablissementDTO.getYoutube()).regime(etablissementDTO.getRegime()).type(etablissementDTO.getType()).email(etablissementDTO.getEmail()).build();
	}

	@Transactional
	public void saveEstablishments(List<Establishment> establishments) {
		for (Establishment establishment : establishments) {
			if (!establishmentRepository.existsByCode(establishment.getCode())) {
				String communeCode = establishment.getCode().substring(0, 4); // Extract the first 4 digits of the code
				Commune commune = communeRepository.findByCode(communeCode);
				if (commune != null) {
					establishment.setCommune(commune);
					establishmentRepository.save(establishment);
				} else {
					// Handle the case where the commune is not found
					System.out.println("Commune not found for code: " + communeCode);
				}
			}
		}
	}
}
