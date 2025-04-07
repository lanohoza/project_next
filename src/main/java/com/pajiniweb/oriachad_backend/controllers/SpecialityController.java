package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.SpecialityDto;
import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.SpecialityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialities")
public class SpecialityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialityController.class);

    @Autowired
    SpecialityService specialityService;

    @GetMapping("/all")
    public List<SpecialityDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll");
        return specialityService.findAll();
    }

    @GetMapping("/getAllByIdLevel/{idLevel}")
    public List<SpecialityDto> getAllByIdLevel(@PathVariable Long idLevel) {
        LOGGER.info(Messages.START_FUNCTION, "findAll");
        return specialityService.getAllByIdLevel(idLevel);
    }

    @GetMapping("/findById/{id}")
    public SpecialityDto findById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById");
        return specialityService.findById(id)
                .orElseThrow(() -> new RuntimeException("Speciality not found with id: " + id));
    }
    @GetMapping("/get-for-desires")
    public List<SpecialityDto> getSpecialtyForDesire() {
        LOGGER.info(Messages.START_FUNCTION, "findById");
        return specialityService.getSpecialtyForDesire();

    }


    @PostMapping("/save")
    public Speciality create(@RequestBody Speciality speciality) {
        LOGGER.info(Messages.START_FUNCTION, "create");
        return specialityService.save(speciality);
    }

    @PutMapping("/update/{id}")
    public Speciality update(@PathVariable Long id, @RequestBody Speciality speciality) {
        LOGGER.info(Messages.START_FUNCTION, "update");
        return specialityService.update(id, speciality);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById");
        return specialityService.deleteById(id);
    }
}
