package com.pajiniweb.oriachad_backend.administration.services;

import com.pajiniweb.oriachad_backend.administration.domains.entities.AdminTechnicalCard;
import com.pajiniweb.oriachad_backend.administration.repositories.AdminTechnicalCardRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditTechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.*;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomAdminDetails;
import com.pajiniweb.oriachad_backend.services.HelperService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TechnicalCardAdminService {

    private static final Logger log = LoggerFactory.getLogger(TechnicalCardAdminService.class);

    final AdminTechnicalCardRepository adminTechnicalCardRepository;
    final UserRepository userRepository;
    final TechnicalCardRepository technicalCardRepository;
    final HelperService helperService;
    final TechnicalCardCategoryRepository technicalCardCategoryRepository;
    final GeneralObjectiveRepository generalObjectiveRepository;
    final HumanToolRepository humanToolRepository;

    @Autowired
    public TechnicalCardAdminService(AdminTechnicalCardRepository technicalCardRepository, UserRepository userRepository, TechnicalCardRepository technicalCardRepository1, HelperService helperService, TechnicalCardCategoryRepository technicalCardCategoryRepository, GeneralObjectiveRepository generalObjectiveRepository, HumanToolRepository humanToolRepository) {
        this.adminTechnicalCardRepository = technicalCardRepository;
        this.userRepository = userRepository;
        this.technicalCardRepository = technicalCardRepository1;
        this.helperService = helperService;
        this.technicalCardCategoryRepository = technicalCardCategoryRepository;
        this.generalObjectiveRepository = generalObjectiveRepository;
        this.humanToolRepository = humanToolRepository;
    }

    // Created By Ali #Administration
    public boolean updateTechnicalCardFromAdmin(Long id, AddEditTechnicalCardDto addEditTechnicalCardDto) throws Exception {
        log.info(Messages.START_FUNCTION, "updateTechnicalCard");
        Optional<AdminTechnicalCard> technicalCardOptional = adminTechnicalCardRepository.findById(id);
        if (!technicalCardOptional.isPresent()) {
            throw new Exception("هاته البطاقة التقنية غير موجودة");
        }
        AdminTechnicalCard technicalCard = technicalCardOptional.get();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomAdminDetails adminDetails = ((CustomAdminDetails) auth.getPrincipal());

        technicalCard.setCategory(TechnicalCardCategory.builder().id(addEditTechnicalCardDto.getIdTcCategory()).build());
        technicalCard.setType(addEditTechnicalCardDto.getType());
        technicalCard.setTypeEstablishment(addEditTechnicalCardDto.getTypeEstablishment());
        technicalCard.setTitle(addEditTechnicalCardDto.getTitle());
        technicalCard.setMaterielToots(addEditTechnicalCardDto.getMaterielToots());
        technicalCard.setFeedback(addEditTechnicalCardDto.getFeedback());
        technicalCard.setImage(addEditTechnicalCardDto.getImage());
        technicalCard.setRunMonth(addEditTechnicalCardDto.getRunMonth());
        technicalCard.setRunWeek(addEditTechnicalCardDto.getRunWeek());
        technicalCard.setGeneralObjectives(addEditTechnicalCardDto.getGeneralObjectiveIds().stream().map((idGeneralObjective) -> GeneralObjective.builder().id(idGeneralObjective).build()).collect(Collectors.toSet()));
        technicalCard.setAudiences(addEditTechnicalCardDto.getAudienceIds().stream().map((idAudience) -> Audience.builder().id(idAudience).build()).collect(Collectors.toSet()));
        technicalCard.setHumanTools(addEditTechnicalCardDto.getHumanToolIds().stream().map((idHumanTool) -> HumanTool.builder().id(idHumanTool).build()).collect(Collectors.toSet()));
        technicalCard.setOfficialTxts(addEditTechnicalCardDto.getOfficialTxtIds().stream().map((idOfficialTxt) -> OfficialTxt.builder().id(idOfficialTxt).build()).collect(Collectors.toSet()));
        technicalCard.setDifficulties(addEditTechnicalCardDto.getDifficultyIds().stream().map((idDifficulty) -> Difficulty.builder().id(idDifficulty).build()).collect(Collectors.toSet()));

        adminTechnicalCardRepository.save(technicalCard);
        log.info(Messages.PROCESS_SUCCESSFULLY, "updateTechnicalCard");
        return true;
    }

    // Delete
    public boolean deleteTechnicalCard(Long id) throws Exception {
        log.info(Messages.START_FUNCTION, "deleteTechnicalCard");
        adminTechnicalCardRepository.deleteById(id);
        log.info(Messages.DELETE_SUCCESSFULLY, "task");
        return true;
    }

    // Read tasks by createdBy
    public Page<TechnicalCardDto> search(String search, Long idTcCategory, int month,TypeEstablishment typeEstablishment , int page, int size) {
        log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
        Page<AdminTechnicalCard> technicalCards = adminTechnicalCardRepository.findByCreatedBy(search, idTcCategory, month,typeEstablishment, pageable);
        return technicalCards.map(technicalCard -> TechnicalCardDto.builder().id(technicalCard.getId()).source(SourceTechnicalCard.admin).idTcCategory(technicalCard.getIdTcCategory()).code(technicalCard.getCode()).createDate(technicalCard.getCreateDate()).feedback(technicalCard.getFeedback()).id(technicalCard.getId()).title(technicalCard.getTitle()).runMonth(technicalCard.getRunMonth()).runWeek(technicalCard.getRunWeek()).type(technicalCard.getType()).build());
    }

    public TechnicalCardDto findTechnicalCardsById(Long id) throws Exception {
        log.info(Messages.START_FUNCTION, "findTechnicalCardsById");
        var technicalCardOptional = adminTechnicalCardRepository.findById(id);
        return technicalCardOptional.map(technicalCard -> TechnicalCardDto.builder().id(technicalCard.getId()).source(SourceTechnicalCard.admin).typeEstablishment(technicalCard.getTypeEstablishment()).idTcCategory(technicalCard.getIdTcCategory()).code(technicalCard.getCode()).createDate(technicalCard.getCreateDate()).feedback(technicalCard.getFeedback()).materielToots(technicalCard.getMaterielToots()).id(technicalCard.getId()).title(technicalCard.getTitle()).runMonth(technicalCard.getRunMonth()).runWeek(technicalCard.getRunWeek()).type(technicalCard.getType()).generalObjectiveIds(technicalCard.getGeneralObjectives().stream().map(GeneralObjective::getId).toList()).audienceIds(technicalCard.getAudiences().stream().map(Audience::getId).toList()).difficultyIds(technicalCard.getDifficulties().stream().map(Difficulty::getId).toList()).humanToolIds(technicalCard.getHumanTools().stream().map(HumanTool::getId).toList()).officialTxtIds(technicalCard.getOfficialTxts().stream().map(OfficialTxt::getId).toList()).build()).orElseThrow(() -> new Exception("not found technical card"));
    }

    // Created By Ali #ADMINISTRATION
    @Transactional
    public boolean createTechnicalCardFromAdmin(AddEditTechnicalCardDto addEditTechnicalCardDto) throws Exception {

        log.info("Start creating task with details: {}", addEditTechnicalCardDto);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomAdminDetails adminDetails = ((CustomAdminDetails) auth.getPrincipal());
        TechnicalCardCategory technicalCardCategory = technicalCardCategoryRepository.findById(addEditTechnicalCardDto.getIdTcCategory()).orElseThrow();
        Long number = adminTechnicalCardRepository.lastNumberOfTechnicalCard(technicalCardCategory.getId(), addEditTechnicalCardDto.getTypeEstablishment()).orElse(0L) + 1;

        String code = generateTechnicalCardCode(number, technicalCardCategory.getCode(), addEditTechnicalCardDto.getTypeEstablishment());
        // Create and save TcTechnicalCard entity
        AdminTechnicalCard technicalCard = new AdminTechnicalCard();
        technicalCard.setCategory(TechnicalCardCategory.builder().id(addEditTechnicalCardDto.getIdTcCategory()).build());
        technicalCard.setType(addEditTechnicalCardDto.getType());
        technicalCard.setTitle(addEditTechnicalCardDto.getTitle());
        technicalCard.setCode(code);
        technicalCard.setTypeEstablishment(addEditTechnicalCardDto.getTypeEstablishment());
        technicalCard.setNumber(number);
        technicalCard.setMaterielToots(addEditTechnicalCardDto.getMaterielToots());
        technicalCard.setFeedback(addEditTechnicalCardDto.getFeedback());
        technicalCard.setImage(addEditTechnicalCardDto.getImage());
        technicalCard.setRunMonth(addEditTechnicalCardDto.getRunMonth());
        technicalCard.setRunWeek(addEditTechnicalCardDto.getRunWeek());
        if (addEditTechnicalCardDto.getGeneralObjectiveIds() != null)
            technicalCard.setGeneralObjectives(addEditTechnicalCardDto.getGeneralObjectiveIds().stream().map((idGeneralObjective) -> GeneralObjective.builder().id(idGeneralObjective).build()).collect(Collectors.toSet()));
        if (addEditTechnicalCardDto.getAudienceIds() != null)
            technicalCard.setAudiences(addEditTechnicalCardDto.getAudienceIds().stream().map((idAudience) -> Audience.builder().id(idAudience).build()).collect(Collectors.toSet()));
        if (addEditTechnicalCardDto.getHumanToolIds() != null)
            technicalCard.setHumanTools(addEditTechnicalCardDto.getHumanToolIds().stream().map((idHumanTool) -> HumanTool.builder().id(idHumanTool).build()).collect(Collectors.toSet()));
        if (addEditTechnicalCardDto.getOfficialTxtIds() != null)
            technicalCard.setOfficialTxts(addEditTechnicalCardDto.getOfficialTxtIds().stream().map((idOfficialTxt) -> OfficialTxt.builder().id(idOfficialTxt).build()).collect(Collectors.toSet()));
        if (addEditTechnicalCardDto.getDifficultyIds() != null)
            technicalCard.setDifficulties(addEditTechnicalCardDto.getDifficultyIds().stream().map((idDifficulty) -> Difficulty.builder().id(idDifficulty).build()).collect(Collectors.toSet()));
        adminTechnicalCardRepository.save(technicalCard);


        return true;
    }

    private String generateTechnicalCardCode(Long number, String tcCategoryCode, @NotNull TypeEstablishment typeEstablishment) throws Exception {
        String typeEstablishmentCode;
        switch (typeEstablishment) {
            case primary:
                typeEstablishmentCode = "P";
                break;
            case secondary:
                typeEstablishmentCode = "S";
                break;
            case middle:
                typeEstablishmentCode = "M";
                break;
            default:
                typeEstablishmentCode = "A"; // Default case if no match
        }

        String formattedNumber = String.format("%03d", number);
        if (formattedNumber.length() > 3) {
            throw new IllegalArgumentException("Number length exceeds allowed format.");
        }

        return String.format("%s%s-%s", tcCategoryCode, typeEstablishmentCode, formattedNumber);
    }

    public List<AdminTechnicalCard> findAll() {
        try {
            log.info(Messages.START_FUNCTION, "find all AdminTechnicalCard");
            return adminTechnicalCardRepository.findAll();
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "find all AdminTechnicalCard because : " + e.getMessage());
        }
        return null;
    }

    @Transactional
    public boolean saveTcFromAdministrationToUser(Long idTechnicalCard) {

        AdminTechnicalCard adminTechnicalCard = adminTechnicalCardRepository.findById(idTechnicalCard).get();

        List<OriachadUser> users;

        if (adminTechnicalCard.getTypeEstablishment() == TypeEstablishment.all) {
            users = userRepository.findAll();
        } else {
            users = userRepository.getAllByEstablishmentType(adminTechnicalCard.getTypeEstablishment());
        }
        users.forEach(user -> {
            if (!technicalCardRepository.existsByCodeAndIdCreatedBy(adminTechnicalCard.getCode(), user.getId())) {
                TechnicalCard technicalCard = new TechnicalCard();

                // Initialize actions if null
                if (adminTechnicalCard.getActions() != null && !adminTechnicalCard.getActions().isEmpty()) {
                    technicalCard.getActions().addAll(adminTechnicalCard.getActions());
                }

                // Set createdBy and defaultTc
                technicalCard.setCreatedBy(user);
                technicalCard.setDefaultTc(true);

                // Initialize audiences if null
                if (adminTechnicalCard.getAudiences() != null) {
                    technicalCard.setAudiences(new HashSet<>());
                    technicalCard.getAudiences().addAll(adminTechnicalCard.getAudiences());
                }

                // Set feedback
                technicalCard.setFeedback(adminTechnicalCard.getFeedback());

                // Initialize difficulties if null
                if (adminTechnicalCard.getDifficulties() != null) {
                    if (technicalCard.getDifficulties() == null) {
                        technicalCard.setDifficulties(new HashSet<>());
                    }
                    technicalCard.getDifficulties().addAll(adminTechnicalCard.getDifficulties());
                }

                // Initialize generalObjectives if null
                if (adminTechnicalCard.getGeneralObjectives() != null && !adminTechnicalCard.getGeneralObjectives().isEmpty()) {
                    Set<GeneralObjective> generalObjectives = adminTechnicalCard.getGeneralObjectives().stream().map(adminGeneralObjective -> {
                        // Check if a GeneralObjective with the same content already exists
                        GeneralObjective existingGeneralObjective = generalObjectiveRepository.findByContentAndIdCreatedBy(adminGeneralObjective.getContent(), user.getId()).orElseGet(() -> {
                            // If not found, create and save a new one
                            GeneralObjective newGeneralObjective = new GeneralObjective();
                            newGeneralObjective.setContent(adminGeneralObjective.getContent());
                            newGeneralObjective.setCreatedBy(user);
                            newGeneralObjective.setSource(SourceTechnicalCard.user);
                            newGeneralObjective.setOperateObjectives(adminGeneralObjective.getOperateObjectives().stream().map(operateObjective -> OperateObjective.builder().content(operateObjective.getContent()).generalObjective(newGeneralObjective).createdBy(user).build()).toList());
                            return generalObjectiveRepository.save(newGeneralObjective);
                        });
                        return existingGeneralObjective;
                    }).collect(Collectors.toSet());
                    technicalCard.setGeneralObjectives(generalObjectives);
                }

                // Initialize humanTools if null
                if (adminTechnicalCard.getHumanTools() != null && !adminTechnicalCard.getHumanTools().isEmpty()) {
                    Set<HumanTool> humanTools = adminTechnicalCard.getHumanTools().stream().map(adminHumanTool -> {
                        // Check if a HumanTool with the same name already exists
                        HumanTool existingHumanTool = humanToolRepository.findByFirstNameAndLastNameAndIdCreatedBy(adminHumanTool.getFirstName(), adminHumanTool.getLastName(), user.getId()).orElseGet(() -> {
                            // If not found, create and save a new one
                            HumanTool newHumanTool = new HumanTool();
                            newHumanTool.setFirstName(adminHumanTool.getFirstName());
                            newHumanTool.setLastName(adminHumanTool.getLastName());
                            newHumanTool.setAdresse(adminHumanTool.getAdresse());
                            newHumanTool.setSource(SourceTechnicalCard.user);
                            newHumanTool.setCreatedBy(user);
                            return humanToolRepository.save(newHumanTool);
                        });
                        return existingHumanTool;
                    }).collect(Collectors.toSet());
                    technicalCard.setHumanTools(humanTools);
                }

                // Initialize officialTxts if null
                if (technicalCard.getOfficialTxts() == null) {
                    technicalCard.setOfficialTxts(new HashSet<>());
                }
                if (adminTechnicalCard.getOfficialTxts() != null) {
                    technicalCard.getOfficialTxts().addAll(adminTechnicalCard.getOfficialTxts());
                }

                // Set the other properties
                technicalCard.setMaterielToots(adminTechnicalCard.getMaterielToots());
                technicalCard.setRunMonth(adminTechnicalCard.getRunMonth());
                technicalCard.setRunWeek(adminTechnicalCard.getRunWeek());
                technicalCard.setType(adminTechnicalCard.getType());
                technicalCard.setTitle(adminTechnicalCard.getTitle());
                technicalCard.setCategory(adminTechnicalCard.getCategory());
                technicalCard.setCode(adminTechnicalCard.getCode());
                technicalCard.setTypeEstablishment(adminTechnicalCard.getTypeEstablishment());
                technicalCard.setNumber(adminTechnicalCard.getNumber());

                // Save the technical card
                technicalCardRepository.save(technicalCard);
            }
        });

        return true;
    }


}

