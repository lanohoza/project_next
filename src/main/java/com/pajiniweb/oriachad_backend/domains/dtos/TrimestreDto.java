package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.TrimestreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrimestreDto {
    private Long id;
    private Long idYear;

    private LocalDate start;
    private LocalDate end;
    private TrimestreType type;

    private Integer number;
    private String title;


}
