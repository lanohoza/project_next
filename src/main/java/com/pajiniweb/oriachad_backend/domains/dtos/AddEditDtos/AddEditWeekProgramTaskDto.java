package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.DaysOfWeek;
import com.pajiniweb.oriachad_backend.domains.enums.Period;
import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEditWeekProgramTaskDto {
    private Long id;
    private DaysOfWeek days;
    private Period period;
    private String descrption;
    @Nullable
    private Long idWeekProgram;
    @Nullable
    private Long idTask;
    @Nullable
    private String titleTask;
    private boolean selected;
}
