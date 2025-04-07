package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.LevelDto;
import com.pajiniweb.oriachad_backend.domains.entities.Level;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.LevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/levels")
public class LevelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LevelController.class);

    @Autowired
    LevelService levelService;

    @GetMapping("/all")
    public List<LevelDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll");
        return levelService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Level findById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById");
        return levelService.findById(id)
                .orElseThrow(() -> new RuntimeException("Level not found with id: " + id));
    }

    @PostMapping("/save")
    public Level create(@RequestBody Level level) {
        LOGGER.info(Messages.START_FUNCTION, "create");
        return levelService.save(level);
    }

    @PutMapping("/update/{id}")
    public Level update(@PathVariable Long id, @RequestBody Level level) {
        LOGGER.info(Messages.START_FUNCTION, "update");
        return levelService.update(id, level);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById");
        return levelService.deleteById(id);
    }

    @GetMapping("/admin/all")
    public List<LevelDto> findAdminAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll");
        return levelService.findAdminAll();
    }
}
