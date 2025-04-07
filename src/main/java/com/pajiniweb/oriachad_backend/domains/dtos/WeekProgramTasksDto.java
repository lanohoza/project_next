package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.entities.TechnicalCard;
import com.pajiniweb.oriachad_backend.domains.enums.DaysOfWeek;
import com.pajiniweb.oriachad_backend.domains.enums.Period;
import jakarta.annotation.Nullable;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeekProgramTasksDto {
    private Long id;
    private DaysOfWeek days;
    private Period period;
    private String descrption;
    @Nullable
    private Long idWeekProgram;
    private String titleTask;
    private boolean selected;
}
