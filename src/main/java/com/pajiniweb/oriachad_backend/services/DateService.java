package com.pajiniweb.oriachad_backend.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class DateService {

    public CurrentDateInfo getCurrentDateInfo() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentWeek = getWeekOfMonth(now) ;

        return new CurrentDateInfo(currentMonth, currentWeek);
    }

    private int getWeekOfMonth(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfMonth());
    }

    public static class CurrentDateInfo {
        private final int month;
        private final int week;

        public CurrentDateInfo(int month, int week) {
            this.month = month;
            this.week = week;
        }

        public int getMonth() {
            return month;
        }

        public int getWeek() {
            return week;
        }
    }
}
