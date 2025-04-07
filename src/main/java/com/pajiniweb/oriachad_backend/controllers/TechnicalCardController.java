package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditTechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardYearDto;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.TechnicalCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/technical-cards")
public class TechnicalCardController {

	private static final Logger log = LoggerFactory.getLogger(TechnicalCardController.class);

	@Autowired
	TechnicalCardService technicalCardService;

	// Create
	@PostMapping("/save")
	public boolean createTechnicalCard(@RequestBody AddEditTechnicalCardDto taskDto) throws Exception {
		log.info(Messages.START_FUNCTION, "createTechnicalCard");
		return technicalCardService.createTechnicalCard(taskDto);
	}

	// Read all
    /*@GetMapping("/findAll")
    public List<TechnicalCard> getAllTechnicalCards() {
        log.info(Messages.START_FUNCTION, "getAllTechnicalCards");
        return technicalCardService.findAllTechnicalCards();
    }*/

	// Read by ID
	@GetMapping("/findById/{id}")
	public TechnicalCardDto getTechnicalCardById(@PathVariable Long id) throws Exception {
		log.info(Messages.START_FUNCTION, "getTechnicalCardById");
		return technicalCardService.findTechnicalCardsById(id);
	}

	// Update
	@PutMapping("/update/{id}")
	public boolean updateTechnicalCard(@PathVariable Long id, @RequestBody AddEditTechnicalCardDto addEditTechnicalCardDto) throws Exception {
		log.info(Messages.START_FUNCTION, "updateTechnicalCard");
		return technicalCardService.updateTechnicalCard(id, addEditTechnicalCardDto);
	}


	// Delete
	@DeleteMapping("/delete/{id}")
	public boolean deleteTechnicalCard(@PathVariable Long id) throws Exception {
		log.info(Messages.START_FUNCTION, "deleteTechnicalCard");
		return technicalCardService.deleteTechnicalCard(id);
	}

	// Read tasks by createdBy with pagination
	@GetMapping("/search")
	public Page<TechnicalCardDto> search(@RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Long idTcCategory, @RequestParam(defaultValue = "-1") int month, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		log.info(Messages.START_FUNCTION, "findTechnicalCardsByCreatedBy");
		return technicalCardService.search(search, idTcCategory, month, page, size);
	}


}
