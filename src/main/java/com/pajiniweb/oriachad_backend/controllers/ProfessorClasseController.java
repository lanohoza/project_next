package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditProfessorClasseDto;
import com.pajiniweb.oriachad_backend.services.ProfessorClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professor-classes")
public class ProfessorClasseController {
    @Autowired
    private ProfessorClasseService professorClasseService;

    @GetMapping("/all")
    public List<AddEditProfessorClasseDto> findAll() {
        return professorClasseService.findAll();
    }

    @GetMapping("/findById/{id}")
    public AddEditProfessorClasseDto findById(@PathVariable Long id) {
        return professorClasseService.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public AddEditProfessorClasseDto save(@RequestBody AddEditProfessorClasseDto dto) {
        return professorClasseService.save(dto);
    }

    @PostMapping("/saveAll")
    public List<AddEditProfessorClasseDto> saveAll(@RequestBody List<AddEditProfessorClasseDto> dtos) {
        return professorClasseService.saveAll(dtos);
    }

    @PutMapping("/update/{id}")
    public AddEditProfessorClasseDto update(@PathVariable Long id, @RequestBody AddEditProfessorClasseDto dto) {
        return professorClasseService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return professorClasseService.deleteById(id);
    }
}
