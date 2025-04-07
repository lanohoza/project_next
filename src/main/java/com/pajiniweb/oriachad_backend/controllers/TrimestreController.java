package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.TrimestreDto;
import com.pajiniweb.oriachad_backend.exceptions.BadRequestException;
import com.pajiniweb.oriachad_backend.exceptions.ResourceNotFoundException;
import com.pajiniweb.oriachad_backend.services.TrimestreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trimestres")
public class TrimestreController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrimestreController.class);

    @Autowired
    private TrimestreService trimestreService;

    @GetMapping("/all")
    public ResponseEntity<List<TrimestreDto>> findAll() {
        LOGGER.info("Start findAll Trimestres");

        try {
            List<TrimestreDto> trimestres = trimestreService.findAll();
            return new ResponseEntity<>(trimestres, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error finding all Trimestres", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/by-year/{idYear}")
    public ResponseEntity<List<TrimestreDto>> findAllByYear(@PathVariable Long idYear) {
        LOGGER.info("Start findAllbyYear Trimestres");
        try {
            List<TrimestreDto> trimestres = trimestreService.findAllByYear(idYear);
            return new ResponseEntity<>(trimestres, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error finding all by Year Trimestres", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrimestreDto> findById(@PathVariable Long id) {
        LOGGER.info("Start findById Trimestre");

        try {
            TrimestreDto trimestre = trimestreService.findById(id);
            return new ResponseEntity<>(trimestre, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Trimestre not found", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Error finding Trimestre by id", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
