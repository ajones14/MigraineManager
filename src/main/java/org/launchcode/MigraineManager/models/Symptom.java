package org.launchcode.MigraineManager.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

@Entity
public class Symptom {

    @Id
    @GeneratedValue
    private int id;

    private int userId;

    private ArrayList<LocalDate> datesOccurred = new ArrayList<>();

    private String name;

    public Symptom() { }

    public Symptom(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public static Comparator<Symptom> SymptomDateComparator = new Comparator<Symptom>() {
        @Override
        public int compare(Symptom o1, Symptom o2) {
            int first = o1.datesOccurred.size();
            int second = o2.datesOccurred.size();
            return second-first;
        }
    };

    public ArrayList<LocalDate> getDatesOccurred() {
        return datesOccurred;
    }

    public void addDateOccurred(LocalDate date) {
        this.datesOccurred.add(date);
    }

    public void removeDateOccurred(LocalDate date) {
        this.datesOccurred.remove(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
