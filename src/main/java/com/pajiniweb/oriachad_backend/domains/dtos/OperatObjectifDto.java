package com.pajiniweb.oriachad_backend.domains.dtos;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatObjectifDto {
    private Long id;
    private String content;
    @Nullable
    private Long createdById;
}
