package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditClasseDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ClasseDto;
import com.pajiniweb.oriachad_backend.domains.entities.Classe;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.ClasseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
public class ClasseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClasseController.class);

    @Autowired
    ClasseService classeService;

    @GetMapping("/search")
    public ResponseEntity<Page<ClasseDto>> searchClasses(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Long idYear, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") int size) {
        LOGGER.info(Messages.START_FUNCTION, "findAll");
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        return new ResponseEntity<>(classeService.searchClasses(search, idYear, pageable), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ClasseDto findById(@PathVariable Long id) {
        return classeService.findById(id);
    }

    @PostMapping("/save")
    public boolean create(@RequestBody  @Valid  AddEditClasseDto addEditClasseDto) {
        LOGGER.info(Messages.START_FUNCTION, "create");
        classeService.save(addEditClasseDto);
        return true;
    }

    @PutMapping("/update")
    public boolean update(@RequestBody @Valid  AddEditClasseDto addEditClasseDto) {
        LOGGER.info(Messages.START_FUNCTION, "update");
        return classeService.update(addEditClasseDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById");
        return classeService.deleteById(id);
    }

    @GetMapping("/all-by-year/{idYear}")
    public ResponseEntity<List<ClasseDto>> allByYear(@PathVariable Long idYear) {
        LOGGER.info(Messages.START_FUNCTION, "all");
        try {
            List<ClasseDto> classeDtos = classeService.allByYear(idYear);
            return new ResponseEntity<>(classeDtos, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get all classes", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-by-currentYear")
    public ResponseEntity<List<ClasseDto>> allByCurrentYear() {
        LOGGER.info(Messages.START_FUNCTION, "all By Current Year");
        try {
            List<ClasseDto> classeDtos = classeService.allByCurrentYear();
            return new ResponseEntity<>(classeDtos, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error get all classes By Current Year", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
