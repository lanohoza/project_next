package com.pajiniweb.oriachad_backend.domains.dtos;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfficialTxtDto {

	private Long id;

	private String number;

	private String title;

	private LocalDate date;

	private Long idOfficielTextCategory;
}
