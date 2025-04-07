package com.pajiniweb.oriachad_backend.actions.domains.dtos.TCE002;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TCEM002StudentCardDto {

	String previousLevelName;

	Map<String,Double> subjectAverages;

	List<String> diagnostics;

}
