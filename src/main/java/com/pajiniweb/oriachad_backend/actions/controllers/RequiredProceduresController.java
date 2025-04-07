package com.pajiniweb.oriachad_backend.actions.controllers;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.RequiredProceduresDto;
import com.pajiniweb.oriachad_backend.actions.services.RequiredProceduresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/required-procedures")
public class RequiredProceduresController {

    @Autowired
    private RequiredProceduresService requiredProceduresService;

    @GetMapping("/all")
    public ResponseEntity<List<RequiredProceduresDto>> getAllRequiredProcedures() {
        List<RequiredProceduresDto> dtos = requiredProceduresService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<RequiredProceduresDto> getRequiredProceduresById(@PathVariable Long id) {
        Optional<RequiredProceduresDto> dtoOpt = requiredProceduresService.findById(id);
        return dtoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<RequiredProceduresDto> createRequiredProcedures(@RequestBody RequiredProceduresDto dto) {
        RequiredProceduresDto created = requiredProceduresService.save(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RequiredProceduresDto> updateRequiredProcedures(@PathVariable Long id,
                                                                          @RequestBody RequiredProceduresDto dto) {
        RequiredProceduresDto updated = requiredProceduresService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRequiredProcedures(@PathVariable Long id) {
        boolean deleted = requiredProceduresService.deleteById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
