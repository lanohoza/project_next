package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.entities.Desire;
import com.pajiniweb.oriachad_backend.domains.entities.DesireSpecialty;
import com.pajiniweb.oriachad_backend.domains.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithDesireDto {

	private Long id;
	private String nbrRakmana;
	private String codeStudent;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private String placeBirth;

	Map<Integer,Long> guidanceSpecialities = new HashMap<>();

	public StudentWithDesireDto(Student student, Desire desire) {
		this.id = student.getId();
		this.nbrRakmana = student.getNbrRakmana();
		this.codeStudent = student.getCodeStudent();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.birthDate = student.getBirthDate();
		this.placeBirth = student.getPlaceBirth();
		if (desire != null)
			guidanceSpecialities = desire.getDesireSpecialties().stream()
					.collect(Collectors.toMap(
							DesireSpecialty::getOrder  ,
							DesireSpecialty::getIdGuidanceSpeciality
					));
	}
}
