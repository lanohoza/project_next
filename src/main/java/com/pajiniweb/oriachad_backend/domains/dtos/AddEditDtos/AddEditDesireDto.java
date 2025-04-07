package com.pajiniweb.oriachad_backend.domains.dtos.AddEditDtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEditDesireDto {

	private Long idStudent;
	private Long idGuidanceSpeciality;
	Integer order;

}
