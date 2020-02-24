package org.launchcode.MigraineManager.models;


import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
public class Migraine {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private ArrayList<String> symptoms;

    @NotNull
    private int userId;

    public Migraine () {}

    public Migraine(LocalDateTime startTime, LocalDateTime endTime, int userId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    public int getId () {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
