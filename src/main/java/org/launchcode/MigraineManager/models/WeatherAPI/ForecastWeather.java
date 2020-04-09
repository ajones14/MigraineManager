package org.launchcode.MigraineManager.models.WeatherAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastWeather extends HistoryWeather {

    private Current current;

    public ForecastWeather() { }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
