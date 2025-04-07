package com.pajiniweb.oriachad_backend.actions.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShedSettingConditionDto {
	private Long id;
	private String syndromeDiagnostic;

	public ShedSettingConditionDto(Long id, String shedCategoryName, String shedCategoryCode, String syndromeDiagnostic) {

		this.id = id;
		this.syndromeDiagnostic = String.format("%s - %s : %s",shedCategoryCode, shedCategoryName, syndromeDiagnostic);
	}
}
