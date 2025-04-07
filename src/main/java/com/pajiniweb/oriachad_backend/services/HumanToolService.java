package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.HumanToolDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.HumanTool;
import com.pajiniweb.oriachad_backend.domains.entities.TcHumanTool;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.HumanToolRepository;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomAdminDetails;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HumanToolService {

	private static final Logger log = LoggerFactory.getLogger(HumanToolService.class);

	@Autowired
	private HumanToolRepository humanToolRepository;


	@Autowired
	UserService userService;

	public Page<HumanToolDto> getAllHumanTools(Pageable pageable) {
		log.info(Messages.START_FUNCTION, "getAllHumanTools");
		try {
			Page<HumanTool> humanTools = humanToolRepository.findAll(pageable);
			log.info(Messages.ENTITY_IS_FOUND, "HumanTools");
			return humanTools.map(this::toDto);
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "getAllHumanTools", e.getMessage());
			return Page.empty();
		}
	}

	public HumanToolDto getHumanToolById(Long id) {
		log.info(Messages.START_FUNCTION, "getHumanToolById");
		try {
			Optional<HumanTool> humanTool = humanToolRepository.findById(id);
			if (humanTool.isPresent()) {
				log.info(Messages.ENTITY_IS_FOUND, "HumanTool");
				return toDto(humanTool.get());
			} else {
				log.warn(Messages.ENTITY_IS_NOT_FOUND, "HumanTool");
				return null;
			}
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "getHumanToolById", e.getMessage());
			return null;
		}
	}

	public HumanToolDto createHumanTool(HumanToolDto humanToolDTO) {
		log.info(Messages.START_FUNCTION, "createHumanTool");
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());


			HumanTool humanTool = toEntity(humanToolDTO);
			humanTool.setCreatedBy(userDetails.getOriachadUser());

			HumanTool savedHumanTool = humanToolRepository.save(humanTool);
			log.info(Messages.PROCESS_SUCCESSFULLY, "createHumanTool");
			return toDto(savedHumanTool);
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "createHumanTool", e.getMessage());
			return null;
		}
	}

	public HumanToolDto updateHumanTool(Long id, HumanToolDto humanToolDTO) {
		log.info(Messages.START_FUNCTION, "updateHumanTool");
		try {
			UserDto createdBy = userService.getUserById(humanToolDTO.getCreatedBy());
			OriachadUser user = userService.mapUserDtoToEntity(createdBy);
			if (humanToolRepository.existsById(id)) {
				HumanTool humanTool = new HumanTool();
				humanTool.setId(id);
				humanTool.setFirstName(humanToolDTO.getFirstName());
				humanTool.setLastName(humanToolDTO.getLastName());
				humanTool.setAdresse(humanToolDTO.getAdresse());
				humanTool.setCreatedBy(user);
				HumanTool updatedHumanTool = humanToolRepository.save(humanTool);
				log.info(Messages.PROCESS_SUCCESSFULLY, "updateHumanTool");
				return toDto(updatedHumanTool);
			} else {
				log.warn(Messages.ENTITY_IS_NOT_FOUND, "HumanTool");
				return null;
			}
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "updateHumanTool because :" + e.getMessage());
			return null;
		}
	}

	public boolean deleteHumanTool(Long id) {
		log.info(Messages.START_FUNCTION, "deleteHumanTool");
		try {
			List<TcHumanTool> tcHumanToolList = null;//humanToolsTechnicalCardService.findByIdHumainTools(id);
			if (tcHumanToolList == null) {
				humanToolRepository.deleteById(id);
				log.info(Messages.PROCESS_SUCCESSFULLY, "deleteHumanTool");
				return true;
			} else {
				log.warn(Messages.DELETE_FAILED, "because this HumanTool is attached to task");
				return false;
			}
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "deleteHumanTool", e.getMessage());
			return false;
		}
	}

	public Page<HumanToolDto> getHumanToolsByCreatedBy(Pageable pageable) {
		log.info(Messages.START_FUNCTION, "getHumanToolsByCreatedBy");
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
			//            UserDTO userDTO = userService.findUserByToken(token);
			Page<HumanTool> humanTools = humanToolRepository.findByCreatedBy(userDetails.getIdUser(), pageable);
			log.info(Messages.PROCESS_SUCCESSFULLY, "getHumanToolsByCreatedBy");
			return humanTools.map(this::toDto);
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "getHumanToolsByCreatedBy", e);
			throw e;
		}
	}

	public HumanToolDto toDto(HumanTool humanTool) {
		return HumanToolDto.builder().id(humanTool.getId())
				.firstName(humanTool.getFirstName())
				.lastName(humanTool.getLastName())
				.adresse(humanTool.getAdresse())
				.createdBy(humanTool.getCreatedBy().getId())
				.source(humanTool.getSource())
				.build();
	}

	public HumanToolDto toDtoAdmin(HumanTool humanTool) {
		return HumanToolDto.builder().id(humanTool.getId())
				.firstName(humanTool.getFirstName())
				.lastName(humanTool.getLastName())
				.adresse(humanTool.getAdresse())
				.idAdmin(humanTool.getCreatedByAdmin().getId())
				.source(humanTool.getSource())
				.build();
	}

	public HumanTool toEntity(HumanToolDto humanToolDTO) {
		HumanTool humanTool = new HumanTool();
		humanTool.setId(humanToolDTO.getId());
		humanTool.setFirstName(humanToolDTO.getFirstName());
		humanTool.setLastName(humanToolDTO.getLastName());
		humanTool.setAdresse(humanToolDTO.getAdresse());
		humanTool.setSource(humanToolDTO.getSource());
		return humanTool;
	}

	public List<HumanToolDto> getAllHumanToolsByCreatedBy() {
		log.info(Messages.START_FUNCTION, "getAllHumanToolsByCreatedBy");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		return humanToolRepository.findByCreatedBy(userDetails.getIdUser()).stream().map(this::toDto).toList();
	}

	// Created By ALI #ADMINISTRATION
	public List<HumanToolDto> getAllHumanToolsCreatedByAdmin() {
		log.info(Messages.START_FUNCTION, "getAllHumanToolsCreatedByAdmin");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomAdminDetails adminDetails = ((CustomAdminDetails) auth.getPrincipal());
		return humanToolRepository.findCreatedByAdmin(adminDetails.getIdAdmin()).stream().map(this::toDtoAdmin).toList();
	}

	public boolean save(HumanToolDto humanToolDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		HumanTool humanTool = HumanTool.builder()
				.adresse(humanToolDTO.getAdresse())
				.lastName(humanToolDTO.getLastName())
				.firstName(humanToolDTO.getFirstName())
				.id(humanToolDTO.getId())
				.createdBy(userDetails.getOriachadUser())
				.source(SourceTechnicalCard.user)
				.build();
		humanToolRepository.save(humanTool);
		return true;
	}

	// Created By Ali #Administration
	public boolean saveFromAdmin(HumanToolDto humanToolDTO) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		CustomAdminDetails adminDetails = ((CustomAdminDetails) auth.getPrincipal());
		HumanTool humanTool = HumanTool.builder()
				.adresse(humanToolDTO.getAdresse())
				.lastName(humanToolDTO.getLastName())
				.firstName(humanToolDTO.getFirstName())
				.id(humanToolDTO.getId())
				.createdByAdmin(adminDetails.getOriachadAdmin())
				.source(SourceTechnicalCard.admin)
				.build();
		humanToolRepository.save(humanTool);
		return true;
	}
}
