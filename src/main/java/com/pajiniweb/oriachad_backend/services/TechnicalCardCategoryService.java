package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardCategoryDto;
import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCardCategory;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.TechnicalCardCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalCardCategoryService {

	private static final Logger log = LoggerFactory.getLogger(TechnicalCardCategoryService.class);

	@Autowired
	private TechnicalCardCategoryRepository technicalCardCategoryRepository;

	// Create
	public TechnicalCardCategory createTaskCategory(TechnicalCardCategory technicalCardCategory) {
		log.info(Messages.START_FUNCTION, "createTaskCategory");
		try {
			TechnicalCardCategory createdTechnicalCardCategory = technicalCardCategoryRepository.save(technicalCardCategory);
			log.info(Messages.PROCESS_SUCCESSFULLY, "createTaskCategory");
			return createdTechnicalCardCategory;
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "createTaskCategory", e);
			return null;
		}
	}

	// Read all
	public List<TechnicalCardCategoryDto> getAllCategories() {
		log.info(Messages.START_FUNCTION, "getAllTaskCategories");
		return technicalCardCategoryRepository.findAll().stream().map((category) -> TechnicalCardCategoryDto.builder().name(category.getName()).id(category.getId()).build()).toList();
	}

	// Read by ID
	public Optional<TechnicalCardCategory> getTaskCategoryById(Long id) {
		log.info(Messages.START_FUNCTION, "getTaskCategoryById");
		try {
			Optional<TechnicalCardCategory> taskCategory = technicalCardCategoryRepository.findById(id);
			if (taskCategory.isPresent()) {
				log.info(Messages.ENTITY_IS_FOUND, "taskCategory");
			} else {
				log.warn(Messages.ENTITY_IS_NOT_FOUND, "taskCategory");
			}
			return taskCategory;
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "getTaskCategoryById", e);
			return Optional.empty();
		}
	}

	// Update
	public TechnicalCardCategory updateTaskCategory(Long id, TechnicalCardCategory technicalCardCategoryDetails) {
		log.info(Messages.START_FUNCTION, "updateTaskCategory");
		try {
			Optional<TechnicalCardCategory> taskCategoryOptional = technicalCardCategoryRepository.findById(id);
			if (taskCategoryOptional.isPresent()) {
				TechnicalCardCategory technicalCardCategory = taskCategoryOptional.get();
				technicalCardCategory.setName(technicalCardCategoryDetails.getName());
				TechnicalCardCategory updatedTechnicalCardCategory = technicalCardCategoryRepository.save(technicalCardCategory);
				log.info(Messages.PROCESS_SUCCESSFULLY, "updateTaskCategory");
				return updatedTechnicalCardCategory;
			} else {
				log.warn(Messages.ENTITY_IS_NOT_FOUND, "taskCategory");
				return null;
			}
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "updateTaskCategory", e);
			return null;
		}
	}

	// Delete
	public boolean deleteTaskCategory(Long id) {
		log.info(Messages.START_FUNCTION, "deleteTaskCategory");
		try {
			Optional<TechnicalCardCategory> taskCategoryOptional = technicalCardCategoryRepository.findById(id);
			if (taskCategoryOptional.isPresent()) {
				technicalCardCategoryRepository.deleteById(id);
				log.info(Messages.DELETE_SUCCESSFULLY, "taskCategory");
				return true;
			} else {
				log.warn(Messages.ENTITY_IS_NOT_FOUND, "taskCategory");
				return false;
			}
		} catch (Exception e) {
			log.error(Messages.PROCESS_FAILED, "deleteTaskCategory", e);
			return false;
		}
	}
}
