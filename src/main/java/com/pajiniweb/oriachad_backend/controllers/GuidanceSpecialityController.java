package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.GuidanceSpecialityDto;
import com.pajiniweb.oriachad_backend.domains.dtos.SpecialityDto;
import com.pajiniweb.oriachad_backend.domains.entities.Speciality;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.GuidanceSpecialityService;
import com.pajiniweb.oriachad_backend.services.SpecialityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guidance_specialitie")
public class GuidanceSpecialityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuidanceSpecialityController.class);

    @Autowired
    GuidanceSpecialityService guidanceSpecialityService;



    @GetMapping("/classe/{idCalsse}")
    public List<GuidanceSpecialityDto> getSpecialtyForClasseDesire(@PathVariable Long idCalsse) {
        LOGGER.info(Messages.START_FUNCTION, "findById");
        return guidanceSpecialityService.getSpecialtyForClasseDesire(idCalsse);

    }

    @GetMapping("/admin/establishment_type/{type}")
    public List<GuidanceSpecialityDto> getAdminSpecialtyForDesire(@PathVariable TypeEstablishment type) {
        LOGGER.info(Messages.START_FUNCTION, "findById");
        return guidanceSpecialityService.getAdminSpecialtyForDesire(type);

    }


}
