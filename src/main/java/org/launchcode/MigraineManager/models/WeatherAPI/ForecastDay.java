package org.launchcode.MigraineManager.models.WeatherAPI;

import java.util.List;

public class ForecastDay {

    private String date;

    private Day day;

    private List<Hour> hour;

    public ForecastDay() { }

    @Override
    public String toString() {
        String result = "";
        for (Hour item : this.hour) {
            result += item.toString();
        }
        return "Date: " + this.date + "\nDay: " + this.day.toString() + "\nHour: " + result;
    }

    public double calculateAveragePressure() {
        double total = 0;
        for (int i = 0; i < hour.size(); i++) {
            total += hour.get(i).getPressure_in();
        }
        return Math.round(total/hour.size() * 10)/10;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public List<Hour> getHour() {
        return hour;
    }

    public void setHour(List<Hour> hour) {
        this.hour = hour;
    }
}
