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
public class TechnicalCardDocumentDto {

	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;
	String type;
	String code;
	String category;
	private String feedback;

	String title;
	String trimestreTitle;
	String monthTitle;
	String weekTitle;
	String materielToots;
	List<String> actionTitles;
	List<String> humanTools;
	List<String> generalObjectsTitles;
	List<String> operateObjectsTitles;
	List<String> difficulties;
    List<String> audiences;
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
