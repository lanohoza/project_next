package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TCE002SubjectAverageCategoryDto {


	private String subjectTitle;
	private Double subjectAverage;
	private int category_18_20;
	private int category_16_18;
	private int category_14_16;
	private int category_12_14;
	private int category_10_12;
	private int category_greater_10;
	private int category_less_10;

	// Constructor
	public TCE002SubjectAverageCategoryDto(String subjectTitle) {
		this.subjectTitle = subjectTitle;
		this.category_18_20 = 0;
		this.category_16_18 = 0;
		this.category_14_16 = 0;
		this.category_12_14 = 0;
		this.category_10_12 = 0;
		this.category_greater_10 = 0;
		this.category_less_10 = 0;
		this.subjectAverage=0.0;
	}

}
