package org.launchcode.MigraineManager.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Entity
public class Triggers<DaysTrigger> {

    @Id
    private int userId;

    private static ArrayList<String> defaultTriggers;

    private ArrayList<String> addedTriggers;

    @OneToMany(mappedBy = "trigger")
    private ArrayList<DaysTrigger> daysTriggers;

    public Triggers() { }

    public Triggers(int userId, ArrayList<String> addedTriggers) {
        this.userId = userId;
        this.addedTriggers = addedTriggers;
    }

    public Triggers(int userId) {
        this.userId = userId;
    }

}
