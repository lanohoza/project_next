package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HumanToolDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String adresse;
    @Nullable
    private Long createdBy;
    @Nullable
    private Long idAdmin;
    private SourceTechnicalCard source;
}
