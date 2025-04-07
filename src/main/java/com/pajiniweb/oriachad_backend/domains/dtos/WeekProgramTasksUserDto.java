package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos.AddEditWeekProgramTaskDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeekProgramTasksUserDto {

    private String username;
    private String establishmentName;
    private String wilayaName;
    private LocalDate startWeek;
    private LocalDate endWeek;
    private List<WeekProgramTasksDto> weekProgramTaskDtos;
}
