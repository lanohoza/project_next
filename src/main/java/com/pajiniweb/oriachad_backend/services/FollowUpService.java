package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.actions.domains.entities.Activity;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedCategory;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActivityType;
import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditFollowUpDto;
import com.pajiniweb.oriachad_backend.domains.dtos.FollowUpDto;
import com.pajiniweb.oriachad_backend.domains.dtos.StudentDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.FollowUpRepository;
import com.pajiniweb.oriachad_backend.repositories.GuidanceGroupRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FollowUpService {

	private static final Logger log = LoggerFactory.getLogger(FollowUpService.class);

	final HelperService helperService;
	final FollowUpRepository followUpRepository;
	private final ActivityRepository activityRepository;
	private final GuidanceGroupRepository guidanceGroupRepository;


	// Update
	public boolean update(AddEditFollowUpDto addEditFollowUpDto) throws Exception {
		log.info(Messages.START_FUNCTION, "updateTechnicalCard");
		Optional<FollowUp> flowUpOptional = followUpRepository.findById(addEditFollowUpDto.getId());
		if (!flowUpOptional.isPresent()) {
			throw new Exception("هاته البطاقة التقنية غير موجودة");
		}
		FollowUp flowUp = flowUpOptional.get();
		flowUp.setDescription(addEditFollowUpDto.getDescription());
		flowUp.setStatus(FollowupStatus.todo);
		flowUp.setType(addEditFollowUpDto.getType());
		flowUp.setResourceUrl(addEditFollowUpDto.getResourceUrl());
		flowUp.setShedCategory(ShedCategory.builder().id(addEditFollowUpDto.getIdShedCategory()).build());
		if (addEditFollowUpDto.getType() == FollowUpType.single) {
			flowUp.setStudent(Student.builder().id(addEditFollowUpDto.getIdStudent()).build());
		} else {
			flowUp.setGuidanceGroup(GuidanceGroup.builder().id(addEditFollowUpDto.getIdGuidanceGroup()).build());
		}
		if (addEditFollowUpDto.getIdShedSettings() != null)
			flowUp.setShedSettings(addEditFollowUpDto.getIdShedSettings().stream().map((idSolution) -> ShedSetting.builder().id(idSolution).build()).collect(Collectors.toSet()));
		followUpRepository.save(flowUp);
		log.info(Messages.PROCESS_SUCCESSFULLY, "updateTechnicalCard");
		return true;
	}

	@Transactional
	public boolean createFlowUp(AddEditFollowUpDto addEditFollowUpDto) throws Exception {
		log.info("Start creating task with details: {}", addEditFollowUpDto);
		OriachadUser oriachadUser = helperService.getCurrentUser().getOriachadUser();
		Year year = helperService.getCurrentYear();
		FollowUp followUp = new FollowUp();
		followUp.setCreatedBy(oriachadUser);
		followUp.setYear(year);
		followUp.setStatus(FollowupStatus.todo);
		followUp.setCreateType(FlowUpCreateType.manual);
		followUp.setType(addEditFollowUpDto.getType());
		followUp.setDescription(addEditFollowUpDto.getDescription());
		followUp.setResourceUrl(addEditFollowUpDto.getResourceUrl());
		followUp.setNumber(followUpRepository.lastNumberOfFlowUp(oriachadUser.getId()).orElse(0L) + 1);
		followUp.setShedCategory(ShedCategory.builder().id(addEditFollowUpDto.getIdShedCategory()).build());
		if (addEditFollowUpDto.getType() == FollowUpType.single) {
			followUp.setStudent(Student.builder().id(addEditFollowUpDto.getIdStudent()).build());
		} else {
			followUp.setGuidanceGroup(guidanceGroupRepository.findById(addEditFollowUpDto.getIdGuidanceGroup()).orElseThrow());
		}
		if (addEditFollowUpDto.getIdShedSettings() != null)
			followUp.setShedSettings(addEditFollowUpDto.getIdShedSettings().stream().map((idSolution) -> ShedSetting.builder().id(idSolution).build()).collect(Collectors.toSet()));
		followUpRepository.save(followUp);

		if (followUp.getType() == FollowUpType.single) {
			if (!activityRepository.hasTypeToday(oriachadUser.getId(), ActivityType.interviewSingle, LocalDate.now())) {
				activityRepository.save(Activity.builder().content("إستكشاف حالات للمتابعة الإرشادية و التكفل النفسي بالتلاميذ").type(ActivityType.followUpSingle).user(oriachadUser).build());
			}
		}
		if (followUp.getType() == FollowUpType.group) {
			activityRepository.save(Activity.builder().content(String.format("%s : %s", "إستكشاف حالات للمتابعة الإرشادية و التكفل النفسي بالتلاميذ ", followUp.getGuidanceGroup().getTitle())).type(ActivityType.followUpGroup).user(oriachadUser).build());
		}
		return true;
	}


	public Page<FollowUpDto> searchFlowUps(String search, FollowUpType type, FollowupStatus followupStatus, Long idYear, Pageable pageable) {
		OriachadUser oriachadUser = helperService.getCurrentUser().getOriachadUser();
		return followUpRepository.search(search, idYear, type, followupStatus, oriachadUser.getId(), pageable).map(flowUp -> FollowUpDto.builder().id(flowUp.getId()).number(flowUp.getNumber()).createdDate(flowUp.getCreatedDate()).status(flowUp.getStatus()).resourceUrl(flowUp.getResourceUrl()).type(flowUp.getType()).target(flowUp.getType() == FollowUpType.group ? flowUp.getGuidanceGroup().getTitle() : String.format("%s %s", flowUp.getStudent().getFirstName(), flowUp.getStudent().getLastName())).description(flowUp.getDescription()).createType(flowUp.getCreateType()).build());
	}

	public FollowUpDto findById(Long id) {

		return followUpRepository.findById(id).map(flowUp -> FollowUpDto.builder().id(flowUp.getId()).createdDate(flowUp.getCreatedDate()).number(flowUp.getNumber()).resourceUrl(flowUp.getResourceUrl())
				.shedCategory(flowUp.getShedCategory().getName())
				.shedSettings(flowUp.getShedSettings().stream().map(ShedSetting::getSyndromeDiagnostic).toList())
				.idShedCategory(flowUp.getIdShedCategory())
				.idShedSettings(flowUp.getShedSettings().stream().map(ShedSetting::getId).toList())
				.taskTitle(flowUp.getTask() != null ? flowUp.getTask().getTitle() : null).status(flowUp.getStatus()).type(flowUp.getType()).description(flowUp.getDescription()).target(flowUp.getType() == FollowUpType.group ? flowUp.getGuidanceGroup().getTitle() : String.format("%s %s", flowUp.getStudent().getFirstName(), flowUp.getStudent().getLastName())).studentDtos(flowUp.getType() == FollowUpType.group ? flowUp.getGuidanceGroup().getStudents().stream().map(student -> StudentDto.builder().nbrRakmana(student.getNbrRakmana()).firstName(student.getFirstName()).lastName(student.getLastName()).birthDate(student.getBirthDate()).classeTitle(student.getCurrentClass().getTitle()).build()).toList() : null).idStudent(flowUp.getIdStudent()).idGuidanceGroup(flowUp.getIdGuidanceGroup()).createType(flowUp.getCreateType()).build()).orElseThrow();
	}

	public List<FollowUpDto> getFlowUpByCurrentYear(FollowUpType type) {
		Year year = helperService.getCurrentYear();
		OriachadUser oriachadUser = helperService.getCurrentUser().getOriachadUser();
		return followUpRepository.getFlowUpCurrentYear(type, year.getId(), oriachadUser.getId()).stream().map(flowUp -> FollowUpDto.builder().id(flowUp.getId()).type(flowUp.getType()).number(flowUp.getNumber())
				.shedCategory(flowUp.getShedCategory().getName())
				.shedSettings(flowUp.getShedSettings().stream().map(ShedSetting::getSyndromeDiagnostic).toList())
				.target(flowUp.getType() == FollowUpType.group ? flowUp.getGuidanceGroup().getTitle() : String.format("%s %s", flowUp.getStudent().getFirstName(), flowUp.getStudent().getLastName())).idStudent(flowUp.getIdStudent()).idGuidanceGroup(flowUp.getIdGuidanceGroup()).createType(flowUp.getCreateType()).build()).collect(Collectors.toList());
	}

	public boolean endFlowUp(Long id) {

		FollowUp flowUp = followUpRepository.findById(id).orElseThrow();
		flowUp.setStatus(FollowupStatus.done);
		followUpRepository.save(flowUp);
		return true;
	}

	public boolean deleteById(Long id) {
	//	followUpRepository.deleteById(id);
		return true;
	}
}
