package org.launchcode.MigraineManager.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Triggers {

    @Id
    private int userId;

    private List<LocalDate> datesOccurred = new ArrayList<>();

    private String name;

    public Triggers() { }

    public Triggers(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<LocalDate> getDatesOccurred() {
        return datesOccurred;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
