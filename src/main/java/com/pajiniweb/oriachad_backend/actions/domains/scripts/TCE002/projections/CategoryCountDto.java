package com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.projections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCountDto {


	private String category;
	private Long count;

}
