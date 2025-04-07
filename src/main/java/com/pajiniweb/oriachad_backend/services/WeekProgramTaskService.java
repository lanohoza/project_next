package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.administration.repositories.AdminTechnicalCardRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditTasksWeekProgramDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditWeekProgramDto;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditWeekProgramTaskDto;
import com.pajiniweb.oriachad_backend.domains.dtos.TechnicalCardYearDto;
import com.pajiniweb.oriachad_backend.domains.dtos.WeekProgramTasksDto;
import com.pajiniweb.oriachad_backend.domains.dtos.WeekProgramTasksUserDto;
import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.entities.WeekProgram;
import com.pajiniweb.oriachad_backend.domains.entities.WeekProgramTask;
import com.pajiniweb.oriachad_backend.exceptions.CustomException;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.TaskRepository;
import com.pajiniweb.oriachad_backend.repositories.TechnicalCardRepository;
import com.pajiniweb.oriachad_backend.repositories.WeekProgramRepository;
import com.pajiniweb.oriachad_backend.repositories.WeekProgramTaskRepository;
import com.pajiniweb.oriachad_backend.security.domain.entities.CustomUserDetails;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeekProgramTaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeekProgramTaskService.class);

    @Autowired
    private WeekProgramTaskRepository weekProgramTaskRepository;

    @Autowired
    private WeekProgramService weekProgramService;

    @Autowired
    private TechnicalCardRepository technicalCardRepository;

    @Autowired
    private TaskRepository taskRepository;


    public List<AddEditWeekProgramTaskDto> findAll() {
        LOGGER.info(Messages.START_FUNCTION, "findAll WeekProgramTask");
        try {
            return weekProgramTaskRepository.findAll().stream()
                    .map(task -> AddEditWeekProgramTaskDto.builder()
                            .id(task.getId())
                            .days(task.getDays())
                            .period(task.getPeriod())
                            .descrption(task.getDescrption())
                            .idWeekProgram(task.getIdWeekProgram())
                            .idTask(task.getTask().getId())
                            .build())
                    .toList();
        } catch (Exception e) {
            LOGGER.error("Error fetching all WeekProgramTasks", e);
            throw e; // Or handle accordingly
        }
    }

    public Optional<WeekProgramTask> findById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "findById WeekProgramTask");
        try {
            return weekProgramTaskRepository.findById(id);
        } catch (Exception e) {
            LOGGER.error("Error fetching WeekProgramTask by id", e);
            throw e; // Or handle accordingly
        }
    }

    @Transactional
    public WeekProgramTask save(AddEditWeekProgramTaskDto weekProgramTaskDto) {
        LOGGER.info(Messages.START_FUNCTION, "save WeekProgramTask");
        try {
            WeekProgramTask weekProgramTask = WeekProgramTask.builder()
                    .days(weekProgramTaskDto.getDays())
                    .period(weekProgramTaskDto.getPeriod())
                    .descrption(weekProgramTaskDto.getDescrption())
                    .idWeekProgram(weekProgramTaskDto.getIdWeekProgram())
                    .idTask(weekProgramTaskDto.getIdTask())
                    .build();
            return weekProgramTaskRepository.save(weekProgramTask);
        } catch (Exception e) {
            LOGGER.error("Error saving WeekProgramTask", e);
            throw e; // Or handle accordingly
        }
    }

    @Transactional
    public List<WeekProgramTask> saveOrUpdateAll(AddEditTasksWeekProgramDto tasksWeekProgramDto) throws Exception {
        LOGGER.info(Messages.START_FUNCTION, "saveOrUpdateAll WeekProgramTasks");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = ((CustomUserDetails) auth.getPrincipal());

        Calendar calendar = Calendar.getInstance();
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        LOGGER.info("Current week of the month: {}", weekOfMonth);
        WeekProgram weekProgram = new WeekProgram();

        for (AddEditWeekProgramTaskDto dto : tasksWeekProgramDto.getWeekProgramTaskDtos()) {
            String validationMessage = validateDto(dto);
            if (validationMessage != null) {
                throw new CustomException(validationMessage);
            }
        }

        if (tasksWeekProgramDto.getWeekProgramTaskDtos() != null && !tasksWeekProgramDto.getWeekProgramTaskDtos().isEmpty() && tasksWeekProgramDto.getAddEditWeekProgramDto() != null) {

            if (weekProgramService.findById(tasksWeekProgramDto.getAddEditWeekProgramDto().getId()).isEmpty()) {
                // Check if a program for the same month and week already exists
                if (weekProgramService.findByMonthAndWeekNumberAndUser(tasksWeekProgramDto.getAddEditWeekProgramDto().getIdMonth(), tasksWeekProgramDto.getAddEditWeekProgramDto().getWeekNumber(), userDetails.getIdUser()) != null) {
                    throw new Exception("تم إعداد برنامج أسبوعي لهذا الشهر والأسبوع مسبقا");
                }

                weekProgram = weekProgramService.save(tasksWeekProgramDto.getAddEditWeekProgramDto());
            } else {
                weekProgram = weekProgramService.findById(tasksWeekProgramDto.getAddEditWeekProgramDto().getId()).get();
            }

            WeekProgram finalWeekProgram = weekProgram;
            List<WeekProgramTask> weekProgramTasks = tasksWeekProgramDto.getWeekProgramTaskDtos().stream()
                    .map(dto -> {

                        WeekProgramTask weekProgramTask = new WeekProgramTask();

                        weekProgramTask.setDays(dto.getDays());
                        weekProgramTask.setPeriod(dto.getPeriod());
                        weekProgramTask.setDescrption(dto.getDescrption());
                        weekProgramTask.setWeekProgram(finalWeekProgram);
                        weekProgramTask.setTitleTask(dto.getTitleTask());

                        if (dto.getIdTask() != null) {
                            technicalCardRepository.findById(dto.getIdTask()).ifPresent(weekProgramTask::setTask);
                        } else {
                            weekProgramTask.setTask(null);
                        }

                        if (dto.getId() != null) {
                            Optional<WeekProgramTask> existingTask = weekProgramTaskRepository.findById(dto.getId());
                            if (existingTask.isPresent()) {
                                weekProgramTask = existingTask.get();
                                if (dto.getIdTask() != null) {
                                    technicalCardRepository.findById(dto.getIdTask()).ifPresent(weekProgramTask::setTask);
                                } else {
                                    weekProgramTask.setTask(null);
                                }
                                weekProgramTask.setDays(dto.getDays());
                                weekProgramTask.setPeriod(dto.getPeriod());
                                weekProgramTask.setDescrption(dto.getDescrption());
                                weekProgramTask.setTitleTask(dto.getTitleTask());
                                weekProgramTask.setWeekProgram(finalWeekProgram);
                            }
                        }
                        return weekProgramTask;
                    })
                    .collect(Collectors.toList());

            return weekProgramTaskRepository.saveAll(weekProgramTasks);

        } else {
            LOGGER.warn(Messages.ENTITY_IS_NOT_FOUND, "saveOrUpdateAll weekProgramTaskDtos list or addEditWeekProgramDto is empty");
            return Collections.emptyList();
        }
    }


    @Transactional
    public List<WeekProgramTask> update(Long idWeekProgram, AddEditWeekProgramTaskDto weekProgramTaskDto) {
        LOGGER.info(Messages.START_FUNCTION, "update WeekProgramTask");
        try {
            List<WeekProgramTask> tasks = weekProgramTaskRepository.findByIdWeekProgram(idWeekProgram);

            if (tasks != null && !tasks.isEmpty()) {
                tasks.forEach(dto -> {
                    if (weekProgramTaskDto.getIdTask() != null) {
                        Optional<TechnicalCard> technicalCard = technicalCardRepository.findById(weekProgramTaskDto.getIdTask());
                        dto.setTask(technicalCard.orElse(null));
                    }
                    dto.setTitleTask(weekProgramTaskDto.getTitleTask());
                    dto.setDays(weekProgramTaskDto.getDays());
                    dto.setPeriod(weekProgramTaskDto.getPeriod());
                    dto.setDescrption(weekProgramTaskDto.getDescrption());
                });

                return weekProgramTaskRepository.saveAll(tasks);
            } else {
                LOGGER.warn("No tasks found for the given week program ID: {}", idWeekProgram);
                return Collections.emptyList();
            }

        } catch (Exception e) {
            LOGGER.error("Error updating WeekProgramTask for WeekProgram ID: {}", idWeekProgram, e);
            throw e;
        }
    }


    public boolean deleteById(Long id) {
        LOGGER.info(Messages.START_FUNCTION, "deleteById WeekProgramTask");
        try {
            if (weekProgramTaskRepository.existsById(id)) {
                weekProgramTaskRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            LOGGER.error("Error deleting WeekProgramTask", e);
            throw e; // Or handle accordingly
        }
    }

    public AddEditTasksWeekProgramDto findByIdWeekProgram(Long idWeekProgram) {
        AddEditTasksWeekProgramDto addEditTasksWeekProgramDto = new AddEditTasksWeekProgramDto();
        AddEditWeekProgramDto addEditWeekProgramDto = new AddEditWeekProgramDto();
        LOGGER.info("Starting findByIdWeekProgram with idWeekProgram: {}", idWeekProgram);
        try {
            WeekProgram weekProgram = weekProgramService.findById(idWeekProgram).get();
            addEditWeekProgramDto.setStartWeek(weekProgram.getStartWeek());
            addEditWeekProgramDto.setEndWeek(weekProgram.getEndWeek());
            addEditWeekProgramDto.setId(weekProgram.getId());
            addEditWeekProgramDto.setUrlDoc(weekProgram.getUrlDoc());
            addEditWeekProgramDto.setIdMonth(weekProgram.getIdMonth());
            addEditWeekProgramDto.setType(weekProgram.getType());
            addEditWeekProgramDto.setWeekNumber(weekProgram.getWeekNumber());
            List<WeekProgramTask> tasks = weekProgramTaskRepository.findByIdWeekProgram(idWeekProgram);
            List<AddEditWeekProgramTaskDto> dtos = tasks.stream()
                    .map(task -> AddEditWeekProgramTaskDto.builder()
                            .id(task.getId())
                            .days(task.getDays())
                            .period(task.getPeriod())
                            .descrption(task.getDescrption())
                            .idWeekProgram(task.getIdWeekProgram())
                            .idTask(task.getTask() != null ? task.getTask().getId() : null)
                            .titleTask(task.getTitleTask() != null ? task.getTitleTask() : task.getTask().getTitle())
                            .selected(true)
                            .build())
                    .collect(Collectors.toList());
            addEditTasksWeekProgramDto.setAddEditWeekProgramDto(addEditWeekProgramDto);
            addEditTasksWeekProgramDto.setWeekProgramTaskDtos(dtos);
            LOGGER.info("Successfully retrieved {} tasks for idWeekProgram: {}", dtos.size(), idWeekProgram);
            return addEditTasksWeekProgramDto;
        } catch (Exception e) {
            LOGGER.error("Error retrieving WeekProgramTasks for idWeekProgram: {}", idWeekProgram, e);
            return null;
        }
    }

    public WeekProgramTasksUserDto findByIdWeekProgramForDocument(Long idWeekProgram) {
        WeekProgramTasksUserDto weekProgramTasksUserDto = new WeekProgramTasksUserDto();
        LOGGER.info("Starting findByIdWeekProgram with idWeekProgram: {}", idWeekProgram);
        try {
            List<WeekProgramTask> tasks = weekProgramTaskRepository.findByIdWeekProgram(idWeekProgram);
            WeekProgram weekProgram = weekProgramService.findById(idWeekProgram).get();
            List<WeekProgramTasksDto> dtos = tasks.stream()
                    .map(task -> WeekProgramTasksDto.builder()
                            .id(task.getId())
                            .days(task.getDays())
                            .period(task.getPeriod())
                            .descrption(task.getDescrption())
                            .idWeekProgram(task.getIdWeekProgram())
                            .titleTask(task.getTask() != null ? task.getTask().getTitle() : task.getTitleTask())
                            .selected(true)
                            .build())
                    .collect(Collectors.toList());
            weekProgramTasksUserDto.setStartWeek(weekProgram.getStartWeek());
            weekProgramTasksUserDto.setEndWeek(weekProgram.getEndWeek());
            weekProgramTasksUserDto.setUsername(weekProgram.getCreatedBy().getUsername());
            weekProgramTasksUserDto.setEstablishmentName(weekProgram.getCreatedBy().getEstablishment().getName());
            weekProgramTasksUserDto.setWilayaName(weekProgram.getCreatedBy().getCommune().getWilaya().getName());
            weekProgramTasksUserDto.setWeekProgramTaskDtos(dtos);
            LOGGER.info("Successfully retrieved {} tasks for idWeekProgram: {}", dtos.size(), idWeekProgram);
            return weekProgramTasksUserDto;
        } catch (Exception e) {
            LOGGER.error("Error retrieving WeekProgramTasks for idWeekProgram: {}", idWeekProgram, e);
            return null;
        }
    }

    private String validateDto(AddEditWeekProgramTaskDto dto) {
        if (dto.getIdTask() == null && (dto.getTitleTask() == null || dto.getTitleTask().isEmpty())) {
            return "ألرجاء إدخال النشاط";
        }

        if (dto.getDays() == null || dto.getPeriod() == null) {
            return "لا يمكن أن تكون الأيام أو الفترة فارغة";
        }

        return null;
    }

}
