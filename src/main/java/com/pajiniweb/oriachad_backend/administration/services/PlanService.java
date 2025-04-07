package com.pajiniweb.oriachad_backend.administration.services;

import com.pajiniweb.oriachad_backend.administration.domains.entities.Plan;
import com.pajiniweb.oriachad_backend.administration.repositories.PlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanService.class);

    @Autowired
    private PlanRepository planRepository;

    public List<Plan> findAll() {
        LOGGER.info("Starting to fetch all plans");
        try {
            return planRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Error fetching all plans", e);
            throw new RuntimeException("Could not fetch plans");
        }
    }

    public Optional<Plan> findById(Long id) {
        LOGGER.info("Fetching plan by id: {}", id);
        try {
            return planRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error("Error fetching plan by id", e);
            throw e;
        }
    }

    public Plan save(Plan plan) {
        LOGGER.info("Saving plan: {}", plan);
        try {
            return planRepository.save(plan);
        } catch (Exception e) {
            LOGGER.error("Error saving plan", e);
            throw e;
        }
    }

    public Plan update(Long id, Plan plan) {
        LOGGER.info("Updating plan with id: {}", id);
        try {
            if (!planRepository.existsById(id)) {
                LOGGER.error("Plan with id {} does not exist", id);
                throw new IllegalArgumentException("Plan not found");
            }
            plan.setId(id); // Ensure the ID is set for update
            return planRepository.save(plan);
        } catch (Exception e) {
            LOGGER.error("Error updating plan", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info("Deleting plan with id: {}", id);
        try {
            if (planRepository.existsById(id)) {
                planRepository.deleteById(id);
                LOGGER.info("Successfully deleted plan with id: {}", id);
                return true;
            } else {
                LOGGER.warn("Plan with id {} does not exist", id);
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting plan", e);
            throw e;
        }
    }
}
