package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.SourceTechnicalCard;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralObjectiveDto {
	private Long id;
	private String content;
	private SourceTechnicalCard source;
	@Nullable
	private Long createdById;
	public List<OperateObjectiveDto> childItems;

	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class OperateObjectiveDto {

		private Long id;

		private String content;

		private SourceTechnicalCard source;

	}
}
