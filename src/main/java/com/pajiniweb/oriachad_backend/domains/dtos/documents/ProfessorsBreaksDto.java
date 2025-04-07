package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorsBreaksDto {
	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;

	List<BreakDay> breakDays;
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class BreakDay {
		String title;
		String sunday;
		String monday;
		String tuesday;
		String wednesday;
		String thursday;
	}
}
