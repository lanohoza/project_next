package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditTechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.*;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TechnicalCardService {

	private static final Logger log = LoggerFactory.getLogger(TechnicalCardService.class);

	final TechnicalCardRepository technicalCardRepository;
	final HelperService helperService;
	final AudienceRepository audienceRepository;
	final TechnicalCardCategoryRepository technicalCardCategoryRepository;
	final GeneralObjectiveRepository generalObjectiveRepository;
	final HumanToolRepository humanToolRepository;
	final OfficialTxtRepository officialTxtRepository;
	final DifficultyRepository difficultyRepository;

	// Update
	public boolean updateTechnicalCard(Long id, AddEditTechnicalCardDto addEditTechnicalCardDto) throws Exception {
		log.info(Messages.START_FUNCTION, "updateTechnicalCard");
		Optional<TechnicalCard> technicalCardOptional = technicalCardRepository.findById(id);
		if (!technicalCardOptional.isPresent()) {
			throw new Exception("هاته البطاقة التقنية غير موجودة");
		}
		TechnicalCard technicalCard = technicalCardOptional.get();

		if (technicalCardRepository.canEdit(technicalCard.getId())) {
			throw new Exception("لايمكن تعديل البطاقة التقنية لارتباطها بمهمة في البرنامج الدراسي قيد التنفيذ أو تم تنفيذها");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());

		technicalCard.setCategory(TechnicalCardCategory.builder().id(addEditTechnicalCardDto.getIdTcCategory()).build());
		technicalCard.setType(addEditTechnicalCardDto.getType());
		technicalCard.setTitle(addEditTechnicalCardDto.getTitle());
		technicalCard.setMaterielToots(addEditTechnicalCardDto.getMaterielToots());
		technicalCard.setFeedback(addEditTechnicalCardDto.getFeedback());
		technicalCard.setImage(addEditTechnicalCardDto.getImage());
		technicalCard.setCreatedBy(userDetails.getOriachadUser());
		technicalCard.setRunMonth(addEditTechnicalCardDto.getRunMonth());
		technicalCard.setRunWeek(addEditTechnicalCardDto.getRunWeek());
		if (addEditTechnicalCardDto.getGeneralObjectiveIds() != null) {
			Set<GeneralObjective> generalObjectives = addEditTechnicalCardDto.getGeneralObjectiveIds().stream().map(idLong -> generalObjectiveRepository.findById(idLong).orElseThrow(() -> new RuntimeException("GeneralObjective not found"))).collect(Collectors.toSet());
			technicalCard.setGeneralObjectives(generalObjectives);
		}
		if (addEditTechnicalCardDto.getAudienceIds() != null) {
			Set<Audience> audiences = addEditTechnicalCardDto.getAudienceIds().stream().map(idLong -> audienceRepository.findById(idLong).orElseThrow(() -> new RuntimeException("Audience not found"))).collect(Collectors.toSet());
			technicalCard.setAudiences(audiences);
		}

		if (addEditTechnicalCardDto.getHumanToolIds() != null) {
			Set<HumanTool> humanTools = addEditTechnicalCardDto.getHumanToolIds().stream().map(idLong -> humanToolRepository.findById(idLong).orElseThrow(() -> new RuntimeException("HumanTool not found"))).collect(Collectors.toSet());
			technicalCard.setHumanTools(humanTools);
		}

		if (addEditTechnicalCardDto.getOfficialTxtIds() != null) {
			Set<OfficialTxt> officialTxts = addEditTechnicalCardDto.getOfficialTxtIds().stream().map(idLong -> officialTxtRepository.findById(idLong).orElseThrow(() -> new RuntimeException("OfficialTxt not found"))).collect(Collectors.toSet());
			technicalCard.setOfficialTxts(officialTxts);
		}

		if (addEditTechnicalCardDto.getDifficultyIds() != null) {
			Set<Difficulty> difficulties = addEditTechnicalCardDto.getDifficultyIds().stream().map(idLong -> difficultyRepository.findById(idLong).orElseThrow(() -> new RuntimeException("Difficulty not found"))).collect(Collectors.toSet());
			technicalCard.setDifficulties(difficulties);
		}
		technicalCardRepository.save(technicalCard);
		log.info(Messages.PROCESS_SUCCESSFULLY, "updateTechnicalCard");
		return true;


	}

	// Delete
	public boolean deleteTechnicalCard(Long id) throws Exception {
		log.info(Messages.START_FUNCTION, "deleteTechnicalCard");
		TechnicalCard technicalCard = technicalCardRepository.findById(id).orElseThrow();
		if (technicalCard.getDefaultTc()){
			throw new Exception("لايمكن حذف البطاقة التقنية لأنها أفتراضية ");
		}
		if (technicalCardRepository.canDelete(id)) {
			throw new Exception("لايمكن حذف البطاقة التقنية لارتباطها بمهمة في البرنامج الدراسي قيد التنفيذ");
		}
		technicalCardRepository.deleteById(id);
		log.info(Messages.DELETE_SUCCESSFULLY, "task");
		return true;

	}

	// Read tasks by createdBy
	public Page<TechnicalCardDto> search(String search, Long idTcCategory, int month, int page, int size) {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		Page<TechnicalCard> technicalCards = technicalCardRepository.findByCreatedBy(search, idTcCategory, month, userDetails.getIdUser(), pageable);
		return technicalCards.map(technicalCard -> TechnicalCardDto.builder().defaultTc(technicalCard.getDefaultTc()).id(technicalCard.getId()).idTcCategory(technicalCard.getIdTcCategory()).code(technicalCard.getCode()).createDate(technicalCard.getCreateDate()).feedback(technicalCard.getFeedback()).id(technicalCard.getId()).title(technicalCard.getTitle()).runMonth(technicalCard.getRunMonth()).runWeek(technicalCard.getRunWeek()).type(technicalCard.getType()).build());

	}

	public TechnicalCardDto findTechnicalCardsById(Long id) throws Exception {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsById");
		var technicalCardOptional = technicalCardRepository.findById(id);
		return technicalCardOptional.map(technicalCard -> TechnicalCardDto.builder().defaultTc(technicalCard.getDefaultTc()).id(technicalCard.getId()).idTcCategory(technicalCard.getIdTcCategory()).code(technicalCard.getCode()).createDate(technicalCard.getCreateDate()).feedback(technicalCard.getFeedback()).materielToots(technicalCard.getMaterielToots()).id(technicalCard.getId()).title(technicalCard.getTitle()).runMonth(technicalCard.getRunMonth()).runWeek(technicalCard.getRunWeek()).type(technicalCard.getType()).generalObjectiveIds(technicalCard.getGeneralObjectives().stream().map(GeneralObjective::getId).toList()).audienceIds(technicalCard.getAudiences().stream().map(Audience::getId).toList()).difficultyIds(technicalCard.getDifficulties().stream().map(Difficulty::getId).toList()).humanToolIds(technicalCard.getHumanTools().stream().map(HumanTool::getId).toList()).officialTxtIds(technicalCard.getOfficialTxts().stream().map(OfficialTxt::getId).toList()).build()).orElseThrow(() -> new Exception("not found technical card"));

	}

	@Transactional
	public boolean createTechnicalCard(AddEditTechnicalCardDto addEditTechnicalCardDto) throws Exception {

		log.info("Start creating task with details: {}", addEditTechnicalCardDto);
		OriachadUser oriachadUser = helperService.getCurrentUser().getOriachadUser();
		Establishment establishment = helperService.getCurrentEstablishment();

		TechnicalCardCategory technicalCardCategory = technicalCardCategoryRepository.findById(addEditTechnicalCardDto.getIdTcCategory()).orElseThrow();
		Long number = technicalCardRepository.lastNumberOfTechnicalCard(oriachadUser.getId(), technicalCardCategory.getId()).orElse(0L) + 1;

		String code = generateTechnicalCardCode(number, technicalCardCategory.getCode(), establishment);
		// Create and save TcTechnicalCard entity
		TechnicalCard technicalCard = new TechnicalCard();
		technicalCard.setCategory(TechnicalCardCategory.builder().id(addEditTechnicalCardDto.getIdTcCategory()).build());
		technicalCard.setType(addEditTechnicalCardDto.getType());
		technicalCard.setTitle(addEditTechnicalCardDto.getTitle());
		technicalCard.setTypeEstablishment(establishment.getType());
		technicalCard.setCode(code);
		technicalCard.setDefaultTc(false);
		technicalCard.setNumber(number);
		technicalCard.setMaterielToots(addEditTechnicalCardDto.getMaterielToots());
		technicalCard.setFeedback(addEditTechnicalCardDto.getFeedback());
		technicalCard.setImage(addEditTechnicalCardDto.getImage());
		technicalCard.setCreatedBy(oriachadUser);
		technicalCard.setRunMonth(addEditTechnicalCardDto.getRunMonth());
		technicalCard.setRunWeek(addEditTechnicalCardDto.getRunWeek());
		if (addEditTechnicalCardDto.getGeneralObjectiveIds() != null) {
			Set<GeneralObjective> generalObjectives = addEditTechnicalCardDto.getGeneralObjectiveIds().stream().map(id -> generalObjectiveRepository.findById(id).orElseThrow(() -> new RuntimeException("GeneralObjective not found"))).collect(Collectors.toSet());
			technicalCard.setGeneralObjectives(generalObjectives);
		}
		if (addEditTechnicalCardDto.getAudienceIds() != null) {
			Set<Audience> audiences = addEditTechnicalCardDto.getAudienceIds().stream().map(id -> audienceRepository.findById(id).orElseThrow(() -> new RuntimeException("Audience not found"))).collect(Collectors.toSet());
			technicalCard.setAudiences(audiences);
		}

		if (addEditTechnicalCardDto.getHumanToolIds() != null) {
			Set<HumanTool> humanTools = addEditTechnicalCardDto.getHumanToolIds().stream().map(id -> humanToolRepository.findById(id).orElseThrow(() -> new RuntimeException("HumanTool not found"))).collect(Collectors.toSet());
			technicalCard.setHumanTools(humanTools);
		}

		if (addEditTechnicalCardDto.getOfficialTxtIds() != null) {
			Set<OfficialTxt> officialTxts = addEditTechnicalCardDto.getOfficialTxtIds().stream().map(id -> officialTxtRepository.findById(id).orElseThrow(() -> new RuntimeException("OfficialTxt not found"))).collect(Collectors.toSet());
			technicalCard.setOfficialTxts(officialTxts);
		}

		if (addEditTechnicalCardDto.getDifficultyIds() != null) {
			Set<Difficulty> difficulties = addEditTechnicalCardDto.getDifficultyIds().stream().map(id -> difficultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Difficulty not found"))).collect(Collectors.toSet());
			technicalCard.setDifficulties(difficulties);
		}
		technicalCardRepository.save(technicalCard);


		return true;
	}

	private String generateTechnicalCardCode(Long number, String tcCategoryCode, Establishment establishment) throws Exception {

		switch (String.valueOf(number).length()) {
			case 1:
				return tcCategoryCode + "C-00" + number;
			case 2:
				return tcCategoryCode + "C-0" + number;
			case 3:
				return tcCategoryCode + "C-" + number;
		}
		throw new Exception("there are error in system please contact the owner");
	}


}
