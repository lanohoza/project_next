package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActionTaskDto;
import com.pajiniweb.oriachad_backend.domains.enums.TaskStatus;
import com.pajiniweb.oriachad_backend.domains.enums.TypeTc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskWithActionsDto {

	private Long id;
	private String category;
	private TypeTc type;
	private String title;
	private String code;
	private Integer runMonth;
	private Integer runWeek;
	private TaskStatus status;
	private LocalDate createdDate;
	private LocalDate lastModifiedDate;
	private String yearTitle;
	List<ActionTaskDto> actions;
}
