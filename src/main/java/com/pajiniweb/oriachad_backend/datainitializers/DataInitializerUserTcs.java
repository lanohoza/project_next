//package com.pajiniweb.oriachad_backend.datainitializers;
//
//import com.pajiniweb.oriachad_backend.actions.domians.entities.Action;
//import com.pajiniweb.oriachad_backend.actions.domians.enums.ActionResultType;
//import com.pajiniweb.oriachad_backend.actions.domians.enums.ActionType;
//import com.pajiniweb.oriachad_backend.actions.repositories.ActionRepository;
//import com.pajiniweb.oriachad_backend.admin.domains.entities.AdminTechnicalCard;
//import com.pajiniweb.oriachad_backend.admin.repositories.AdminTechnicalCardRepository;
//import com.pajiniweb.oriachad_backend.domains.entities.*;
//import com.pajiniweb.oriachad_backend.repositories.*;
//import com.pajiniweb.oriachad_backend.settings.configuration.AppSettings;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component()
//@Order(2)
//@AllArgsConstructor
//@Transactional
//public class DataInitializerUserTcs implements ApplicationRunner {
//
//
//	private final AppSettings appSettings;
//	private final TechnicalCardRepository technicalCardRepository;
//	private final AdminTechnicalCardRepository adminTechnicalCardRepository;
//	private final UserRepository userRepository;
//	final GeneralObjectiveRepository generalObjectiveRepository;
//	final HumanToolRepository humanToolRepository;
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		List<OriachadUser> users = userRepository.findAll();
//		adminTechnicalCardRepository.findAll().forEach(adminTechnicalCard -> {
//			users.forEach(user -> {
//				if (!technicalCardRepository.existsByCodeAndIdCreatedBy(adminTechnicalCard.getCode(),user.getId())) {
//					TechnicalCard technicalCard = new TechnicalCard();
//					technicalCard.getActions().addAll(adminTechnicalCard.getActions());
//					technicalCard.setCreatedBy(user);
//					technicalCard.setDefaultTc(true);
//					technicalCard.getAudiences().addAll(adminTechnicalCard.getAudiences());
//					technicalCard.setFeedback(adminTechnicalCard.getFeedback());
//					technicalCard.getDifficulties().addAll(adminTechnicalCard.getDifficulties());
//					if (!adminTechnicalCard.getGeneralObjectives().isEmpty()) {
//						Set<GeneralObjective> generalObjectives = adminTechnicalCard.getGeneralObjectives().stream().map(adminGeneralObjective -> {
//							// Check if a GeneralObjective with the same content already exists
//							GeneralObjective existingGeneralObjective = generalObjectiveRepository.findByContentAndIdCreatedBy(adminGeneralObjective.getContent(), user.getId()).orElseGet(() -> {
//								// If not found, create and save a new one
//								GeneralObjective newGeneralObjective = new GeneralObjective();
//								newGeneralObjective.setContent(adminGeneralObjective.getContent());
//								newGeneralObjective.setCreatedBy(user);
//								newGeneralObjective.setOperateObjectives(adminGeneralObjective.getOperateObjectives().stream().map(operateObjective -> OperateObjective.builder().content(operateObjective.getContent()).generalObjective(newGeneralObjective).createdBy(user).build()).toList());
//								// Set the creator to the current user
//								return generalObjectiveRepository.save(newGeneralObjective);
//							});
//							return existingGeneralObjective;
//						}).collect(Collectors.toSet());
//						technicalCard.setGeneralObjectives(generalObjectives);
//					}
//					technicalCard.getHumanTools().addAll(adminTechnicalCard.getHumanTools());
//					if (!adminTechnicalCard.getHumanTools().isEmpty()) {
//						Set<HumanTool> humanTools = adminTechnicalCard.getHumanTools().stream().map(adminGeneralObjective -> {
//							// Check if a GeneralObjective with the same content already exists
//							HumanTool existinghumanTool = humanToolRepository.findByFirstNameAndLastNameAndIdCreatedBy(adminGeneralObjective.getFirstName(), adminGeneralObjective.getLastName(), user.getId()).orElseGet(() -> {
//								// If not found, create and save a new one
//								HumanTool newHumanTool = new HumanTool();
//								newHumanTool.setFirstName(adminGeneralObjective.getFirstName());
//								newHumanTool.setLastName(adminGeneralObjective.getLastName());
//								newHumanTool.setAdresse(adminGeneralObjective.getAdresse());
//								newHumanTool.setCreatedBy(user);
//								return humanToolRepository.save(newHumanTool);
//							});
//							return existinghumanTool;
//						}).collect(Collectors.toSet());
//						technicalCard.setHumanTools(humanTools);
//					}
//					technicalCard.setMaterielToots(adminTechnicalCard.getMaterielToots());
//					technicalCard.getOfficialTxts().addAll(adminTechnicalCard.getOfficialTxts());
//					technicalCard.setRunMonth(adminTechnicalCard.getRunMonth());
//					technicalCard.setRunWeek(adminTechnicalCard.getRunWeek());
//					technicalCard.setType(adminTechnicalCard.getType());
//					technicalCard.setTitle(adminTechnicalCard.getTitle());
//					technicalCard.setCategory(adminTechnicalCard.getCategory());
//					technicalCard.setCode(adminTechnicalCard.getCode());
//					technicalCard.setNumber(adminTechnicalCard.getNumber());
//					technicalCardRepository.save(technicalCard);
//				}
//			});
//		});
//	}
//
//
//}
