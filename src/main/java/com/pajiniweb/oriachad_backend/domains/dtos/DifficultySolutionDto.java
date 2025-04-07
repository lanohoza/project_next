package com.pajiniweb.oriachad_backend.domains.dtos;


import com.pajiniweb.oriachad_backend.domains.entities.Difficulty;
import com.pajiniweb.oriachad_backend.domains.entities.Solution;
import jakarta.annotation.Nullable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DifficultySolutionDto {
    private Long id;
    private Difficulty difficultyId;
    private Solution solutionId;
    @Nullable
    private Long createdBy;
}
