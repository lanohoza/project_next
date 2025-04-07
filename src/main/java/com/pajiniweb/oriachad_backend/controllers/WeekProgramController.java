package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditWeekProgramDto;
import com.pajiniweb.oriachad_backend.domains.dtos.CurrentMonthAndWeekDto;
import com.pajiniweb.oriachad_backend.domains.entities.WeekProgram;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.WeekProgramService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/week-programs")
public class WeekProgramController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeekProgramController.class);

    @Autowired
    private WeekProgramService weekProgramService;

    @GetMapping("/all")
    public ResponseEntity<List<AddEditWeekProgramDto>> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll WeekProgram");
        List<AddEditWeekProgramDto> weekPrograms = weekProgramService.findAll();
        return new ResponseEntity<>(weekPrograms, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<WeekProgram> findById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById WeekProgram");
        return weekProgramService.findById(id)
                .map(weekProgram -> new ResponseEntity<>(weekProgram, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<WeekProgram> create(@RequestBody @Valid AddEditWeekProgramDto addEditWeekProgramDto) {
        LOGGER.info(Messages.START_FUNCTION, "create WeekProgram");
        WeekProgram savedWeekProgram = weekProgramService.save(addEditWeekProgramDto);
        return new ResponseEntity<>(savedWeekProgram, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WeekProgram> update(@PathVariable Long id, @RequestBody @Valid AddEditWeekProgramDto addEditWeekProgramDto) {
        LOGGER.info(Messages.START_FUNCTION, "update WeekProgram");
        try {
            WeekProgram updatedWeekProgram = weekProgramService.update(id, addEditWeekProgramDto);
            return new ResponseEntity<>(updatedWeekProgram, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error updating WeekProgram", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById WeekProgram");
        boolean isDeleted = weekProgramService.deleteById(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<AddEditWeekProgramDto>> findByIdUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<AddEditWeekProgramDto> weekPrograms = weekProgramService.findByIdUser(page,size);
        return new ResponseEntity<>(weekPrograms, HttpStatus.OK);
    }

    @GetMapping("/getBeginAndEndWeek")
    public ResponseEntity<CurrentMonthAndWeekDto> getBeginAndEndWeek(@RequestParam int weekNumber,
                                                                     @RequestParam int month) {
        CurrentMonthAndWeekDto currentMonthAndWeekDto = weekProgramService.getBeginAndEndWeek(weekNumber, month);
        return new ResponseEntity<>(currentMonthAndWeekDto, HttpStatus.OK);
    }

}
