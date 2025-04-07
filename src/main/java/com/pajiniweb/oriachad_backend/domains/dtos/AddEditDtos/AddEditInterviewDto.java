package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;


import com.pajiniweb.oriachad_backend.domains.entities.Task;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditInterviewDto {

    private Long id;

    private InterviewType type;

    private Long idFollowUp;
    private Long idStudent;
    private Long idGuidanceGroup;

    private Long idShedCategory;
    List<Long> idShedSettings;

    private Task task;
    private Long idTask;

    private String description;
    private Boolean accessRapid;
}
