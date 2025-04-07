package com.pajiniweb.oriachad_backend.actions.controllers;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.SupportStudentDto;
import com.pajiniweb.oriachad_backend.actions.services.SupportStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/support-students")
public class SupportStudentController {

    @Autowired
    private SupportStudentService supportStudentService;

    @GetMapping("/all")
    public ResponseEntity<List<SupportStudentDto>> getAllSupportStudents() {
        List<SupportStudentDto> dtos = supportStudentService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<SupportStudentDto> getSupportStudentById(@PathVariable Long id) {
        Optional<SupportStudentDto> dtoOpt = supportStudentService.findById(id);
        return dtoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<SupportStudentDto> createSupportStudent(@RequestBody SupportStudentDto dto) {
        SupportStudentDto created = supportStudentService.save(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SupportStudentDto> updateSupportStudent(@PathVariable Long id, @RequestBody SupportStudentDto dto) {
        SupportStudentDto updated = supportStudentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupportStudent(@PathVariable Long id) {
        boolean deleted = supportStudentService.deleteById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/allByShedSetting/{idShedSetting}")
    public ResponseEntity<List<SupportStudentDto>> allByShedSetting(@PathVariable Long idShedSetting) {
      List<SupportStudentDto> allByShedSetting = supportStudentService.allByShedSetting(idShedSetting);
        return ResponseEntity.ok(allByShedSetting);
    }
}
