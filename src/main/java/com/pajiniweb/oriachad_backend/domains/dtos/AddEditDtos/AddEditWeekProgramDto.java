package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import com.pajiniweb.oriachad_backend.domains.enums.TypeWeekProgram;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEditWeekProgramDto {
    private Long id;
    private LocalDate startWeek;
    private LocalDate endWeek;
    private String urlDoc;
    private TypeWeekProgram type;
    private Integer idMonth;
    private Integer weekNumber;
}
