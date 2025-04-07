package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.InterViewCreateType;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewStatus;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDetailDto {
	private Long id;
	private Long number;
	private InterviewType type;
	private LocalDateTime createdDate;
	private LocalDate interviewDate;
	private String target;
	private String yearTitle;
	private InterviewStatus status;
	private String taskTitle;
	private List<String> shedSettings;
	private String shedCategory;
	private  String description;
	private Long idStudent;
	private Long idGuidanceGroup;
	private InterViewCreateType createType;
	Long idFollowUp;
	private List<StudentDto> studentDtos;

	Long followupNumber;
}
