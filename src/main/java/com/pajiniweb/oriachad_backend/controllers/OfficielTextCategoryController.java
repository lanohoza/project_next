package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.OfficielTextCategoryDto;
import com.pajiniweb.oriachad_backend.domains.entities.OfficielTextCategory;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.OfficielTextCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/official-txts-category")
public class OfficielTextCategoryController {

    private static final Logger log = LoggerFactory.getLogger(OfficialTxtController.class);

    @Autowired
    OfficielTextCategoryService officielTextCategoryService;

    // Read all
    @GetMapping("/all")
    public List<OfficielTextCategoryDto> getAllOfficialTxtsCategory() {
        log.info(Messages.START_FUNCTION, "getAllOfficialTxtsCategory");
        return officielTextCategoryService.getAllOfficialTxtsCategory();
    }
}
