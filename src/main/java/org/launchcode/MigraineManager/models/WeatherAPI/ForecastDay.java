package org.launchcode.MigraineManager.models.WeatherAPI;

public class ForecastDay {

    private String date;

    private Day day;

    private Hour[] hour;

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
        for (int i = 0; i < hour.length; i++) {
            total += hour[i].getPressure_in();
        }
        return total/hour.length;
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

    public Hour[] getHour() {
        return hour;
    }

    public void setHour(Hour[] hour) {
        this.hour = hour;
    }
}
