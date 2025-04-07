package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.HumanToolDto;
import com.pajiniweb.oriachad_backend.services.HumanToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/human-tools")
public class HumanToolController {

    @Autowired
    private HumanToolService humanToolService;

    @GetMapping("/all")
    public Page<HumanToolDto> getAllHumanTools(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return humanToolService.getAllHumanTools(PageRequest.of(page, size));
    }


    @PostMapping("/save")
    public boolean save(@RequestBody HumanToolDto humanToolDTO) {
        return humanToolService.save(humanToolDTO);
    }

    @PostMapping("/saveFromAdministration")
    public boolean saveFromAdmin(@RequestBody HumanToolDto humanToolDTO) {
        return humanToolService.saveFromAdmin(humanToolDTO);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteHumanTool(@PathVariable Long id) {
        return humanToolService.deleteHumanTool(id);
    }

    @GetMapping("/created-by")
    public Page<HumanToolDto> getHumanToolsByCreatedBy(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return humanToolService.getHumanToolsByCreatedBy(PageRequest.of(page, size));
    }
    @GetMapping("/all-created-by")
    public List<HumanToolDto> getAllHumanToolsByCreatedBy() {
        return humanToolService.getAllHumanToolsByCreatedBy();
    }

    @GetMapping("/all-created-by-admin")
    public List<HumanToolDto> getAllHumanToolsCreatedByAdmin() {
        return humanToolService.getAllHumanToolsCreatedByAdmin();
    }
}
