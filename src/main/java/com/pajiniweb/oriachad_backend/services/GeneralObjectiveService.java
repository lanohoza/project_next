package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.administration.domains.entities.Admin;
import com.pajiniweb.oriachad_backend.domains.dtos.GeneralObjectiveDto;
import com.pajiniweb.oriachad_backend.domains.dtos.UserDto;
import com.pajiniweb.oriachad_backend.domains.entities.OperateObjective;
import com.pajiniweb.oriachad_backend.domains.entities.TcGeneralObjective;
import com.pajiniweb.oriachad_backend.domains.entities.GeneralObjective;
import com.pajiniweb.oriachad_backend.domains.entities.OriachadUser;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.GeneralObjectiveRepository;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomAdminDetails;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneralObjectiveService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralObjectiveService.class);

	@Autowired
	GeneralObjectiveRepository generalObjectiveRepository;


	@Autowired
	UserService userService;

	public GeneralObjectiveDto toDTO(GeneralObjective generalObjective) {
		return GeneralObjectiveDto.builder().id(generalObjective.getId())
				.content(generalObjective.getContent())
				.source(generalObjective.getSource())
				.createdById(generalObjective.getIdCreatedBy())
				.childItems(generalObjective.getOperateObjectives().stream().map(operateObjective -> GeneralObjectiveDto.OperateObjectiveDto.builder().id(operateObjective.getId()).content(operateObjective.getContent()).source(operateObjective.getSource()).build()).toList()).build();
	}

	public GeneralObjectiveDto toDTOAdmin(GeneralObjective generalObjective) {
		return GeneralObjectiveDto.builder().id(generalObjective.getId())
				.content(generalObjective.getContent())
				.source(generalObjective.getSource())
				.createdById(generalObjective.getIdAdmin())
				.childItems(generalObjective.getOperateObjectives().stream().map(operateObjective -> GeneralObjectiveDto.OperateObjectiveDto.builder().id(operateObjective.getId()).content(operateObjective.getContent()).source(operateObjective.getSource()).build()).toList()).build();
	}


	public Page<GeneralObjectiveDto> findAll(Pageable pageable) {
		LOGGER.info(Messages.START_FUNCTION, "findAll");
		try {
			Page<GeneralObjective> objectives = generalObjectiveRepository.findAll(pageable);
			LOGGER.info(Messages.ENTITY_IS_FOUND, "GeneralObjective");
			return objectives.map(this::toDTO);
		} catch (Exception e) {
			LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "GeneralObjective", e.getMessage());
			throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
		}
	}


	public boolean save(GeneralObjectiveDto generalObjectiveDTO) {
		LOGGER.info(Messages.START_FUNCTION, "save");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		OriachadUser user = userDetails.getOriachadUser();

		GeneralObjective generalObjective = GeneralObjective.builder().content(generalObjectiveDTO.getContent())
				.id(generalObjectiveDTO.getId())
				.createdBy(user)
				.source(SourceTechnicalCard.user)
				.build();
		if (generalObjectiveDTO.getChildItems() != null)
			generalObjective.setOperateObjectives(generalObjectiveDTO.getChildItems().stream().map(child -> OperateObjective.builder()
					.content(child.getContent())
					.id(child.getId())
					.createdBy(user)
					.generalObjective(generalObjective)
					.source(SourceTechnicalCard.user)
					.build()).toList());
		generalObjectiveRepository.save(generalObjective);
		LOGGER.info(Messages.PROCESS_SUCCESSFULLY, "GeneralObjective");
		return true;
	}

	// Created By Ali #Administration
	public boolean saveFromAdmin(GeneralObjectiveDto generalObjectiveDTO) {
		LOGGER.info(Messages.START_FUNCTION, "saveFromAdmin");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomAdminDetails adminDetails = ((CustomAdminDetails) auth.getPrincipal());
		Admin admin = adminDetails.getOriachadAdmin();

		GeneralObjective generalObjective = GeneralObjective.builder().content(generalObjectiveDTO.getContent())
				.id(generalObjectiveDTO.getId())
				.createdByAdmin(admin)
				.source(SourceTechnicalCard.admin)
				.build();
		if (generalObjectiveDTO.getChildItems() != null)
			generalObjective.setOperateObjectives(generalObjectiveDTO.getChildItems().stream().map(child -> OperateObjective.builder()
					.content(child.getContent())
					.id(child.getId())
					.createdByAdmin(admin)
					.generalObjective(generalObjective)
					.source(SourceTechnicalCard.admin)
					.build()).toList());
		generalObjectiveRepository.save(generalObjective);
		LOGGER.info(Messages.PROCESS_SUCCESSFULLY, "GeneralObjective");
		return true;
	}


	public boolean deleteById(Long id) throws Exception {
		LOGGER.info(Messages.START_FUNCTION, "deleteById");
		if (generalObjectiveRepository.existsWithTechnicalCard(id)) {
			throw new Exception("لا يمكن حذف الهدف العام لانه مرتبط بطاقة تقنية أخرى");
		}
		generalObjectiveRepository.deleteById(id);
		return true;
	}

	public Page<GeneralObjectiveDto> findByCreatedBy(Pageable pageable) {
		LOGGER.info(Messages.START_FUNCTION, "findByCreatedBy");
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
			//            UserDTO userDTO = userService.findUserByToken(token);
			Page<GeneralObjective> objectives = generalObjectiveRepository.findByCreatedBy(userDetails.getIdUser(), pageable);
			LOGGER.info(Messages.ENTITY_IS_FOUND, "GeneralObjective");
			return objectives.map(this::toDTO);
		} catch (Exception e) {
			LOGGER.error(Messages.ENTITY_IS_NOT_FOUND, "GeneralObjective", e.getMessage());
			throw new RuntimeException(Messages.PROCESS_IS_NOT_DONE);
		}
	}

	public List<GeneralObjectiveDto> findByCreatedBy() {
		LOGGER.info(Messages.START_FUNCTION, "findByCreatedBy");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		return generalObjectiveRepository.findByCreatedBy(userDetails.getIdUser()).stream().map(this::toDTO).toList();

	}

	// Created by ALI #ADMINISTRATION
	public List<GeneralObjectiveDto> findCreatedByAdmin() {
		LOGGER.info(Messages.START_FUNCTION, "findCreatedByAdmin");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomAdminDetails adminDetails = ((CustomAdminDetails) auth.getPrincipal());
		return generalObjectiveRepository.findCreatedByAdmin(adminDetails.getIdAdmin()).stream().map(this::toDTOAdmin).toList();

	}
}
