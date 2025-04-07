package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionResultType;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionStatus;
import com.pajiniweb.oriachad_backend.actions.domains.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActionTaskDto {


    private Long id;
    private Long idAction;

    private String title;
    private ActionType type;


    private ActionResultType resultType;

    private String resultValue;
    ActionStatus status;
    String description;

}
