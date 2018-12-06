package com.schedulesmadeeasy.schedular;

/**
 * Shift helper class
 */
public class Shift {
    String company;
    String day;
    int time;

    public Shift(){};

    public Shift(String company, String day, int time) {
        this.company = company;
        this.day = day;
        this.time = time;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTime() {
        return time;
    }

    public void setTime(String status) {
        this.time = time;
    }

}


