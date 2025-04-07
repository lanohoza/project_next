package com.pajiniweb.oriachad_backend.domains.dtos;

import com.pajiniweb.oriachad_backend.domains.enums.InterViewCreateType;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewStatus;
import com.pajiniweb.oriachad_backend.domains.enums.InterviewType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InterviewDto {

	private Long id;
	private Long number;
	private InterviewType type;
	private LocalDateTime createdDate;
	private LocalDate interviewDate;
	private String target;
	private InterviewStatus status;
	private String taskTitle;
	private List<String> shedSettings;
	private String shedCategory;
	private  String description;
	private Long idStudent;
	private Long idGuidanceGroup;
	private InterViewCreateType createType;

	private List<StudentDto> studentDtos;

	List<Long> idDifficulties;
	List<Long> idSolutions;
	Long idFollowUp;
	Long followupNumber;
	private Long idShedCategory;
	List<Long> idShedSettings;


}