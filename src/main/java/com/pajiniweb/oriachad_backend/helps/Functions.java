package com.pajiniweb.oriachad_backend.helps;

import java.util.List;

public class Functions {
	static List<String> months = List.of("جانفي", "فيفري", "مارس", "أفريل", "ماي", "جوان", "جويلية", "أوت", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر");

	static List<String> weeks = List.of("الأسبوع الأول", "الأسبوع الثاني", "الأسبوع الثالث", "الأسبوع الرابع", "الأسبوع الخامس");

	public static String getWeekTitle(Integer weekNumber) {
		if (weekNumber != null && weekNumber <= weeks.size())
			return weeks.get(weekNumber - 1);
		return "";
	}

	public static String getMonthTitle(Integer monthsNumber) {
		if (monthsNumber != null && monthsNumber <= months.size())
			return months.get(monthsNumber - 1);
		return "";
	}
}
