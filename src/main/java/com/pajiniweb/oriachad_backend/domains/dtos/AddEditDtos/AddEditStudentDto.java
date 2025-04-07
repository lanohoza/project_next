package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;


import com.pajiniweb.oriachad_backend.domains.enums.Gender;
import com.pajiniweb.oriachad_backend.domains.enums.StudyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditStudentDto {

	private Long id;
	@NotBlank

	private String nbrRakmana;

	private String codeStudent;
	@NotBlank

	private String firstName;
	@NotBlank

	private String lastName;
	@NotNull

	private Gender sexe;
	@NotNull

	private LocalDate birthDate;
	@NotBlank
	private String placeBirth;
	@NotNull
	private StudyType schoolingSystem;
	@NotNull
	private Long idClasse;
	@NotNull

	private Boolean repeatClasseActual;
	@NotNull

	private Integer nbrRepeatClasse;
	@NotBlank

	private String fatherProfession;
	@NotBlank

	private String motherProfession;
	@NotBlank

	private String tutorName;
	@NotBlank

	private String tutorMobPhone;
	@NotBlank

	private String tutorEmail;
	@NotNull

	private LocalDate dateStudentInscription;
	private String healthProblem;
	@NotNull

	private Boolean isMotherOrphan;
	@NotNull

	private Boolean isFatherOrphan;

	@NotNull

	private Boolean isNeed;
	@NotNull

	private Boolean isDisease;
	@NotNull

	private Boolean isMain;

}
