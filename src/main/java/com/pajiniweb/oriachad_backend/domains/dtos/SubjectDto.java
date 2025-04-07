package com.pajiniweb.oriachad_backend.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    private Long id;
    private String title;
    private Long idSubjectLevel;
    private Long idSpeciality;
    private Long idLevel;
}
