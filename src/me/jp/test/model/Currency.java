package me.jp.test.model;

import java.time.DayOfWeek;

public class Currency {

    private String code;
    private DayOfWeek openingDay;
    private DayOfWeek closingDay;

    public Currency(String code, DayOfWeek openingDay, DayOfWeek closingDay) {
        this.code = code;
        this.openingDay = openingDay;
        this.closingDay = closingDay;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DayOfWeek getOpeningDay() {
        return openingDay;
    }

    public void setOpeningDay(DayOfWeek openingDay) {
        this.openingDay = openingDay;
    }

    public DayOfWeek getClosingDay() {
        return closingDay;
    }

    public void setClosingDay(DayOfWeek closingDay) {
        this.closingDay = closingDay;
    }
}
