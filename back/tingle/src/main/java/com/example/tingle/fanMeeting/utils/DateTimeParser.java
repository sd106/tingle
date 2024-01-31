package com.example.tingle.fanMeeting.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class DateTimeParser {
    private static final Map<String, Month> monthMap = new HashMap<>();

    static {
        monthMap.put("January", Month.JANUARY);
        monthMap.put("February", Month.FEBRUARY);
        monthMap.put("March", Month.MARCH);
        monthMap.put("April", Month.APRIL);
        monthMap.put("May", Month.MAY);
        monthMap.put("June", Month.JUNE);
        monthMap.put("July", Month.JULY);
        monthMap.put("August", Month.AUGUST);
        monthMap.put("September", Month.SEPTEMBER);
        monthMap.put("October", Month.OCTOBER);
        monthMap.put("November", Month.NOVEMBER);
        monthMap.put("December", Month.DECEMBER);
    }

    public static LocalDateTime parse(String sDateTime) {
        // 요일, 월, 일, 년, 시간, 시간대 등을 파싱
        String[] parts = sDateTime.split(" ");


        int day = Integer.parseInt(parts[2]);
        Month month = monthMap.get(parts[1]);
        int year = Integer.parseInt(parts[3]);
        String[] timeParts = parts[4].split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        int second = Integer.parseInt(timeParts[2]);

        // LocalDateTime 객체 생성
        return LocalDateTime.of(year, month, day, hour, minute, second);

    }
}
