package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ActionTask;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionStatus;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardYearDto;
import com.pajiniweb.oriachad_backend.domains.entities.GeneralObjective;
import com.pajiniweb.oriachad_backend.domains.entities.Task;
import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.domains.enums.TaskStatus;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.GeneralObjectiveRepository;
import com.pajiniweb.oriachad_backend.repositories.TaskRepository;
import com.pajiniweb.oriachad_backend.repositories.TechnicalCardRepository;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
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

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class YearProgramService {

	private static final Logger log = LoggerFactory.getLogger(YearProgramService.class);

	@Autowired
	private TaskRepository taskRepository;
	@Autowired

	private TechnicalCardRepository technicalCardRepository;
	@Autowired
	YearService yearService;
	@Autowired
	HelperService helperService;
	@Autowired
	private DateService dateService;
	@Autowired
	private GeneralObjectiveRepository generalObjectiveRepository;

	public Page<TechnicalCardYearDto> search(String search, Long idTcCategory, int month, int page, int size) {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		Year year = helperService.getCurrentYear();
		Page<Task> taskPage = taskRepository.yearProgramSearch(search, idTcCategory, month, year.getId(), userDetails.getIdUser(), pageable);
		return taskPage.map(task -> TechnicalCardYearDto.builder().id(task.getId()).idTcCategory(task.getIdTcCategory()).code(task.getCode()).createDate(task.getCreatedDate()).feedback(task.getFeedback()).title(task.getTitle()).runMonth(task.getRunMonth()).runWeek(task.getRunWeek()).type(task.getType()).idTask(task.getId()).lastModifiedDateTask(task.getLastModifiedDate()).createdDateTask(task.getCreatedDate()).idYear(task.getIdYear()).statusTask(task.getStatus()).build());
	}


	public Page<TechnicalCardDto> getNotImplementedTechnicalCard(String search, int page, int size) {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		Year year = helperService.getCurrentYear();
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		return technicalCardRepository.findAllWithNotTask(search, userDetails.getIdUser(), year.getId(), pageable).map(technicalCard -> TechnicalCardDto.builder().id(technicalCard.getId()).idTcCategory(technicalCard.getIdTcCategory()).code(technicalCard.getCode()).createDate(technicalCard.getCreateDate()).feedback(technicalCard.getFeedback()).id(technicalCard.getId()).title(technicalCard.getTitle()).runMonth(technicalCard.getRunMonth()).runWeek(technicalCard.getRunWeek()).type(technicalCard.getType()).build());
	}

	@Transactional
	public boolean implementTechnicalCards(List<Long> technicalCardIds) {
		log.info(Messages.START_FUNCTION, "implementTechnicalCard");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		Year year = helperService.getCurrentYear();
		List<TechnicalCard> technicalCards = technicalCardRepository.findByIdIn(technicalCardIds);
		List<Task> tasks = technicalCards.stream().map(technicalCard -> {
			Task task = Task.builder().year(year).technicalCard(technicalCard).createdBy(userDetails.getOriachadUser()).status(TaskStatus.todo).build();
			// Initialize audiences if null
			if (technicalCard.getAudiences() != null && !technicalCard.getAudiences().isEmpty()) {
				task.setAudiences(new HashSet<>());
				task.getAudiences().addAll(technicalCard.getAudiences());
			}
			if (technicalCard.getDifficulties() != null && !technicalCard.getDifficulties().isEmpty()) {
				task.setDifficulties(new HashSet<>());
				task.getDifficulties().addAll(technicalCard.getDifficulties());
			}

			if (technicalCard.getGeneralObjectives() != null && !technicalCard.getGeneralObjectives().isEmpty()) {
				task.setGeneralObjectives(new HashSet<>());
				task.getGeneralObjectives().addAll(technicalCard.getGeneralObjectives());
			}

			if (technicalCard.getHumanTools() != null && !technicalCard.getHumanTools().isEmpty()) {
				task.setHumanTools(new HashSet<>());
				task.getHumanTools().addAll(technicalCard.getHumanTools());
			}

			if (technicalCard.getOfficialTxts() != null && !technicalCard.getOfficialTxts().isEmpty()) {
				task.setOfficialTxts(new HashSet<>());
				task.getOfficialTxts().addAll(technicalCard.getOfficialTxts());
			}

			// Set the other properties
			task.setFeedback(technicalCard.getFeedback());
			task.setMaterielToots(technicalCard.getMaterielToots());
			task.setRunMonth(technicalCard.getRunMonth());
			task.setRunWeek(technicalCard.getRunWeek());
			task.setType(technicalCard.getType());
			task.setTitle(technicalCard.getTitle());
			task.setCategory(technicalCard.getCategory());
			task.setCode(technicalCard.getCode());

			return task;
		}).toList();
		taskRepository.saveAll(tasks);
		return true;
	}

	@Transactional
	public boolean execute(Long idTask) throws Exception {
		log.info(Messages.START_FUNCTION, "implementTechnicalCard");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		Task task = taskRepository.findById(idTask).orElseThrow(() -> new ResourceNotFoundException("not found Task"));
		if (task.getStatus() != TaskStatus.todo) {
			throw new Exception("task is " + task.getStatus());
		}
		task.getActions().clear();

		// Add new actions to the existing collection
		task.getTechnicalCard().getActions().forEach(action -> {
			ActionTask actionTask = ActionTask.builder().status(ActionStatus.todo).user(userDetails.getOriachadUser()).task(task).action(action).script(action.getScript()).resultValue(action.getResultValue()).resultType(action.getResultType()).build();
			task.getActions().add(actionTask);
		});
		task.setStatus(TaskStatus.in_progress);
		taskRepository.save(task);
		return true;
	}

	public boolean deleteTask(Long idTask) throws Exception {
		log.info(Messages.START_FUNCTION, "implementTechnicalCard");
		Task task = taskRepository.findById(idTask).orElseThrow(() -> new Exception("لا يمكن أيجاد هاته المهمة"));
		if (task.getStatus() != TaskStatus.todo) {
			throw new Exception("لا يمكن حذف هاته المهمة لان حالتها  " + task.getStatus());
		}
		taskRepository.delete(task);


		return true;
	}

	public List<TechnicalCardYearDto> getCurrentWeekTasks() throws Exception {

		Calendar calendar = Calendar.getInstance();
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		Year year = helperService.getCurrentYear();
		return taskRepository.getCurrentWeekTasks(year.getId(), userDetails.getIdUser(), month, weekOfMonth).stream().map(task -> TechnicalCardYearDto.builder().id(task.getId()).idTcCategory(task.getIdTcCategory()).code(task.getCode()).createDate(task.getCreatedDate()).feedback(task.getFeedback()).title(task.getTitle()).runMonth(task.getRunMonth()).runWeek(task.getRunWeek()).type(task.getType()).idTask(task.getId()).lastModifiedDateTask(task.getLastModifiedDate()).createdDateTask(task.getCreatedDate()).idYear(task.getIdYear()).statusTask(task.getStatus()).build()).toList();
	}

	public List<TechnicalCardYearDto> getAllTaskByUserAndYear() {
		log.info(Messages.START_FUNCTION, "getAllTaskByUserAndYear");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());

		// Get the current year
		Year year = helperService.getCurrentYear();

		List<Task> tasks = taskRepository.getAllByUserAndYear(year.getId(), userDetails.getIdUser());

		return tasks.stream()
				.map(task -> TechnicalCardYearDto.builder()
						.id(task.getTechnicalCard().getId())
						.idTcCategory(task.getTechnicalCard().getIdTcCategory())
						.code(task.getTechnicalCard().getCode())
						.createDate(task.getTechnicalCard().getCreateDate())
						.feedback(task.getTechnicalCard().getFeedback())
						.title(task.getTechnicalCard().getTitle())
						.runMonth(task.getTechnicalCard().getRunMonth())
						.runWeek(task.getTechnicalCard().getRunWeek())
						.type(task.getTechnicalCard().getType())
						.idTask(task.getId())
						.lastModifiedDateTask(task.getLastModifiedDate())
						.createdDateTask(task.getCreatedDate())
						.idYear(task.getIdYear())
						.statusTask(task.getStatus())
						.build())
				.collect(Collectors.toList());
	}

}
