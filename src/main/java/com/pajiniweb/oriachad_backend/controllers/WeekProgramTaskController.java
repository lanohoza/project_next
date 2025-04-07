package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditTasksWeekProgramDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditWeekProgramTaskDto;
import com.pajiniweb.oriachad_backend.domains.dtos.WeekProgramTasksUserDto;
import com.pajiniweb.oriachad_backend.domains.entities.WeekProgramTask;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.WeekProgramTaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/week-program-tasks")
public class WeekProgramTaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeekProgramTaskController.class);

    @Autowired
    private WeekProgramTaskService weekProgramTaskService;

    @GetMapping("/all")
    public ResponseEntity<List<AddEditWeekProgramTaskDto>> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll WeekProgramTasks");
        List<AddEditWeekProgramTaskDto> tasks = weekProgramTaskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AddEditWeekProgramTaskDto> findById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById WeekProgramTask");
        return weekProgramTaskService.findById(id)
                .map(task -> new ResponseEntity<>(AddEditWeekProgramTaskDto.builder()
                        .id(task.getId())
                        .days(task.getDays())
                        .period(task.getPeriod())
                        .descrption(task.getDescrption())
                        .idWeekProgram(task.getIdWeekProgram())
                        .idTask(task.getIdTask())
                        .build(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<WeekProgramTask> create(@RequestBody @Valid AddEditWeekProgramTaskDto weekProgramTaskDto) {
        LOGGER.info(Messages.START_FUNCTION, "create WeekProgramTask");
        WeekProgramTask createdTask = weekProgramTaskService.save(weekProgramTaskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<WeekProgramTask>> createAll(
            @RequestBody AddEditTasksWeekProgramDto tasksWeekProgramDto) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "createAll WeekProgramTasks");
        List<WeekProgramTask> createdTasks = weekProgramTaskService.saveOrUpdateAll(tasksWeekProgramDto);
        return new ResponseEntity<>(createdTasks, HttpStatus.CREATED);
    }

    @PutMapping("/update/{idWeekProgram}")
    public ResponseEntity<List<WeekProgramTask>> update(@PathVariable Long idWeekProgram, @RequestBody @Valid AddEditWeekProgramTaskDto weekProgramTaskDto) {
        LOGGER.info(Messages.START_FUNCTION, "update WeekProgramTask");
        List<WeekProgramTask> updatedTask = weekProgramTaskService.update(idWeekProgram, weekProgramTaskDto);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById WeekProgramTask");
        return weekProgramTaskService.deleteById(id);
    }

    @GetMapping("/findByIdWeekProgram/{id}")
    public AddEditTasksWeekProgramDto findByIdWeekProgram(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdWeekProgram");
        return weekProgramTaskService.findByIdWeekProgram(id);
    }

    @GetMapping("/findByIdWeekProgramForDocument/{id}")
    public WeekProgramTasksUserDto findByIdWeekProgramForDocument(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdWeekProgramForDocument");
        return weekProgramTaskService.findByIdWeekProgramForDocument(id);
    }
}
