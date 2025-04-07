package com.pajiniweb.oriachad_backend.controllers;

import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardCategoryDto;
import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCardCategory;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.services.TechnicalCardCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/technical-card-categories")
public class TechnicalCardCategoriesController {

	private static final Logger log = LoggerFactory.getLogger(TechnicalCardCategoriesController.class);

	@Autowired
	private TechnicalCardCategoryService technicalCardCategoryService;

	// Create task category
  /*  @PostMapping("/create")
    public TechnicalCardCategory createTaskCategory(@RequestBody TechnicalCardCategory technicalCardCategory) {
        log.info(Messages.START_FUNCTION, "createTaskCategory");
        return technicalCardCategoryService.createTaskCategory(technicalCardCategory);
    }*/

	// Read all task categories
	@GetMapping("/all")
	public List<TechnicalCardCategoryDto> getAllTaskCategories() {
		log.info(Messages.START_FUNCTION, "getAllTaskCategories");
		return technicalCardCategoryService.getAllCategories();
	}

	// Read task category by ID
 /*   @GetMapping("/getTaskCategoryById/{id}")
    public TechnicalCardCategory getTaskCategoryById(@PathVariable Long id) {
        log.info(Messages.START_FUNCTION, "getTaskCategoryById");
        Optional<TechnicalCardCategory> taskCategory = technicalCardCategoryService.getTaskCategoryById(id);
        return taskCategory.orElse(null);
    }

    // Update task category
    @PutMapping("/update/{id}")
    public TechnicalCardCategory updateTaskCategory(@PathVariable Long id, @RequestBody TechnicalCardCategory technicalCardCategoryDetails) {
        log.info(Messages.START_FUNCTION, "updateTaskCategory");
        return technicalCardCategoryService.updateTaskCategory(id, technicalCardCategoryDetails);
    }

    // Delete task category
    @DeleteMapping("/delete/{id}")
    public boolean deleteTaskCategory(@PathVariable Long id) {
        log.info(Messages.START_FUNCTION, "deleteTaskCategory");
        return technicalCardCategoryService.deleteTaskCategory(id);
    }*/
}
