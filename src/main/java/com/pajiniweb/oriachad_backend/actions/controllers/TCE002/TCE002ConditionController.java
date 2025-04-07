package com.pajiniweb.oriachad_backend.actions.controllers.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002.TCE002ConditionToDisplayDto;
import com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002ConditionDto;
import com.pajiniweb.oriachad_backend.actions.services.TCE002.TCE002ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tce002-conditions")
public class TCE002ConditionController {

    @Autowired
    private TCE002ConditionService TCE002ConditionService;

    @GetMapping("/all")
    public ResponseEntity<List<TCE002ConditionDto>> getAllTCE002Conditions() {
        List<TCE002ConditionDto> dtos = TCE002ConditionService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TCE002ConditionToDisplayDto> getTCE002ConditionById(@PathVariable Long id) {
        Optional<TCE002ConditionToDisplayDto> dtoOpt = TCE002ConditionService.findById(id);
        return dtoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<TCE002ConditionDto> createTCE002Condition(@RequestBody TCE002ConditionDto dto) {
        TCE002ConditionDto created = TCE002ConditionService.save(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TCE002ConditionDto> updateTCE002Condition(@PathVariable Long id,
                                                                    @RequestBody TCE002ConditionDto dto) {
        TCE002ConditionDto updated = TCE002ConditionService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTCE002Condition(@PathVariable Long id) {
        boolean deleted = TCE002ConditionService.deleteById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public Page<TCE002ConditionToDisplayDto> search(
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        return TCE002ConditionService.search(search, pageable);
    }
}
