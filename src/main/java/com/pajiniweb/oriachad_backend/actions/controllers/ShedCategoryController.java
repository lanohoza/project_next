package com.pajiniweb.oriachad_backend.actions.controllers;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedCategoryDto;
import com.pajiniweb.oriachad_backend.actions.services.ShedCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shed-categories")
public class ShedCategoryController {

    @Autowired
    private ShedCategoryService shedCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<ShedCategoryDto>> getAllShedCategories() {
        List<ShedCategoryDto> dtos = shedCategoryService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ShedCategoryDto> getShedCategoryById(@PathVariable Long id) {
        Optional<ShedCategoryDto> dtoOpt = shedCategoryService.findById(id);
        return dtoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ShedCategoryDto> createShedCategory(@RequestBody ShedCategoryDto dto) {
        ShedCategoryDto created = shedCategoryService.save(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShedCategoryDto> updateShedCategory(@PathVariable Long id, @RequestBody ShedCategoryDto dto) {
        ShedCategoryDto updated = shedCategoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShedCategory(@PathVariable Long id) {
        boolean deleted = shedCategoryService.deleteById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
