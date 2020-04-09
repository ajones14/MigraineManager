package org.launchcode.MigraineManager.models.WeatherAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

    private ForecastDay[] forecastday;

    public Forecast() { }

    public ForecastDay[] getForecastDay() {
        return forecastday;
    }

    public void setForecastDay(ForecastDay[] forecastDay) {
        this.forecastday = forecastDay;
    }
}
