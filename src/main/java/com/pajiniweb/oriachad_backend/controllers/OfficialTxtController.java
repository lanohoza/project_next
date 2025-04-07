package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.OfficialTxtDto;
import com.pajiniweb.oriachad_backend.domains.entities.OfficialTxt;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.OfficialTxtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/official-txts")
public class OfficialTxtController {

    private static final Logger log = LoggerFactory.getLogger(OfficialTxtController.class);

    @Autowired
    OfficialTxtService officialTxtService;

    // Read all
    @GetMapping("/all")
    public List<OfficialTxtDto> getAllOfficialTxts() {
        log.info(Messages.START_FUNCTION, "getAllOfficialTxts");
        return officialTxtService.getAllOfficialTxts();
    }

    // Get by ID
    @GetMapping("/getById/{id}")
    public OfficialTxtDto getOfficialTxtById(@PathVariable Long id) {
        log.info(Messages.START_FUNCTION, "getOfficialTxtById");
        return officialTxtService.getOfficialTxtById(id)
                .orElseThrow(() -> new RuntimeException(Messages.ENTITY_IS_NOT_FOUND));
    }

    // Create
    @PostMapping("/save")
    public OfficialTxtDto createOfficialTxt(@RequestBody OfficialTxtDto officialTxtDto) {
        log.info(Messages.START_FUNCTION, "createOfficialTxt");
        return officialTxtService.createOfficialTxt(officialTxtDto);
    }

    // Update
    @PutMapping("/update/{id}")
    public OfficialTxtDto updateOfficialTxt(@PathVariable Long id, @RequestBody OfficialTxtDto officialTxtDto) {
        log.info(Messages.START_FUNCTION, "updateOfficialTxt");
        return officialTxtService.updateOfficialTxt(id, officialTxtDto);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public boolean deleteOfficialTxt(@PathVariable Long id) {
        log.info(Messages.START_FUNCTION, "deleteOfficialTxt");
        return officialTxtService.deleteOfficialTxt(id);
    }
}
