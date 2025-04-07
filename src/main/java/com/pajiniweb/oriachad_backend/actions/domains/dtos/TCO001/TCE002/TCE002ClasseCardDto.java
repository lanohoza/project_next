package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCO001.TCE002;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TCE002ClasseCardDto {

	String previousLevelName;

	List<TCE002SubjectAverageCategoryDto> subjectAverageCategories;

	List<String> diagnostics;


}
