package org.launchcode.MigraineManager.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "`trigger`")
public class Trigger {

    @Id
    @GeneratedValue
    private int id;

    private int userId;

    private ArrayList<LocalDate> datesOccurred = new ArrayList<>();

    private String name;

    public Trigger () { }

    public Trigger (String name) {
        this.userId = userId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<LocalDate> getDatesOccurred() {
        return datesOccurred;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
