package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.YearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/years")
public class YearController {

    private static final Logger log = LoggerFactory.getLogger(YearController.class);

    @Autowired
    private YearService yearService;

    // Create
    @PostMapping("/save")
    public Year createYear(@RequestBody Year year) {
        log.info(Messages.START_FUNCTION, "createYear");
        return yearService.createYear(year);
    }

    // Read all
    @GetMapping("/all")
    public  ResponseEntity<?> getAllYears() {
        log.info(Messages.START_FUNCTION, "getAllYears");
        return  ResponseEntity.ok(yearService.getAllYears());
    }

    // Read by ID
    @GetMapping("/getYearById/{id}")
    public Year getYearById(@PathVariable Long id) {
        log.info(Messages.START_FUNCTION, "getYearById");
        Optional<Year> year = yearService.getYearById(id);
        return year.orElse(null);
    }

    // Update
    @PutMapping("/updateYear/{id}")
    public Year updateYear(@PathVariable Long id, @RequestBody Year yearDetails) {
        log.info(Messages.START_FUNCTION, "updateYear");
        return yearService.updateYear(id, yearDetails);
    }

    // Delete
    @DeleteMapping("/deleteYear/{id}")
    public boolean deleteYear(@PathVariable Long id) {
        log.info(Messages.START_FUNCTION, "deleteYear");
        return yearService.deleteYear(id);
    }

    @GetMapping("/current")
    public Optional<Year> getCurrentYear() {
       // return yearService.getCurrentYear();
        return  null;
    }
}
