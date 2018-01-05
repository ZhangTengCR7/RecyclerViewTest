package com.zt.calendar;

/**
 * Created by zhangteng on 2017/12/28.
 */

public class DateBean {

    private int year;
    private int month;
    private int day;
    private boolean flag;

    public DateBean() {
    }

    public DateBean(int year, int month, int day, boolean flag) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.flag = flag;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "DateBean{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", flag=" + flag +
                '}';
    }
}
