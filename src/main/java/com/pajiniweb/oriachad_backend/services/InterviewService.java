package com.pajiniweb.oriachad_backend.services;

import com.pajiniweb.oriachad_backend.actions.domains.entities.Activity;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedCategory;
import com.pajiniweb.oriachad_backend.actions.domains.entities.ShedSetting;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActivityType;
import com.pajiniweb.oriachad_backend.actions.repositories.ActivityRepository;
import com.pajiniweb.oriachad_backend.domains.dtos.*;
import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditInterviewDto;
import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.FollowupStatus;
import com.pajiniweb.oriachad_backend.domains.enums.InterViewCreateType;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewStatus;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewType;
import com.pajiniweb.oriachad_backend.helps.Messages;
import com.pajiniweb.oriachad_backend.repositories.FollowUpRepository;
import com.pajiniweb.oriachad_backend.repositories.GuidanceGroupRepository;
import com.pajiniweb.oriachad_backend.repositories.InterviewRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InterviewService {

    private static final Logger log = LoggerFactory.getLogger(InterviewService.class);

    final HelperService helperService;
    final InterviewRepository interviewRepository;
    final DateTimeFormatter dateTimeFormatter;
    private final FollowUpRepository followUpRepository;
    private final ActivityRepository activityRepository;
    private final GuidanceGroupRepository guidanceGroupRepository;


    // Update
    public boolean update(AddEditInterviewDto addEditInterviewDto) throws Exception {
        log.info(Messages.START_FUNCTION, "updateTechnicalCard");
        Optional<Interview> interviewOptional = interviewRepository.findById(addEditInterviewDto.getId());
        if (!interviewOptional.isPresent()) {
            throw new Exception("هاته البطاقة التقنية غير موجودة");
        }

        Interview interview = interviewOptional.get();
        interview.setFollowUp(FollowUp.builder()
                                      .id(addEditInterviewDto.getIdFollowUp())
                                      .build());
        interview.setDescription(addEditInterviewDto.getDescription());
        interview.setStatus(InterviewStatus.todo);
        interview.setType(addEditInterviewDto.getType());
        interview.setShedCategory(ShedCategory.builder()
                                              .id(addEditInterviewDto.getIdShedCategory())
                                              .build());
        if (addEditInterviewDto.getType() == InterviewType.single) {
            interview.setStudent(Student.builder()
                                        .id(addEditInterviewDto.getIdStudent())
                                        .build());
        } else {
            interview.setGuidanceGroup(GuidanceGroup.builder()
                                                    .id(addEditInterviewDto.getIdGuidanceGroup())
                                                    .build());
        }
        if (addEditInterviewDto.getIdShedSettings() != null)
            interview.setShedSettings(addEditInterviewDto.getIdShedSettings()
                                                         .stream()
                                                         .map((idSolution) -> ShedSetting.builder()
                                                                                         .id(idSolution)
                                                                                         .build())
                                                         .collect(Collectors.toSet()));
        interviewRepository.save(interview);
        log.info(Messages.PROCESS_SUCCESSFULLY, "updateTechnicalCard");
        return true;
    }

    @Transactional
    public Long createInterview(AddEditInterviewDto addEditInterviewDto) throws Exception {
        log.info("Start creating task with details: {}", addEditInterviewDto);
        OriachadUser oriachadUser = helperService.getCurrentUser()
                                                 .getOriachadUser();
        Year year = helperService.getCurrentYear();

        Interview interview = new Interview();
        interview.setCreatedBy(oriachadUser);
        /**
         *  check if it has followUp , if it has change its status.
         */
        if (addEditInterviewDto.getIdFollowUp() != null) {
            FollowUp followUp = followUpRepository.findById(addEditInterviewDto.getIdFollowUp())
                                                  .orElseThrow();
            interview.setFollowUp(followUp);
            followUp.setStatus(FollowupStatus.in_progress);
            followUpRepository.save(followUp);
        }
        interview.setYear(year);
        if (addEditInterviewDto.getAccessRapid()) {
            interview.setStatus(InterviewStatus.in_progress);
            interview.setInterviewDate(LocalDate.now());
        }else{
            interview.setStatus(InterviewStatus.todo);
        }
        interview.setCreateType(InterViewCreateType.manual);
        interview.setType(addEditInterviewDto.getType());
        interview.setDescription(addEditInterviewDto.getDescription());
        interview.setNumber(interviewRepository.lastNumberOfInterview(oriachadUser.getId())
                                               .orElse(0L) + 1);
        interview.setShedCategory(ShedCategory.builder()
                                              .id(addEditInterviewDto.getIdShedCategory())
                                              .build());


        if (addEditInterviewDto.getType() == InterviewType.single) {
            interview.setStudent(Student.builder()
                                        .id(addEditInterviewDto.getIdStudent())
                                        .build());
        } else {
            interview.setGuidanceGroup(guidanceGroupRepository.findById(addEditInterviewDto.getIdGuidanceGroup())
                                                              .orElseThrow());
        }
        if (addEditInterviewDto.getIdShedSettings() != null)
            interview.setShedSettings(addEditInterviewDto.getIdShedSettings()
                                                         .stream()
                                                         .map((idSolution) -> ShedSetting.builder()
                                                                                         .id(idSolution)
                                                                                         .build())
                                                         .collect(Collectors.toSet()));
        interviewRepository.save(interview);
        if (interview.getType() == InterviewType.single) {
            if (!activityRepository.hasTypeToday(oriachadUser.getId(), ActivityType.interviewSingle, LocalDate.now())) {
                activityRepository.save(Activity.builder()
                                                .content("إجراء مقابلات إرشادية")
                                                .type(ActivityType.interviewSingle)
                                                .user(oriachadUser)
                                                .build());
            }
        }
        if (interview.getType() == InterviewType.group) {
            activityRepository.save(Activity.builder()
                                            .content(String.format("%s :  %s", "إجراء مقابلة إرشادية جماعية ", interview.getGuidanceGroup()
                                                                                                                        .getTitle()))
                                            .type(ActivityType.interviewGroup)
                                            .user(oriachadUser)
                                            .build());
        }

        return interview.getId();
    }


    public Page<InterviewDto> searchInterviews(String search, InterviewType type, InterviewStatus status, Long idYear, Pageable pageable) {
        OriachadUser oriachadUser = helperService.getCurrentUser()
                                                 .getOriachadUser();
        return interviewRepository.search(search, idYear, type, status, oriachadUser.getId(), pageable)
                                  .map(interview -> InterviewDto.builder()
                                                                .id(interview.getId())
                                                                .number(interview.getNumber())
                                                                .createdDate(interview.getCreatedDate())
                                                                .status(interview.getStatus())
                                                                .type(interview.getType())
                                                                .target(interview.getType() == InterviewType.group ? interview.getGuidanceGroup()
                                                                                                                              .getTitle() : String.format("%s %s", interview.getStudent()
                                                                                                                                                                            .getFirstName(), interview.getStudent()
                                                                                                                                                                                                      .getLastName()))
                                                                .interviewDate(interview.getInterviewDate())
                                                                .description(interview.getDescription())
                                                                .createType(interview.getCreateType())
                                                                .build());
    }

    public boolean doInterview(DoInterviewDto doInterviewDto) {
        Interview interview = interviewRepository.findById(doInterviewDto.getIdInterview())
                                                 .orElseThrow();
        interview.setInterviewDate(LocalDate.parse(doInterviewDto.getInterviewDate(), dateTimeFormatter));
        interview.setStatus(InterviewStatus.in_progress);
        interviewRepository.save(interview);
        return true;
    }

    public InterviewDto findById(Long id) {

        return interviewRepository.findById(id)
                                  .map(interview -> InterviewDto.builder()
                                                                .id(interview.getId())
                                                                .createdDate(interview.getCreatedDate())
                                                                .number(interview.getNumber())
                                                                .interviewDate(interview.getInterviewDate())
                                                                .shedCategory(interview.getShedCategory()
                                                                                       .getName())
                                                                .shedSettings(interview.getShedSettings()
                                                                                       .stream()
                                                                                       .map(ShedSetting::getSyndromeDiagnostic)
                                                                                       .toList())
                                                                .taskTitle(interview.getTask() != null ? interview.getTask()
                                                                                                                  .getTitle() : null)
                                                                .status(interview.getStatus())
                                                                .type(interview.getType())
                                                                .idShedCategory(interview.getIdShedCategory())
                                                                .idShedSettings(interview.getShedSettings()
                                                                                         .stream()
                                                                                         .map(ShedSetting::getId)
                                                                                         .toList())
                                                                .description(interview.getDescription())
                                                                .target(interview.getType() == InterviewType.group ? interview.getGuidanceGroup()
                                                                                                                              .getTitle() : String.format("%s %s", interview.getStudent()
                                                                                                                                                                            .getFirstName(), interview.getStudent()
                                                                                                                                                                                                      .getLastName()))
                                                                .idStudent(interview.getIdStudent())
                                                                .idGuidanceGroup(interview.getIdGuidanceGroup())
                                                                .idFollowUp(interview.getIdFollowUp())
                                                                .followupNumber(interview.getFollowUp() != null ? interview.getFollowUp()
                                                                                                                           .getNumber() : null)
                                                                .createType(interview.getCreateType())
                                                                .build())
                                  .orElseThrow();
    }

    @Transactional
    public boolean endInterview(EndInterviewDto endInterviewDto) throws CloneNotSupportedException {
        Interview interview = interviewRepository.findById(endInterviewDto.getIdInterview())
                                                 .orElseThrow();
        interview.setStatus(InterviewStatus.done);
        interviewRepository.save(interview);
        if (endInterviewDto.getOpenNewInterview()) {
            OriachadUser oriachadUser = helperService.getCurrentUser()
                                                     .getOriachadUser();
            Interview newInterview = new Interview();
            newInterview.setDescription(interview.getDescription());
            newInterview.setNumber(interviewRepository.lastNumberOfInterview(oriachadUser.getId())
                                                      .orElse(0L) + 1);
            newInterview.setStatus(InterviewStatus.todo);
            newInterview.setCreateType(interview.getCreateType());
            newInterview.setFollowUp(interview.getFollowUp());
            newInterview.setGuidanceGroup(interview.getGuidanceGroup());
            newInterview.setStudent(interview.getStudent());
            newInterview.setTask(interview.getTask());
            newInterview.setParent(interview);
            newInterview.setYear(interview.getYear());
            newInterview.setCreatedBy(oriachadUser);
            newInterview.setType(interview.getType());
            newInterview.setShedCategory(interview.getShedCategory());
            newInterview.getShedSettings()
                        .addAll(interview.getShedSettings());
            /*newInterview.setStatus(InterviewStatus.todo);
            newInterview.setId(null);
            newInterview.setCreateDate(null);
            newInterview.setInterviewDate(null);*/
            interviewRepository.save(newInterview);
        }
        return true;

    }

    public boolean deleteById(Long id) {
        interviewRepository.deleteById(id);
        return false;
    }


    public InterviewDetailDto getDetailsById(Long id) {
        return interviewRepository.findById(id)
                                  .map(interview -> InterviewDetailDto.builder()
                                                                      .yearTitle(interview.getYear()
                                                                                          .getTitle())
                                                                      .id(interview.getId())
                                                                      .createdDate(interview.getCreatedDate())
                                                                      .number(interview.getNumber())
                                                                      .interviewDate(interview.getInterviewDate())
                                                                      .shedCategory(interview.getShedCategory()
                                                                                             .getName())
                                                                      .shedSettings(interview.getShedSettings()
                                                                                             .stream()
                                                                                             .map(ShedSetting::getSyndromeDiagnostic)
                                                                                             .toList())
                                                                      .taskTitle(interview.getTask() != null ? interview.getTask()
                                                                                                                        .getTitle() : null)
                                                                      .status(interview.getStatus())
                                                                      .type(interview.getType())
                                                                      .description(interview.getDescription())
                                                                      .target(interview.getType() == InterviewType.group ? interview.getGuidanceGroup()
                                                                                                                                    .getTitle() : String.format("%s %s", interview.getStudent()
                                                                                                                                                                                  .getFirstName(), interview.getStudent()
                                                                                                                                                                                                            .getLastName()))
                                                                      .idStudent(interview.getIdStudent())
                                                                      .idGuidanceGroup(interview.getIdGuidanceGroup())
                                                                      .idFollowUp(interview.getIdFollowUp())
                                                                      .studentDtos(interview.getType() == InterviewType.group ? interview.getGuidanceGroup()
                                                                                                                                         .getStudents()
                                                                                                                                         .stream()
                                                                                                                                         .map(student -> StudentDto.builder()
                                                                                                                                                                   .nbrRakmana(student.getNbrRakmana())
                                                                                                                                                                   .firstName(student.getFirstName())
                                                                                                                                                                   .lastName(student.getLastName())
                                                                                                                                                                   .birthDate(student.getBirthDate())
                                                                                                                                                                   .classeTitle(student.getCurrentClass()
                                                                                                                                                                                       .getTitle())
                                                                                                                                                                   .build())
                                                                                                                                         .toList() : List.of(StudentDto.builder()
                                                                                                                                                                       .nbrRakmana(interview.getStudent()
                                                                                                                                                                                            .getNbrRakmana())
                                                                                                                                                                       .firstName(interview.getStudent()
                                                                                                                                                                                           .getFirstName())
                                                                                                                                                                       .lastName(interview.getStudent()
                                                                                                                                                                                          .getLastName())
                                                                                                                                                                       .birthDate(interview.getStudent()
                                                                                                                                                                                           .getBirthDate())
                                                                                                                                                                       .classeTitle(interview.getStudent()
                                                                                                                                                                                             .getCurrentClass()
                                                                                                                                                                                             .getTitle())
                                                                                                                                                                       .build()))
                                                                      .followupNumber(interview.getFollowUp() != null ? interview.getFollowUp()
                                                                                                                                 .getNumber() : null)
                                                                      .createType(interview.getCreateType())
                                                                      .build())
                                  .orElseThrow();
    }
}
