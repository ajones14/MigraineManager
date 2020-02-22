package org.launchcode.MigraineManager.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Symptoms {

    @Id
    private int userId;

    private static ArrayList<String> defaultSymptoms;

    private ArrayList<String> addedSymptoms;

    public Symptoms() { }

    public Symptoms(int userId, ArrayList<String> addedSymptoms) {
        this.userId = userId;
        this.addedSymptoms = addedSymptoms;
    }

    public Symptoms(int userId) {
        this.userId = userId;
    }

    public ArrayList<String> getAllSymptoms() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(this.defaultSymptoms);
        result.addAll(this.addedSymptoms);
        return result;
    }

    public ArrayList<String> getDefaultSymptoms() {
        return defaultSymptoms;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<String> getAddedSymptoms() {
        return addedSymptoms;
    }

    public void setAddedSymptoms(ArrayList<String> addedSymptoms) {
        this.addedSymptoms = addedSymptoms;
    }
}
