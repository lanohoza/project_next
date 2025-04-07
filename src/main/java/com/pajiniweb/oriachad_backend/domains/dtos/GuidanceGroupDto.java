package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.entities.GuidanceGroup;
import com.pajiniweb.oriachad_backend.domains.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuidanceGroupDto {
	private Long id;
	private String title;
	private Integer studentCount;
	private List<StudentDto> studentDtos;
	private List<Long> idStudents;

	public GuidanceGroupDto(GuidanceGroup guidanceGroup, Integer studentCount) {
		this.id = guidanceGroup.getId();
		this.title = guidanceGroup.getTitle();
		//this.id = guidanceGroup.getId();
		this.studentCount = studentCount;
	}

	// Constructor for creating DTO with a set of students
	public GuidanceGroupDto(GuidanceGroup guidanceGroup) {
		this.id = guidanceGroup.getId();
		this.title = guidanceGroup.getTitle();
		this.studentCount = guidanceGroup.getStudents().size(); // Student count is set here
		this.studentDtos = guidanceGroup.getStudents().stream() // Mapping each Student to StudentDto
				.map(student -> StudentDto.builder()
						.nbrRakmana(student.getNbrRakmana())
						.firstName(student.getFirstName())
						.lastName(student.getLastName())
						.birthDate(student.getBirthDate())
						.classeTitle(student.getCurrentClass().getTitle())
						.build())
				.toList();
	}
}
