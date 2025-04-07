package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.EstablishmentDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.EstablishmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/establishments")
public class EstablishmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EstablishmentController.class);

    @Autowired
    private EstablishmentService  establishmentService;

    @GetMapping("/all")
    public List<EstablishmentDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll Etablissement");
        return establishmentService.findAll();
    }

    @GetMapping("/findById/{id}")
    public EstablishmentDto findById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById Etablissement");
        return establishmentService.findById(id);
    }

    @GetMapping("/findByIdCommune/{idCommune}")
    public List<EstablishmentDto> findByIdCommune(@PathVariable Long idCommune) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdCommune Etablissement");
        return establishmentService.findByIdCommune(idCommune);
    }

    @PostMapping("/save")
    public EstablishmentDto create(@RequestBody EstablishmentDto establishmentDTO) {
        LOGGER.info(Messages.START_FUNCTION, "create Etablissement");
        return establishmentService.save(establishmentDTO);
    }

    @PutMapping("/update/{id}")
    public EstablishmentDto update(@PathVariable Long id, @RequestBody EstablishmentDto establishmentDTO) {
        LOGGER.info(Messages.START_FUNCTION, "update Etablissement");
        return establishmentService.update(id, establishmentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById Etablissement");
        return establishmentService.deleteById(id);
    }
}
