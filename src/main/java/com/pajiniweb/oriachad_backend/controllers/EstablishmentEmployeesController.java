package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditEstablishmentEmployeesDto;
import com.pajiniweb.oriachad_backend.services.EstablishmentEmployeesService;
import com.pajiniweb.oriachad_backend.helps.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/establishment-employees")
public class EstablishmentEmployeesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstablishmentEmployeesController.class);

    private final EstablishmentEmployeesService establishmentEmployeesService;

    public EstablishmentEmployeesController(EstablishmentEmployeesService establishmentEmployeesService) {
        this.establishmentEmployeesService = establishmentEmployeesService;
    }

    @GetMapping("/all")
    public List<AddEditEstablishmentEmployeesDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll EstablishmentEmployees");
        return establishmentEmployeesService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Optional<AddEditEstablishmentEmployeesDto> findById(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById EstablishmentEmployees with ID {}", id);
        return establishmentEmployeesService.findById(id);
    }

    @PostMapping("/create")
    public AddEditEstablishmentEmployeesDto create(@RequestBody AddEditEstablishmentEmployeesDto dto) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "create EstablishmentEmployees");
        return establishmentEmployeesService.save(dto);
    }

    @PutMapping("/update/{id}")
    public AddEditEstablishmentEmployeesDto update(@PathVariable Long id, @RequestBody AddEditEstablishmentEmployeesDto dto) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "update EstablishmentEmployees with ID {}", id);
        return establishmentEmployeesService.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        LOGGER.info(Messages.START_FUNCTION, "delete EstablishmentEmployees with ID {}", id);
        return establishmentEmployeesService.deleteById(id);
    }

    @GetMapping("/findByUser")
    public Page<AddEditEstablishmentEmployeesDto> getEmployeesByCreatedBy(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        return establishmentEmployeesService.findByCreatedBy(page,size);
    }

    @GetMapping("/search")
    public Page<AddEditEstablishmentEmployeesDto> search(
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
        return establishmentEmployeesService.search(search, pageable);
    }
}
