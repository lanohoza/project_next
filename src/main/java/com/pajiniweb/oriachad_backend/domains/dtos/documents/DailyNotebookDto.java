package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import com.pajiniweb.oriachad_backend.actions.domains.dtos.ActivityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyNotebookDto {

	String wilayaName;
	String userName;
	String establishmentName;
	String reportNumber;
	String day;
	String yearTitle;
	List<ActivityDto> activities;
}
