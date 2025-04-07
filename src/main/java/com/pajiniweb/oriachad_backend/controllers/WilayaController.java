package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.WilayaDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.WilayaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wilayas")
public class WilayaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WilayaController.class);

    @Autowired
    private WilayaService wilayaService;

    @GetMapping("/all")
    public List<WilayaDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll Wilaya");
        return wilayaService.findAll();
    }

    @GetMapping("/findById/{id}")
    public WilayaDto findById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById Wilaya");
        return wilayaService.findById(id);
    }

    @PostMapping("/save")
    public WilayaDto create(@RequestBody WilayaDto wilayaDTO) {
        LOGGER.info(Messages.START_FUNCTION, "create Wilaya");
        return wilayaService.save(wilayaDTO);
    }

    @PutMapping("/update/{id}")
    public WilayaDto update(@PathVariable Long id, @RequestBody WilayaDto wilayaDTO) {
        LOGGER.info(Messages.START_FUNCTION, "update Wilaya");
        return wilayaService.update(id, wilayaDTO);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById Wilaya");
        return wilayaService.deleteById(id);
    }
}
