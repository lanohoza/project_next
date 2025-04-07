package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActionTaskDto;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ActionTask;
import com.pajiniweb.oriachad_backend.domains.dtos.TaskWithActionsDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardYearDto;
import com.pajiniweb.oriachad_backend.domains.entities.Task;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.TaskRepository;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

	private static final Logger log = LoggerFactory.getLogger(TaskService.class);

	private final TaskRepository taskRepository;

	private final HelperService helperService;


	public TaskWithActionsDto getTaskWithActionsById(Long id) {

		Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
		List<ActionTask> actions = task.getActions().stream().toList();
		Year year = helperService.getCurrentYear();

		return TaskWithActionsDto.builder()
				.yearTitle(year.getTitle())
				.id(task.getId())
				.status(task.getStatus())
				.createdDate(task.getCreatedDate())
				.lastModifiedDate(task.getLastModifiedDate())
				.runMonth(task.getRunMonth())
				.runWeek(task.getRunWeek())
				.type(task.getType())
				.code(task.getCode())
				.title(task.getTitle())
				.category(task
						.getCategory().getName())
				.actions(actions.stream().sorted(Comparator.comparingLong(ActionTask::getIdAction)).map(actionTask -> ActionTaskDto.builder()
						.id(actionTask.getId())
						.status(actionTask.getStatus())
						.resultValue(actionTask.getResultValue())
						.resultType(actionTask.getResultType())
						.description(actionTask.getAction().getDescription())
						.idAction(actionTask.getAction().getId())
						.title(actionTask.getAction().getTitle())
						.build()).collect(Collectors.toList())).build();
	}

	public Page<TechnicalCardYearDto> donneSearch(String search, Long idTcCategory, int month, int page, int size) {
		log.info(Messages.START_FUNCTION, "donneSearch");
		Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
		Year year = helperService.getCurrentYear();
		Page<Task> taskPage = taskRepository.donneSearch(search, idTcCategory, month, year.getId(), userDetails.getIdUser(), pageable);
		return taskPage.map(task -> TechnicalCardYearDto.builder().id(task.getId()).idTcCategory(task.getIdTcCategory()).code(task.getCode()).createDate(task.getCreatedDate()).feedback(task.getFeedback()).title(task.getTitle()).runMonth(task.getRunMonth()).runWeek(task.getRunWeek()).type(task.getType()).idTask(task.getId()).lastModifiedDateTask(task.getLastModifiedDate()).createdDateTask(task.getCreatedDate()).idYear(task.getIdYear()).statusTask(task.getStatus()).build());

	}
}
