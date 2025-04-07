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
public class StudentsBreaksDto {
	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;

	List<ClassBreakDay> classBreakDays;
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ClassBreakDay {
		String title;
		String sunday;
		String monday;
		String tuesday;
		String wednesday;
		String thursday;
	}
}
