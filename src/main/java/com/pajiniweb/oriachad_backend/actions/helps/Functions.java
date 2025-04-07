package com.pajiniweb.oriachad_backend.actions.helps;

import com.pajiniweb.oriachad_backend.actions.domains.scripts.TCE002.TCE002ConditionOperate;

import java.util.List;

public  class Functions {
	public static boolean applyOperate(TCE002ConditionOperate operate, Double average, Double averageMax, Double value) {
		if (average == null || value == null)
			return false;
		return switch (operate) {
			case less -> average > value;
			case greater -> average < value;
			case lessEqual -> average >= value;
			case greaterEqual -> average <= value;
			case equal -> average == value;
			case btwEqual -> average <= value && averageMax >= value;
			default -> false;
		};
	}
}
