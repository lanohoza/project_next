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
public class ActivitiesReportDto {

	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;
	//List<ActivityDto> activities;
	List<ActivityByDateDto> activities ;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TaskReportDto{
		String title;
		LocalDate date;
	}
}
