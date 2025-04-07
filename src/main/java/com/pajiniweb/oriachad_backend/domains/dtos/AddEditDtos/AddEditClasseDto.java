package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;


import com.pajiniweb.oriachad_backend.domains.dtos.ClasseBreakDto;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditClasseDto {

    private Long id;
    @NonNull
    private Long idLevel;

    private Long idSpeciality;

    private String title;
    @NonNull
    @Min(0)
    private Integer number;
    @NonNull
    private Long idYear;
    private Long idProfessor;
    List<ClasseBreakDto> breaks;

}
