package com.pajiniweb.oriachad_backend.domains.dtos;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClasseBreakDto {
	private Long id;
	private Integer breakDay;

	private LocalTime startHour;

	private LocalTime endHour;
}
