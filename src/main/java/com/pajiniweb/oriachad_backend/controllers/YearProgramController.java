package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.TaskDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardYearDto;
import com.pajiniweb.oriachad_backend.domains.entities.Task;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.TaskService;
import com.pajiniweb.oriachad_backend.services.YearProgramService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/year-programs")
public class YearProgramController {

	private static final Logger log = LoggerFactory.getLogger(YearProgramController.class);

	@Autowired
	private YearProgramService yearProgramService;


	// Read tasks by createdBy with pagination
	@GetMapping("/search")
	public Page<TechnicalCardYearDto> search(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Long idTcCategory, @RequestParam(defaultValue = "-1") int month, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		return yearProgramService.search(search, idTcCategory, month, page, size);
	}

	@GetMapping("/tc-search")
	public Page<TechnicalCardDto> getNotImplementedTechnicalCard(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		return yearProgramService.getNotImplementedTechnicalCard(search, page, size);
	}

	@PostMapping("/implement")
	public boolean getNotImplementedTechnicalCard(@RequestBody @NotNull List<Long> ids) {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		return yearProgramService.implementTechnicalCards(ids);
	}

	@PostMapping("/task/{idTask}/execute")
	public boolean execute(@PathVariable Long idTask) throws Exception {
		log.info(Messages.START_FUNCTION, "execute");
		return yearProgramService.execute(idTask);
	}

	@DeleteMapping("/delete-task/{idTask}")
	public boolean getNotImplementedTechnicalCard(@PathVariable Long idTask) throws Exception {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		return yearProgramService.deleteTask(idTask);
	}

	@GetMapping("/current-week-tasks")
	public List<TechnicalCardYearDto> getCurrentWeekTasks() throws Exception {
		log.info(Messages.START_FUNCTION, "getCurrentWeekTasks");
		return yearProgramService.getCurrentWeekTasks();
	}

	@GetMapping("/getAllTaskByUserAndYear")
	public List<TechnicalCardYearDto> getAllTaskByUserAndYear() throws Exception {
		log.info(Messages.START_FUNCTION, "getAllTaskByUserAndYear");
		return yearProgramService.getAllTaskByUserAndYear();
	}

}
