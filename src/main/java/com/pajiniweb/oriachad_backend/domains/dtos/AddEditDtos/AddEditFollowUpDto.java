package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;


import com.pajiniweb.oriachad_backend.domains.entities.*;
import com.pajiniweb.oriachad_backend.domains.enums.FollowUpType;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditFollowUpDto {

    private Long id;

    private FollowUpType type;


    private Long idStudent;
    private Long idGuidanceGroup;

    private Task task;
    private Long idTask;

    private Long idShedCategory;
    List<Long> idShedSettings;
    String resourceUrl;

    private String description;

}
