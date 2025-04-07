package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import com.pajiniweb.oriachad_backend.domains.dtos.InterviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowupReportDto {

	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;

	List<FollowupByDateDto> flowupByDateDtos ;


}
