package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.FlowUpCreateType;
import com.pajiniweb.oriachad_backend.domains.enums.FollowupStatus;
import com.pajiniweb.oriachad_backend.domains.enums.FollowUpType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowUpDto {

	private Long id;
	private Long number;
	private FollowUpType type;
	private LocalDateTime createdDate;
	private String target;
	private FollowupStatus status;
	private String taskTitle;
	private String resourceUrl;
	private  String description;
	private Long idStudent;
	private List<StudentDto> studentDtos;

	private Long idGuidanceGroup;
	private FlowUpCreateType createType;

	private List<String> shedSettings;
	private String shedCategory;
	private Long idShedCategory;
	List<Long> idShedSettings;
}