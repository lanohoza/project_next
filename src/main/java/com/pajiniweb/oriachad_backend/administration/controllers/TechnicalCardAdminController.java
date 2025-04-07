package com.pajiniweb.oriachad_backend.administration.controllers;

import com.pajiniweb.oriachad_backend.administration.domains.entities.AdminTechnicalCard;
import com.pajiniweb.oriachad_backend.administration.services.TechnicalCardAdminService;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditTechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.TechnicalCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/technical-cards-admin")
public class TechnicalCardAdminController {

    private static final Logger log = LoggerFactory.getLogger(TechnicalCardAdminController.class);

    @Autowired
    TechnicalCardAdminService technicalCardService;

    @PostMapping("/saveFromAdministration")
    public boolean createTechnicalCardFromAdmin(@RequestBody AddEditTechnicalCardDto taskDto) throws Exception {
        log.info(Messages.START_FUNCTION, "createTechnicalCardFromAdmin");
        return technicalCardService.createTechnicalCardFromAdmin(taskDto);
    }

    // Read by ID
    @GetMapping("/findById/{id}")
    public TechnicalCardDto getTechnicalCardById(@PathVariable Long id) throws Exception {
        log.info(Messages.START_FUNCTION, "getTechnicalCardById");
        return technicalCardService.findTechnicalCardsById(id);
    }

    @PutMapping("/updateFromAdmin/{id}")
    public boolean updateTechnicalCardFromAdmin(@PathVariable Long id, @RequestBody AddEditTechnicalCardDto addEditTechnicalCardDto) throws Exception {
        log.info(Messages.START_FUNCTION, "updateTechnicalCardFromAdmin");
        return technicalCardService.updateTechnicalCardFromAdmin(id, addEditTechnicalCardDto);
    }


    // Delete
    @DeleteMapping("/delete/{id}")
    public boolean deleteTechnicalCard(@PathVariable Long id) throws Exception {
        log.info(Messages.START_FUNCTION, "deleteTechnicalCard");
        return technicalCardService.deleteTechnicalCard(id);
    }

    // Read tasks by createdBy with pagination
    @GetMapping("/search")
    public Page<TechnicalCardDto> search(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "-1") Long idTcCategory,
            @RequestParam(defaultValue = "-1") int month,
            @RequestParam(required = false) TypeEstablishment typeEstablishment,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
        return technicalCardService.search(search, idTcCategory, month, typeEstablishment, page, size);
    }

    @PostMapping("/saveTcFromAdministrationToUser")
    public boolean saveTcFromAdministrationToUser(@RequestParam Long idTechnicalCard) throws Exception {
        log.info(Messages.START_FUNCTION, "saveTcFromAdministrationToUser");
        return technicalCardService.saveTcFromAdministrationToUser(idTechnicalCard);
    }
}
