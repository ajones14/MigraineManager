package org.launchcode.MigraineManager.models.WeatherAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hour {

    private String time;

    private double pressure_in;

    public Hour() { }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPressure_in() {
        return pressure_in;
    }

    public void setPressure_in(double pressure_in) {
        this.pressure_in = pressure_in;
    }
}
