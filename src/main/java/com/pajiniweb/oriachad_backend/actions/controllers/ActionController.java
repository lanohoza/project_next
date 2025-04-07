package com.pajiniweb.oriachad_backend.actions.controllers;


import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActionTaskDto;
import com.pajiniweb.oriachad_backend.actions.scripts.core.TCScriptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actions")
@AllArgsConstructor
public class ActionController {

	final TCScriptService tcScriptService;

	public List<ActionTaskDto> getAllActionByIdTask(Long idTask) {
		return null;
	}
	@GetMapping("/execute")
	public void executeAction() throws Exception {
		tcScriptService.runScript("TCO001");

	}
}