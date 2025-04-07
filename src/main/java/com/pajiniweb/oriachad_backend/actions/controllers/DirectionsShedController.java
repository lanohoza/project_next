package com.pajiniweb.oriachad_backend.actions.controllers;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.DirectionsShedDto;
import com.pajiniweb.oriachad_backend.actions.services.DirectionsShedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/directions-shed")
public class DirectionsShedController {

    @Autowired
    private DirectionsShedService directionsShedService;

    @GetMapping("/all")
    public ResponseEntity<List<DirectionsShedDto>> getAllDirectionsShed() {
        return ResponseEntity.ok(directionsShedService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<DirectionsShedDto> getDirectionsShedById(@PathVariable Long id) {
        Optional<DirectionsShedDto> directionsShed = directionsShedService.findById(id);
        return directionsShed.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<DirectionsShedDto> createDirectionsShed(@RequestBody DirectionsShedDto directionsShedDto) {
        return ResponseEntity.ok(directionsShedService.save(directionsShedDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DirectionsShedDto> updateDirectionsShed(@PathVariable Long id, @RequestBody DirectionsShedDto directionsShedDto) {
        return ResponseEntity.ok(directionsShedService.update(id, directionsShedDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDirectionsShed(@PathVariable Long id) {
        return directionsShedService.deleteById(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
