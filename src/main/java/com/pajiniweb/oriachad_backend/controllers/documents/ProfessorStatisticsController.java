package com.pajiniweb.oriachad_backend.controllers.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.documents.*;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.documents.ProfessorStatisticsService;
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
public class ProfessorStatisticsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorStatisticsController.class);

    private final ProfessorStatisticsService professorStatisticsService;


    @GetMapping("/professors/breaks")
    public ResponseEntity<ProfessorsBreaksDto> getProfessorsBreaks() {
        LOGGER.info(Messages.START_FUNCTION, "getProfessorsBreaks");
        ProfessorsBreaksDto professorsBreaksDto = professorStatisticsService.getProfessorsBreaks();
        return new ResponseEntity<>(professorsBreaksDto, HttpStatus.OK);
    }

    @GetMapping("/professors/coordinators")
    public ResponseEntity<ProfessorsCoordinatorsDto> getProfessorsCoordinators() {
        LOGGER.info(Messages.START_FUNCTION, "getProfessorsCoordinators");
        ProfessorsCoordinatorsDto professorsCoordinators = professorStatisticsService.getProfessorsCoordinators();
        return new ResponseEntity<>(professorsCoordinators, HttpStatus.OK);
    }

    @GetMapping("/professors/mains")
    public ResponseEntity<ProfessorsMainsDto> getProfessorsMains() {
        LOGGER.info(Messages.START_FUNCTION, "getProfessorsMains");
        ProfessorsMainsDto professorsMainsDto = professorStatisticsService.getProfessorsMains();
        return new ResponseEntity<>(professorsMainsDto, HttpStatus.OK);
    }
}
