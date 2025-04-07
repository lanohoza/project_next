package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.SubjectDto;
import com.pajiniweb.oriachad_backend.domains.dtos.SubjectShedSettingConditionDto;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.exceptions.BadRequestException;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.services.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/current-type")
    public List<SubjectDto> findAll() {
        return subjectService.findAllByCurrentEstablishmentType();
    }

    @GetMapping("/by-classe/{idClasse}")
    public ResponseEntity<List<SubjectDto>> findAllByClasse(@PathVariable Long idClasse) {
        LOGGER.info("Start findAllbyYear Subjects");
        try {
            List<SubjectDto> subjects = subjectService.findAllByClasse(idClasse);
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error finding all by Year Subjects", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> findById(@PathVariable Long id) {
        LOGGER.info("Start findById Subject");

        try {
            SubjectDto subject = subjectService.findById(id);
            return new ResponseEntity<>(subject, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Subject not found", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Error finding Subject by id", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody SubjectDto subjectDto) {
        LOGGER.info("Start create Subject");

        try {
            boolean result = subjectService.save(subjectDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            LOGGER.error("Invalid input", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Error creating Subject", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody SubjectDto subjectDto) {
        LOGGER.info("Start update Subject");

        try {
            boolean result = subjectService.update(id, subjectDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Subject not found", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            LOGGER.error("Invalid input", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Error updating Subject", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        LOGGER.info("Start deleteById Subject");

        try {
            boolean result = subjectService.deleteById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Subject not found", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Error deleting Subject", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<SubjectShedSettingConditionDto>> getSubjectsByType() {
        List<SubjectShedSettingConditionDto> dtos = subjectService.findAllByTypeForShedSettingCondition();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/admin/by-type/{type}")
    public ResponseEntity<List<SubjectDto>> getAdminSubjectsByType(@PathVariable TypeEstablishment type) {
        return ResponseEntity.ok(subjectService.getAdminSubjectsByType(type));
    }
}
