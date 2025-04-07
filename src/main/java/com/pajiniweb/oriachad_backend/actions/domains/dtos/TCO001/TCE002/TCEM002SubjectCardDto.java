package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCO001.TCE002;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections.CategoryCountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TCEM002SubjectCardDto {

	List<CategoryCountDto> averageCategoryCounts;

	Double average;

	List<String> diagnostics;
}
