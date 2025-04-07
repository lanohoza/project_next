package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.TaskWithActionsDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardYearDto;
import com.pajiniweb.oriachad_backend.domains.entities.Task;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    // Create
  /*  @PostMapping("/save")
    public Task createScolarYearTask(@RequestBody Task task) {
        log.info(Messages.START_FUNCTION, "createScolarYearTask");
        return taskService.createScolarYearTask(task);
    }


    // Delete
    @DeleteMapping("/delete/{id}")
    public boolean deleteScolarYearTask(@PathVariable Long id) {
        log.info(Messages.START_FUNCTION, "deleteScolarYearTask");
        return taskService.deleteScolarYearTask(id);
    }*/


    @GetMapping("/{id}/actions")
    public ResponseEntity<TaskWithActionsDto> getTaskWithActions(@PathVariable Long id) {
        TaskWithActionsDto taskWithActions = taskService.getTaskWithActionsById(id);
        return ResponseEntity.ok(taskWithActions);
    }


    @GetMapping("/donne-search")
    public Page<TechnicalCardYearDto> donneSearch(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Long idTcCategory, @RequestParam(defaultValue = "-1") int month, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
        return taskService.donneSearch(search, idTcCategory, month, page, size);
    }
   /* @GetMapping("/by-id-task/{idTask}")
    public List<Task> getTasksByIdTask(@PathVariable Long idTask) {
        return taskService.getTasksByIdTechnicalCard(idTask);
    }

    @GetMapping("/by-id-technicalCard-year/{idTask}")
    public Task getTaskByIdTechnicalCardAndYear(@PathVariable Long idTask) {
        return taskService.getTaskByIdTechnicalCardAndYear(idTask);
    }

    @GetMapping("/by-scolar-year/{idScolarYear}")
    public List<Task> getTasksByScolarYear(@PathVariable Long idScolarYear) {
        return taskService.findByIdScolarYear(idScolarYear);
    }

    @GetMapping("/by-user")
    public List<Task> getTasksByScolarYear() {
        return taskService.findByUser();
    }*/
}
