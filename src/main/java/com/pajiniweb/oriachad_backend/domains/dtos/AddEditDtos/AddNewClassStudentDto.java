package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;


import com.pajiniweb.oriachad_backend.domains.enums.StudyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddNewClassStudentDto {
    @NotBlank

    private Long id;
    @NotBlank
    private String nbrRakmana;
    @NotNull
    private StudyType schoolingSystem;
    @NotNull
    private Long idClasse;
    @NotNull
    private LocalDate dateStudentInscription;
}
