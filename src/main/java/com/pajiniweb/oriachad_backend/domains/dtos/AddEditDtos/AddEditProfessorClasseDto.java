package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditProfessorClasseDto {
    private Long id;
    private Long idClasse;
    private Long idProfessor;
    private Long idYear;
    private Long idCreatedBy;
}
