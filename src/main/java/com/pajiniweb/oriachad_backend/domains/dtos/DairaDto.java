package com.pajiniweb.oriachad_backend.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DairaDto {
    private Long id;
    private String name;
    private Long idWilaya;
}
