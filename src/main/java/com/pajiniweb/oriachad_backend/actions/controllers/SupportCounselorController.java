package com.pajiniweb.oriachad_backend.actions.controllers;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.SupportCounselorDto;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.SupportStudentDto;
import com.pajiniweb.oriachad_backend.actions.services.SupportCounselorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/support-counselors")
public class SupportCounselorController {

    @Autowired
    private SupportCounselorService supportCounselorService;

    @GetMapping("/all")
    public ResponseEntity<List<SupportCounselorDto>> getAllSupportCounselors() {
        List<SupportCounselorDto> dtos = supportCounselorService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<SupportCounselorDto> getSupportCounselorById(@PathVariable Long id) {
        Optional<SupportCounselorDto> dtoOpt = supportCounselorService.findById(id);
        return dtoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<SupportCounselorDto> createSupportCounselor(@RequestBody SupportCounselorDto dto) {
        SupportCounselorDto created = supportCounselorService.save(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SupportCounselorDto> updateSupportCounselor(@PathVariable Long id, @RequestBody SupportCounselorDto dto) {
        SupportCounselorDto updated = supportCounselorService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupportCounselor(@PathVariable Long id) {
        boolean deleted = supportCounselorService.deleteById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/allByShedSetting/{idShedSetting}")
    public ResponseEntity<List<SupportCounselorDto>> allByShedSetting(@PathVariable Long idShedSetting) {
        List<SupportCounselorDto> allByShedSetting = supportCounselorService.allByShedSetting(idShedSetting);
        return ResponseEntity.ok(allByShedSetting);
    }
}
