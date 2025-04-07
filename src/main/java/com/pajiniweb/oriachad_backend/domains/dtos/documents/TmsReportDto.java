package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TmsReportDto {
	String wilayaName;
	String userName;
	String establishmentName;
	String trimestreTitle;
	String yearTitle;
	TypeEstablishment type;
	GlobalData globalData;
	List<SpecialtyData> specialties;
	List<ClassesData> classesData;
	List<ActivitiesReportDto.TaskReportDto> tasks;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TaskReportDto{
		String title;
		LocalDate date;
	}
	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ClassesData {
		String title;
		int numberOfStudents;
	}
	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SpecialtyData {
		String title;
		int numberOfClass;
		int numberOfStudents;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GlobalData {
		int firstNumberOfClass;
		int secondNumberOfClass;
		int thirdNumberOfClass;
		int firstNumberOfStudent;
		int secondNumberOfStudent;
		int thirdNumberOfStudent;
		int fourthNumberOfStudent;
		int fourthNumberOfClass;
	}
}
