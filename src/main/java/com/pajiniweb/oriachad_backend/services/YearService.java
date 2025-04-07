package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.YearDto;
import com.pajiniweb.oriachad_backend.domains.entities.Year;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class YearService {

    private static final Logger log = LoggerFactory.getLogger(YearService.class);

    @Autowired
    private YearRepository yearRepository;

    // Create
    public Year createYear(Year year) {
        log.info(Messages.START_FUNCTION, "createYear");
        try {
            return yearRepository.save(year);
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "createYear", e.getMessage());
            return null;
        }
    }

    // Read all
    public List<YearDto> getAllYears() {
        log.info(Messages.START_FUNCTION, "getAllYears");
        try {
            return yearRepository.findAll()
                    .stream().map(year -> YearDto.builder()
                            .title(year.getTitle())
                            .end(year.getEnd())
                            .start(year.getStart())
                            .id(year.getId())
                            .build()).toList();
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getAllYears", e.getMessage());
            return null;
        }
    }

    // Read by ID
    public Optional<Year> getYearById(Long id) {
        log.info(Messages.START_FUNCTION, "getYearById");
        try {
            return yearRepository.findById(id);
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "getYearById", e.getMessage());
            return Optional.empty();
        }
    }

    // Update
    public Year updateYear(Long id, Year yearDetails) {
        log.info(Messages.START_FUNCTION, "updateYear");
        try {
            Optional<Year> optionalYear = yearRepository.findById(id);
            if (optionalYear.isPresent()) {
                Year year = optionalYear.get();
                year.setTitle(yearDetails.getTitle());
                year.setStart(yearDetails.getStart());
                year.setEnd(yearDetails.getEnd());
                return yearRepository.save(year);
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Year", id);
                return null;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "updateYear", e.getMessage());
            return null;
        }
    }

    // Delete
    public boolean deleteYear(Long id) {
        log.info(Messages.START_FUNCTION, "deleteYear");
        try {
            Optional<Year> optionalYear = yearRepository.findById(id);
            if (optionalYear.isPresent()) {
                yearRepository.delete(optionalYear.get());
                return true;
            } else {
                log.warn(Messages.ENTITY_IS_NOT_FOUND, "Year", id);
                return false;
            }
        } catch (Exception e) {
            log.error(Messages.PROCESS_FAILED, "deleteYear", e.getMessage());
            return false;
        }
    }

    public Year getYearByOrder(Integer order) throws Exception {

            return yearRepository.findFirstByOrder(order).orElseThrow(() -> new Exception("لا توجد موسم دراسي سابق لهذا الموسم"));

    }
}
