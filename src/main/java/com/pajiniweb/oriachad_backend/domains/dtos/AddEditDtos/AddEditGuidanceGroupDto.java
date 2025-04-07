package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditGuidanceGroupDto {
	private Long id;
	private String title;
	private List<Long> idStudents;
}
