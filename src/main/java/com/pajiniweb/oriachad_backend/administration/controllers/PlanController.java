package com.pajiniweb.oriachad_backend.administration.controllers;

import com.pajiniweb.oriachad_backend.administration.domains.entities.Plan;
import com.pajiniweb.oriachad_backend.administration.services.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/plans")
public class PlanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    private PlanService planService;

    @GetMapping("/all")
    public ResponseEntity<List<Plan>> getAllPlans() {
        LOGGER.info("Received request to get all plans");
        List<Plan> plans = planService.findAll();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        LOGGER.info("Received request to get plan by id: {}", id);
        Optional<Plan> plan = planService.findById(id);
        return plan.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        LOGGER.info("Received request to create a new plan: {}", plan);
        Plan savedPlan = planService.save(plan);
        return ResponseEntity.status(201).body(savedPlan);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody Plan plan) {
        LOGGER.info("Received request to update plan with id: {}", id);
        try {
            Plan updatedPlan = planService.update(id, plan);
            return ResponseEntity.ok(updatedPlan);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        LOGGER.info("Received request to delete plan with id: {}", id);
        if (planService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
