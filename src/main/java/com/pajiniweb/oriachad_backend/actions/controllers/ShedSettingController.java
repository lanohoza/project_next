package com.pajiniweb.oriachad_backend.actions.controllers;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingConditionDto;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingDto;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingToDisplayDto;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.ShedSettingWithSupportDto;
import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.DiagnosticType;
import com.pajiniweb.oriachad_backend.actions.services.ShedSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shed-settings")
public class ShedSettingController {

    @Autowired
    private ShedSettingService shedSettingService;

    @GetMapping("/all")
    public ResponseEntity<Page<ShedSettingDto>> getAllShedSettings(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        return ResponseEntity.ok(shedSettingService.findAll(pageable));
    }


    @GetMapping("/findById/{id}")
    public ResponseEntity<ShedSettingDto> getShedSettingById(@PathVariable Long id) {
        Optional<ShedSettingDto> shedSetting = shedSettingService.findById(id);
        return shedSetting.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ShedSettingDto> createShedSetting(@RequestBody ShedSettingDto shedSettingDto) {
        return ResponseEntity.ok(shedSettingService.save(shedSettingDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShedSettingDto> updateShedSetting(@PathVariable Long id, @RequestBody ShedSettingDto shedSettingDto) {
        return ResponseEntity.ok(shedSettingService.update(id, shedSettingDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShedSetting(@PathVariable Long id) {
        return shedSettingService.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public Page<ShedSettingToDisplayDto> search(@RequestParam(name = "search", defaultValue = "") String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        return shedSettingService.search(search, pageable);
    }

    @GetMapping("/allByTargetAndCategory")
    public ResponseEntity<List<ShedSettingConditionDto>> searchByTargetAndCategory(@RequestParam("target") DiagnosticType target, @RequestParam(value = "idShedCategory", defaultValue = "-1") Long idShedCategory) {
        List<ShedSettingConditionDto> dtos = shedSettingService.findByTargetAndCategory(target, idShedCategory);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/allFilter")
    public ResponseEntity<List<ShedSettingConditionDto>> allFilter(@RequestParam("target") DiagnosticType target, @RequestParam(value = "idSpeciality", defaultValue = "-1") Long idSpeciality, @RequestParam(value = "idShedCategory", defaultValue = "-1") Long idShedCategory) {
        List<ShedSettingConditionDto> dtos = shedSettingService.allFilter(target, idSpeciality, idShedCategory);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/allByCategory/{idShedCategory}")
    public ResponseEntity<List<ShedSettingConditionDto>> allByCategory(@PathVariable Long idShedCategory) {
        List<ShedSettingConditionDto> dtos = shedSettingService.allByCategory(idShedCategory);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/getByInterview/{idInterview}")
    public ResponseEntity<List<ShedSettingWithSupportDto>> getByInterview(@PathVariable Long idInterview) {
        List<ShedSettingWithSupportDto> dtos = shedSettingService.getByInterview(idInterview);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/getByFollowUp/{idFollowup}")
    public ResponseEntity<List<ShedSettingWithSupportDto>> getByFollowUp(@PathVariable Long idFollowup) {
        List<ShedSettingWithSupportDto> dtos = shedSettingService.getByFollowUp(idFollowup);
        return ResponseEntity.ok(dtos);
    }
}
