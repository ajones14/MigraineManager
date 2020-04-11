package org.launchcode.MigraineManager.models.WeatherAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

    private ForecastDay[] forecastday;

    public Forecast() { }

    @Override
    public String toString() {
        return "Forecast Day: " + forecastday.toString();
    }

    public ForecastDay[] getForecastDay() {
        return forecastday;
    }

    public void setForecastDay(ForecastDay[] forecastDay) {
        this.forecastday = forecastDay;
    }
}
