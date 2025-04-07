package com.pajiniweb.oriachad_backend.actions.controllers;


import com.pajiniweb.oriachad_backend.actions.services.ActionTaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/action-tasks")
@AllArgsConstructor
public class ActionTaskController {
	final ActionTaskService actionTaskService;

	@PostMapping("/{id}/execute")
	public boolean execute(@PathVariable Long id) throws Exception {
		return actionTaskService.execute(id);
	}
}