package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEditTasksWeekProgramDto {
    private AddEditWeekProgramDto addEditWeekProgramDto;
    private List<AddEditWeekProgramTaskDto> weekProgramTaskDtos;
}
