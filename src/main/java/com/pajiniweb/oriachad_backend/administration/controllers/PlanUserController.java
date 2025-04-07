package com.pajiniweb.oriachad_backend.administration.controllers;

import com.pajiniweb.oriachad_backend.administration.domains.dtos.PlanUserAddEditDto;
import com.pajiniweb.oriachad_backend.administration.domains.entities.PlanUser; // Adjust import according to your entity
import com.pajiniweb.oriachad_backend.administration.services.PlanUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plan-users")
public class PlanUserController {
    @Autowired
    private PlanUserService planUserService;

    @GetMapping("/all")
    public ResponseEntity<List<PlanUserAddEditDto>> getAll() {
        List<PlanUserAddEditDto> planUsers = planUserService.findAll();
        return ResponseEntity.ok(planUsers);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<PlanUser> getById(@PathVariable Long id) {
        return planUserService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<PlanUser> create(@RequestBody PlanUserAddEditDto planUserDto) {
        PlanUser planUser = planUserService.save(planUserDto);
        return ResponseEntity.ok(planUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PlanUser> update(@PathVariable Long id, @RequestBody PlanUserAddEditDto planUserDto) {
        return ResponseEntity.ok(planUserService.update(id, planUserDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return planUserService.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<PlanUserAddEditDto>> getByIdUser(@PathVariable Long idUser) {
        List<PlanUserAddEditDto> planUsers = planUserService.findByIdUser(idUser);
        return ResponseEntity.ok(planUsers);
    }

    @GetMapping("/plan/{idPlan}")
    public ResponseEntity<List<PlanUserAddEditDto>> getByIdPlan(@PathVariable Long idPlan) {
        List<PlanUserAddEditDto> planUsers = planUserService.findByIdPlan(idPlan);
        return ResponseEntity.ok(planUsers);
    }

    @GetMapping("/user/{idUser}/plan/{idPlan}")
    public ResponseEntity<List<PlanUserAddEditDto>> getByIdUserAndIdPlan(@PathVariable Long idUser, @PathVariable Long idPlan) {
        List<PlanUserAddEditDto> planUsers = planUserService.findByIdUserAndIdPlan(idUser, idPlan);
        return ResponseEntity.ok(planUsers);
    }
}
