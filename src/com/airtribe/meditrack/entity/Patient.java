
package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.interfaces.Searchable;
import java.util.*;

public class Patient extends Person implements Searchable, Cloneable {

    private List<String> history = new ArrayList<>();

    public Patient(String id, String name, int age) {
        super(id, name, age);
    }

    public void addHistory(String h) {
        history.add(h);
    }

    // CLONE DEMO
    @Override
    public Patient clone() throws CloneNotSupportedException {
        try {
            Patient p = (Patient) super.clone();
            p.history = new ArrayList<>(history);
            return p;
        } catch (CloneNotSupportedException e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public String getSummary() {
        return "Patient " + getId() + " " + getName() + " age:" + getAge();
    }

    @Override
    public boolean matches(String query) {

        return getId().equalsIgnoreCase(query)
                || getName().toLowerCase().contains(query.toLowerCase());
    }

    @Override
    public boolean matches(int age) {
        return getAge() == age;
    }
}
