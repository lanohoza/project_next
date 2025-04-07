package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;


import com.pajiniweb.oriachad_backend.domains.dtos.ClasseDto;
import com.pajiniweb.oriachad_backend.domains.dtos.ProfessorBreakDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddEditProfessorDto {

	private Long id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String phoneNumber;
	@NotBlank
	private String email;
	@NonNull Long idSubject;
	@NonNull Boolean coordinator;

	List<ProfessorBreakDto> breaks;

	List<Long> classes;
}
