package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.SolutionDto;
import com.pajiniweb.oriachad_backend.services.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solutions")
public class SolutionController {

    @Autowired
    SolutionService solutionService;

    @PostMapping("/save")
    public SolutionDto createSolution(@RequestBody SolutionDto solutionDTO) {
        return solutionService.createSolution(solutionDTO);
    }

    @PutMapping("/update/{id}")
    public SolutionDto updateSolution(@PathVariable Long id, @RequestBody SolutionDto solutionDTO) {
        return solutionService.updateSolution(id, solutionDTO);
    }

    @GetMapping("/findById/{id}")
    public SolutionDto getSolution(@PathVariable Long id) {
        return solutionService.getSolution(id);
    }

    @GetMapping("/all")
    public List<SolutionDto> getAllSolutions() {
        return solutionService.getAllSolutions();
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteSolution(@PathVariable Long id) {
        return solutionService.deleteSolution(id);
    }
}
