package org.launchcode.MigraineManager.models;


import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class Migraine {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private LocalDate startTime;

    private LocalDate endTime;

    private ArrayList<String> symptoms;

    @NotNull
    private int userId;

    public Migraine () {}

    public Migraine(LocalDate startTime, LocalDate endTime, int userId, ArrayList<String> symptoms) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.symptoms = symptoms;
    }

    public Migraine(LocalDate startTime, LocalDate endTime, int userId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    public int getId () {
        return id;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
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
