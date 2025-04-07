//package com.pajiniweb.oriachad_backend.actions.controllers;
//
//
//import com.pajiniweb.oriachad_backend.actions.domians.dtos.ActionDto;
//import com.pajiniweb.oriachad_backend.actions.services.ScriptService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/scripts")
//public class ScriptController {
//	final ScriptService scriptService;
//
//	@Autowired
//	public ScriptController(ScriptService scriptService) {
//		this.scriptService = scriptService;
//	}
//
//	@GetMapping("/actions_details/{idTask}")
//	public List<ActionDto> getActionsDetails(@PathVariable Long idTask) {
//		return scriptService.getActionsDetails(idTask);
//	}
//
//	@GetMapping("/actions_execute/{idTask}")
//	public void actionsExecute(@PathVariable Long idTask) throws Exception {
//		scriptService.actionsExecute(idTask);
//	}
//}
