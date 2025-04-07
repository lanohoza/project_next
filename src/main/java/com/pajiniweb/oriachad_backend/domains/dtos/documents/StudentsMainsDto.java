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
public class StudentsMainsDto {
	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;
	List<Student> students;
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Student {
		String fullName;
		String classTitle;

	}
}
