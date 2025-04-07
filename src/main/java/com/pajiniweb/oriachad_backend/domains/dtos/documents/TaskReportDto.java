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
public class TaskReportDto {

	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;
	String taskTitle;
	String trimestreTitle;
	String monthTitle;
	String weekTitle;
	String materielToots;
	List<String> actionTitles;
	List<String> humanTools;
	List<String> generalObjectsTitles;
	List<String> difficulties;


	List<OfficialTxtDto> officialTxts;


	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class OfficialTxtDto {

		private Long id;

		private String number;

		private String title;

		private LocalDate date;
	}
}
