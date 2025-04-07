package com.pajiniweb.oriachad_backend.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearDto {

    private Long id;

    private String title;

    private LocalDate start;

    private LocalDate end;

}