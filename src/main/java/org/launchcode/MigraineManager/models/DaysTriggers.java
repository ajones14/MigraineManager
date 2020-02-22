package org.launchcode.MigraineManager.models;


import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class DaysTriggers {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private LocalDate date;

    private ArrayList<String> triggerList;

    @ManyToOne
    private Triggers trigger;

    public DaysTriggers () { }

    public DaysTriggers (LocalDate date, Triggers trigger, ArrayList<String> triggerList) {
        this.date = date;
        this.trigger = trigger;
        this.triggerList = triggerList;
    }

    public DaysTriggers (LocalDate date, Triggers trigger) {
        this.date = date;
        this.trigger = trigger;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ArrayList<String> getTriggerList() {
        return triggerList;
    }

    public void setTriggerList(ArrayList<String> triggerList) {
        this.triggerList = triggerList;
    }

    public Triggers getTrigger() {
        return trigger;
    }

    public void setTrigger(Triggers trigger) {
        this.trigger = trigger;
    }
}
