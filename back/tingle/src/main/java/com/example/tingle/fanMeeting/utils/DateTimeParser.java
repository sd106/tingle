package com.example.tingle.fanMeeting.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class DateTimeParser {
    public static LocalDateTime parse(String sDateTime) {
        // 요일, 월, 일, 년, 시간, 시간대 등을 파싱
        String[] parts = sDateTime.split(" ");

        Map<String, Month> monthMap = new HashMap<>();

        monthMap.put("Jan", Month.JANUARY);
        monthMap.put("Feb", Month.FEBRUARY);
        monthMap.put("Mar", Month.MARCH);
        monthMap.put("Apr", Month.APRIL);
        monthMap.put("May", Month.MAY);
        monthMap.put("Jun", Month.JUNE);
        monthMap.put("Jul", Month.JULY);
        monthMap.put("Aug", Month.AUGUST);
        monthMap.put("Sep", Month.SEPTEMBER);
        monthMap.put("Oct", Month.OCTOBER);
        monthMap.put("Nov", Month.NOVEMBER);
        monthMap.put("Dec", Month.DECEMBER);

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
