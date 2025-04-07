package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections;


import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectAverageDto {
	private String subjectTitle;
	private Double averageValue;
	private String gradeCategory;
}
