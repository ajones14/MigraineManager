package org.launchcode.MigraineManager.models.WeatherAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastWeather extends HistoryWeather {

    private Current current;

    public ForecastWeather() { }

    @Override
    public String toString() {
        return "Current: " + current.toString();
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
