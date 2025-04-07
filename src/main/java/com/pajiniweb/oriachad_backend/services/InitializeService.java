package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.administration.repositories.AdminTechnicalCardRepository;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.repositories.GeneralObjectiveRepository;
import com.pajiniweb.oriachad_backend.repositories.HumanToolRepository;
import com.pajiniweb.oriachad_backend.repositories.TechnicalCardRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InitializeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(InitializeService.class);
	final AdminTechnicalCardRepository adminTechnicalCardRepository;
	final TechnicalCardRepository technicalCardRepository;
	final GeneralObjectiveRepository generalObjectiveRepository;
	final HumanToolRepository humanToolRepository;

	public void InitializeUserEnvironment(OriachadUser user) {
		InitializeUserTcs(user);
	}

	public void InitializeUserTcs(OriachadUser user) {
		adminTechnicalCardRepository.findByTypeEstablishment(user.getEstablishment().getType()).forEach(adminTechnicalCard -> {
			TechnicalCard technicalCard = new TechnicalCard();
			technicalCard.getActions().addAll(adminTechnicalCard.getActions());
			technicalCard.setCreatedBy(user);
			technicalCard.setDefaultTc(true);
			technicalCard.getAudiences().addAll(adminTechnicalCard.getAudiences());
			technicalCard.setFeedback(adminTechnicalCard.getFeedback());
			technicalCard.getDifficulties().addAll(adminTechnicalCard.getDifficulties());
			if (!adminTechnicalCard.getGeneralObjectives().isEmpty()) {
				Set<GeneralObjective> generalObjectives = adminTechnicalCard.getGeneralObjectives().stream().map(adminGeneralObjective -> {
					// Check if a GeneralObjective with the same content already exists
					GeneralObjective existingGeneralObjective = generalObjectiveRepository.findByContentAndIdCreatedBy(adminGeneralObjective.getContent(),user.getId()).orElseGet(() -> {
						// If not found, create and save a new one
						GeneralObjective newGeneralObjective = new GeneralObjective();
						newGeneralObjective.setContent(adminGeneralObjective.getContent());
						newGeneralObjective.setCreatedBy(user);
						newGeneralObjective.setOperateObjectives(adminGeneralObjective.getOperateObjectives().stream().map(operateObjective -> OperateObjective.builder().content(operateObjective.getContent()).generalObjective(newGeneralObjective).createdBy(user).build()).toList());
						// Set the creator to the current user
						return generalObjectiveRepository.save(newGeneralObjective);
					});
					return existingGeneralObjective;
				}).collect(Collectors.toSet());
				technicalCard.setGeneralObjectives(generalObjectives);
			}
			technicalCard.getHumanTools().addAll(adminTechnicalCard.getHumanTools());
			if (!adminTechnicalCard.getHumanTools().isEmpty()) {
				Set<HumanTool> humanTools = adminTechnicalCard.getHumanTools().stream().map(adminGeneralObjective -> {
					// Check if a GeneralObjective with the same content already exists
					HumanTool existinghumanTool = humanToolRepository.findByFirstNameAndLastNameAndIdCreatedBy(adminGeneralObjective.getFirstName(), adminGeneralObjective.getLastName(),user.getId()).orElseGet(() -> {
						// If not found, create and save a new one
						HumanTool newHumanTool = new HumanTool();
						newHumanTool.setFirstName(adminGeneralObjective.getFirstName());
						newHumanTool.setLastName(adminGeneralObjective.getLastName());
						newHumanTool.setAdresse(adminGeneralObjective.getAdresse());
						newHumanTool.setCreatedBy(user);
						return humanToolRepository.save(newHumanTool);
					});
					return existinghumanTool;
				}).collect(Collectors.toSet());
				technicalCard.setHumanTools(humanTools);
			}
			technicalCard.setMaterielToots(adminTechnicalCard.getMaterielToots());
			technicalCard.getOfficialTxts().addAll(adminTechnicalCard.getOfficialTxts());
			technicalCard.setRunMonth(adminTechnicalCard.getRunMonth());
			technicalCard.setRunWeek(adminTechnicalCard.getRunWeek());
			technicalCard.setType(adminTechnicalCard.getType());
			technicalCard.setTitle(adminTechnicalCard.getTitle());
			technicalCard.setCategory(adminTechnicalCard.getCategory());
			technicalCard.setCode(adminTechnicalCard.getCode());
			technicalCard.setNumber(adminTechnicalCard.getNumber());
			technicalCard.setTypeEstablishment(adminTechnicalCard.getTypeEstablishment());
			technicalCardRepository.save(technicalCard);
		});
	}
}
