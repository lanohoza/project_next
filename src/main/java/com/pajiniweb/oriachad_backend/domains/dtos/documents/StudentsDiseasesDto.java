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
public class StudentsDiseasesDto {
	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;
	List<DiseasesStudent> diseasesStudents;
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DiseasesStudent {
		String fullName;
		String classTitle;

	}
}
