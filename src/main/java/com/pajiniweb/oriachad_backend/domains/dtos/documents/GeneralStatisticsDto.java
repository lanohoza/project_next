package com.pajiniweb.oriachad_backend.domains.dtos.documents;

import com.pajiniweb.oriachad_backend.domains.enums.TypeEstablishment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralStatisticsDto {
	String wilayaName;
	String userName;
	String establishmentName;
	String yearTitle;
	TypeEstablishment type;
	GlobalData globalData;
	List<SpecialtyData> specialties;
	List<ClassesData> classesData;

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
		int FirstNumberOfClass;
		int SecondNumberOfClass;
		int ThirdNumberOfClass;
		int FirstNumberOfStudent;
		int SecondNumberOfStudent;
		int ThirdNumberOfStudent;
		int FourthNumberOfStudent;
		int FourthNumberOfClass;
	}
}
