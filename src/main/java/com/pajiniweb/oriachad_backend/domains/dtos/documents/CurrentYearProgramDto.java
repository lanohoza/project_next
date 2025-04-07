package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentYearProgramDto {

	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;


	List<CurrentYearProgramTaskDto> tasks;


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CurrentYearProgramTaskDto {

private  Long id;
		private String month;

		private String week;
		private String taskTitle;
		List<IdValueDto> generalObjectsTitles;

	}
}
