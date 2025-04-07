package com.pajiniweb.oriachad_backend.domains.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClasseDto {

    private Long id;
    private String levelTitle;
    private String specialityTitle;
    private Long idLevel;

    private Long idSpeciality;

    List<ClasseBreakDto> breaks;
    private String title;
    private Integer number;
    private Long idYear;

    private  Long idProfessor;
}
