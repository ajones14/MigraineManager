package org.launchcode.MigraineManager.models.WeatherAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

    private List<ForecastDay> forecastday;

    public Forecast() { }

    @Override
    public String toString() {
        String result = "";
        for (ForecastDay item : this.forecastday) {
            result += item.toString();
        }
        return "Forecast Day: " + result;
    }

    public ForecastDay getSelectedForecastday(String date) {
        for (ForecastDay day : this.forecastday) {
            if (day.getDate().equals(date)) {
                return day;
            }
        }
        return this.forecastday.get(0);
    }

    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<ForecastDay> forecastday) {
        this.forecastday = forecastday;
    }
}
