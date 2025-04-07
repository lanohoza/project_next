//package com.pajiniweb.oriachad_backend.actions.services;
//
//import com.pajiniweb.oriachad_backend.actions.domians.dtos.ActionDto;
//import com.pajiniweb.oriachad_backend.actions.scripts.core.ScriptImplementation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ScriptService {
//
//	Map<String, ScriptImplementation> actionScripts;
//	final ActionService actionService;
//	final ActivitiesService activitiesService;
//
//
//	@Autowired
//	public ScriptService(ActionService actionService, ActivitiesService activitiesService) {
//		this.actionService = actionService;
//		this.activitiesService = activitiesService;
//		actionScripts = new HashMap<>();
//	}
//
//	public List<ActionDto> getActionsDetails(Long idTask) {
//		List<String> actionCodes = actionService.getAllActionCodesByIdTask(idTask);
//	//	return actionCodes.stream().map(code -> actionScripts.get(code).getAction()).toList();
//	}
//
//	@Transactional
//	public void actionsExecute(Long idTask) throws Exception {
//		List<String> actionCodes = actionService.getAllActionCodesByIdTask(idTask);
//		for (var code : actionCodes) {
//			var actionScript = actionScripts.get(code);
//			//actionScript.run();
//		}
//
//	}
//}
