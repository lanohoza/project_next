package com.pajiniweb.oriachad_backend.actions.services;

import com.pajiniweb.oriachad_backend.actions.domains.entities.ActionTask;
import com.pajiniweb.oriachad_backend.actions.domains.entities.Activity;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionStatus;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActivityType;
import com.pajiniweb.oriachad_backend.actions.repositories.ActionTaskRepository;
import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.actions.scripts.core.TCScriptService;
import com.pajiniweb.oriachad_backend.domains.entities.Task;
import com.pajiniweb.oriachad_backend.domains.enums.TaskStatus;
import com.pajiniweb.oriachad_backend.domains.enums.TypeTc;
import com.pajiniweb.oriachad_backend.repositories.TaskRepository;
import com.pajiniweb.oriachad_backend.services.HelperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ActionTaskService {

	final ActionTaskRepository actionTaskRepository;
	final TaskRepository taskRepository;
	final ActivityRepository activityRepository;
	final HelperService helperService;
	final TCScriptService tcScriptService;
	@Transactional
	public Boolean execute(Long idActionTask) throws Exception {
		ActionTask actionTask = actionTaskRepository.findById(idActionTask).orElseThrow();
		if (actionTask.getIdTask()==null&&actionTask.getStatus() != ActionStatus.todo) {
			throw new Exception("Action Task is " + actionTask.getStatus());
		}
		if (actionTaskRepository.existsDependenciesNotStatus(actionTask.getId(), ActionStatus.finish)) {
			throw new Exception("لا يمكن تنفيذ هاته التعليمة لارتباطها بمهام اخرى غر منفذة");
		}
		if (actionTask.getScript() != null) {
			tcScriptService.runScript(actionTask.getScript().getCode());
		}
		actionTask.setStatus(ActionStatus.finish);
		actionTaskRepository.save(actionTask);
		activityRepository.save(Activity.builder().type(ActivityType.action).content(String.format("%s : %s", actionTask.getTask().getTitle(), actionTask.getAction().getTitle())).user(helperService.getCurrentUser().getOriachadUser()).build());
		if (!actionTaskRepository.allRelatedActionTaskNotStatus(actionTask.getId(), ActionStatus.finish)) {
			Task task = taskRepository.findById(actionTask.getIdTask()).orElseThrow();
			if (task.getType() != TypeTc.permanent) {
				task.setStatus(TaskStatus.finish);
				taskRepository.save(task);
			}
		}

		return true;
	}

}
