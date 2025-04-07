package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditWeekProgramDto;
import com.pajiniweb.oriachad_backend.domains.dtos.CurrentMonthAndWeekDto;
import com.pajiniweb.oriachad_backend.domains.entities.Months;
import com.pajiniweb.oriachad_backend.domains.entities.WeekProgram;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.MonthsRepository;
import com.pajiniweb.oriachad_backend.repositories.WeekProgramRepository;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class WeekProgramService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeekProgramService.class);

    @Autowired
    private WeekProgramRepository weekProgramRepository;

    @Autowired
    private MonthsRepository monthsRepository;

    public List<AddEditWeekProgramDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll WeekProgram");
        return weekProgramRepository.findAll().stream()
                .map(weekProgram -> AddEditWeekProgramDto.builder()
                        .id(weekProgram.getId())
                        .startWeek(weekProgram.getStartWeek())
                        .endWeek(weekProgram.getEndWeek())
                        //.urlDoc(weekProgram.getUrlDoc())
                        .type(weekProgram.getType())
                        .idMonth(weekProgram.getIdMonth())
                        .build())
                .toList();
    }

    public Optional<WeekProgram> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById WeekProgram");
        try {
            if (id != null) {
                return weekProgramRepository.findById(id);
            }
            return Optional.empty();
        } catch (Exception e) {
            LOGGER.error("Error fetching WeekProgram by id", e);
            throw e;
        }
    }

    @Transactional
    public WeekProgram save(AddEditWeekProgramDto weekProgramDto) {
        LOGGER.info(Messages.START_FUNCTION, "save WeekProgram");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
            CurrentMonthAndWeekDto currentMonthAndWeekDto = getBeginAndEndWeek(weekProgramDto.getWeekNumber(), weekProgramDto.getIdMonth());
            if (weekProgramDto != null) {
                Optional<Months> months = monthsRepository.findById(Long.valueOf(weekProgramDto.getIdMonth()));
                WeekProgram weekProgram = new WeekProgram();
                weekProgram.setStartWeek(currentMonthAndWeekDto.getStartWeek());
                weekProgram.setEndWeek(currentMonthAndWeekDto.getEndWeek());
                //weekProgram.urlDoc(weekProgramDto.getUrlDoc())
                weekProgram.setCreatedBy(userDetails.getOriachadUser());
                weekProgram.setType(weekProgramDto.getType());
                weekProgram.setMonth(months.get());
                weekProgram.setWeekNumber(weekProgramDto.getWeekNumber());

                return weekProgramRepository.save(weekProgram);
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_FOUND, "weekProgramDto");
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error saving WeekProgram", e);
            throw e;
        }
    }

    public WeekProgram update(Long id, AddEditWeekProgramDto weekProgramDto) {
        LOGGER.info(Messages.START_FUNCTION, "update WeekProgram");
        try {
            if (!weekProgramRepository.existsById(id)) {
                LOGGER.error(Messages.ENTITY_IS_NOT_EXIST, "WeekProgram");
                throw new IllegalArgumentException(Messages.ENTITY_IS_NOT_EXIST.replace("{}", "WeekProgram"));
            }

            WeekProgram weekProgram = WeekProgram.builder()
                    .id(id)
                    .startWeek(weekProgramDto.getStartWeek())
                    .endWeek(weekProgramDto.getEndWeek())
                    //.urlDoc(weekProgramDto.getUrlDoc())
                    .type(weekProgramDto.getType())
                    .idMonth(weekProgramDto.getIdMonth())
                    .build();

            return weekProgramRepository.save(weekProgram);
        } catch (Exception e) {
            LOGGER.error("Error updating WeekProgram", e);
            throw e;
        }
    }

    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById WeekProgram");
        try {
            if (weekProgramRepository.existsById(id)) {
                weekProgramRepository.deleteById(id);
                LOGGER.info(Messages.DELETE_SUCCESSFULLY, "WeekProgram");
                return true;
            } else {
                LOGGER.warn(Messages.ENTITY_IS_NOT_EXIST, "WeekProgram");
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting WeekProgram", e);
            throw e;
        }
    }

    public Page<AddEditWeekProgramDto> findByIdUser(int page, int size) {
        LOGGER.info(Messages.START_FUNCTION, "findByIdUser WeekProgram");
        try {
            Pageable pageable = PageRequest.of(page < 0 ? 0 : page, size);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());
            Page<WeekProgram> weekPrograms = weekProgramRepository.findByCreatedById(userDetails.getIdUser(), pageable);
            return weekPrograms
                    .map(weekProgram -> AddEditWeekProgramDto.builder()
                            .id(weekProgram.getId())
                            .startWeek(weekProgram.getStartWeek())
                            .endWeek(weekProgram.getEndWeek())
                            .urlDoc(weekProgram.getUrlDoc())
                            .type(weekProgram.getType())
                            .idMonth(weekProgram.getIdMonth())
                            .build());
        } catch (Exception e) {
            LOGGER.error("Error fetching WeekPrograms by user id", e);
            throw e;
        }
    }

    public CurrentMonthAndWeekDto getBeginAndEndWeek(int weekNumber, int month) {
        LOGGER.info("START_FUNCTION: getBeginAndEndWeek");
        LOGGER.info("weekNumber: {}", weekNumber);
        LOGGER.info("month: {}", month);

        int year = LocalDate.now().getYear();
        LOGGER.info("year (auto-detected): {}", year);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        LocalDate startWeek = firstDayOfMonth.with(WeekFields.of(Locale.getDefault()).weekOfMonth(), weekNumber)
                .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);

        if (startWeek.getMonthValue() != month) {
            startWeek = firstDayOfMonth.with(WeekFields.of(Locale.getDefault()).weekOfMonth(), weekNumber)
                    .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1);
        }

        // (Thursday)
        LocalDate endWeek = startWeek.plusDays(4);

        CurrentMonthAndWeekDto result = new CurrentMonthAndWeekDto();
        result.setIdMonth(month);
        result.setStartWeek(startWeek);
        result.setEndWeek(endWeek);

        LOGGER.info("Start of week (Sunday): {}", startWeek);
        LOGGER.info("End of week (Thursday): {}", endWeek);

        return result;
    }

    public AddEditWeekProgramDto findByMonthAndWeekNumberAndUser(int month, int weekNumber, Long idUser) {
        try {
            LOGGER.info(Messages.START_FUNCTION, "findByMonthAndWeekNumber");
            WeekProgram weekProgram = weekProgramRepository.findByMonthAndWeekNumberAndUser(idUser, month, weekNumber);

            return AddEditWeekProgramDto.builder()
                    .id(weekProgram.getId())
                    .startWeek(weekProgram.getStartWeek())
                    .endWeek(weekProgram.getEndWeek())
                    .urlDoc(weekProgram.getUrlDoc())
                    .type(weekProgram.getType())
                    .idMonth(weekProgram.getIdMonth())
                    .build();
        } catch (Exception e) {
            LOGGER.error(Messages.PROCESS_FAILED, "findByMonthAndWeekNumber because :" + e.getMessage());
        }
        return null;
    }
}