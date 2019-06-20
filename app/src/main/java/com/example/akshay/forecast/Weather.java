package com.example.akshay.forecast;
public class Weather {
    String date;
    String minTemp;
    String maxTemp;
    String desc;
    public String getDate() {
        String[] sep = date.split("T");
        return sep[0];
    }
    public void setDate(String date) { this.date = date;
    }
    public String getMinTemp() {
        return minTemp;
    }
    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }
    public String getMaxTemp() {
        return maxTemp;
    }
    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    }