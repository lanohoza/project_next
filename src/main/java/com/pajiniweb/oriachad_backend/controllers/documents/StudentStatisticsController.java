package com.pajiniweb.oriachad_backend.controllers.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.documents.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.documents.StudentStatisticsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StudentStatisticsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentStatisticsController.class);

    private final StudentStatisticsService studentStatisticsService;


    @GetMapping("/general")
    public ResponseEntity<GeneralStatisticsDto> getGeneralStatistics() {
        LOGGER.info(Messages.START_FUNCTION, "getGeneralStatistics");
        GeneralStatisticsDto generalStatisticsDto = studentStatisticsService.getGeneralStatistics();
        return new ResponseEntity<>(generalStatisticsDto, HttpStatus.OK);
    }

    @GetMapping("/students/breaks")
    public ResponseEntity<StudentsBreaksDto> getStudentsBreaks() {
        LOGGER.info(Messages.START_FUNCTION, "getStudentsBreaks");
        StudentsBreaksDto studentsBreaks = studentStatisticsService.getStudentsBreaks();
        return new ResponseEntity<>(studentsBreaks, HttpStatus.OK);
    }

    @GetMapping("/students/diseases")
    public ResponseEntity<StudentsDiseasesDto> getStudentsDiseases() {
        LOGGER.info(Messages.START_FUNCTION, "getStudentsDiseases");
        StudentsDiseasesDto studentsDiseases = studentStatisticsService.getStudentsDiseases();
        return new ResponseEntity<>(studentsDiseases, HttpStatus.OK);
    }

    @GetMapping("/students/needs")
    public ResponseEntity<StudentsNeedsDto> getStudentsNeeds() {
        LOGGER.info(Messages.START_FUNCTION, "getStudentsDiseases");
        StudentsNeedsDto studentsNeeds = studentStatisticsService.getStudentsNeeds();
        return new ResponseEntity<>(studentsNeeds, HttpStatus.OK);
    }

    @GetMapping("/students/orphans")
    public ResponseEntity<StudentsNeedsDto> getStudentsOrphans() {
        LOGGER.info(Messages.START_FUNCTION, "getStudentsOrphans");
        StudentsNeedsDto studentsNeeds = studentStatisticsService.getStudentsOrphans();
        return new ResponseEntity<>(studentsNeeds, HttpStatus.OK);
    }
    @GetMapping("/students/mains")
    public ResponseEntity<StudentsMainsDto> getStudentsMains() {
        LOGGER.info(Messages.START_FUNCTION, "getStudentsMains");
        StudentsMainsDto studentsMainsDto = studentStatisticsService.getStudentsMains();
        return new ResponseEntity<>(studentsMainsDto, HttpStatus.OK);
    }



}
