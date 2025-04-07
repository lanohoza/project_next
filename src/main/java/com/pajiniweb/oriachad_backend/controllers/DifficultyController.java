package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.DifficultyDto;
import com.pajiniweb.oriachad_backend.services.DifficultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/difficulties")
public class DifficultyController {

    @Autowired
    DifficultyService difficultyService;

    @PostMapping("/save")
    public DifficultyDto createDifficulty(@RequestBody DifficultyDto difficultyDTO) {
        return difficultyService.createDifficulty(difficultyDTO);
    }

    @PutMapping("/update/{id}")
    public DifficultyDto updateDifficulty(@PathVariable Long id, @RequestBody DifficultyDto difficultyDTO) {
        return difficultyService.updateDifficulty(id, difficultyDTO);
    }

    @GetMapping("/getById/{id}")
    public DifficultyDto getDifficulty(@PathVariable Long id) {
        return difficultyService.getDifficulty(id);
    }

    @GetMapping("/all")
    public List<DifficultyDto> getAllDifficulties() {
        return difficultyService.getAllDifficulties();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteDifficulty(@PathVariable Long id) {
        return difficultyService.deleteDifficulty(id);
    }
}
