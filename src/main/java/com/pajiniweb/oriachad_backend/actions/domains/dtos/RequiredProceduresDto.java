package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequiredProceduresDto {
    private Long id;
    private String name;
}
