package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import com.pajiniweb.oriachad_backend.actions.domains.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActivityDto {

	private Long id;

	private String content;
	private Long idUser;
	private ActivityType type;
	private LocalDate createdDate;
}
