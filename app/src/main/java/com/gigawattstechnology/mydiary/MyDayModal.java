package com.gigawattstechnology.mydiary;

public class MyDayModal {
    String myDay_date;
    String myDay_text;

    public MyDayModal(String myDay_date, String myDay_text) {
        this.myDay_date = myDay_date;
        this.myDay_text = myDay_text;
    }

    public String getMyDay_date() {
        return myDay_date;
    }

    public String getMyDay_text() {
        return myDay_text;
    }
}
