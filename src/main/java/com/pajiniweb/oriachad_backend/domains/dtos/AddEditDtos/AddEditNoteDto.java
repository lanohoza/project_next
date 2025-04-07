package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditNoteDto {
    @NonNull List<AddEditResultDto> results;
    @NotNull AddEditAverageDto average;
    @NonNull Long idStudent;
    @NotNull Long idTrimestre;
}
