
package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.service.interfaces.Searchable;

public class Doctor extends Person implements Searchable {

    private Specialization specialization;
    private double fee;
    // Doctor slots and availability can be added as needed, for now we keep it
    // simple.
    static {
        System.out.println("Doctor class loaded");
    }

    public Doctor(String id, String name, int age, Specialization sp, double fee) {
        super(id, name, age);
        this.specialization = sp;
        this.fee = fee;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public double getFee() {
        return fee;
    }

    @Override
    public String getSummary() {
        return "Doctor " + getId() + " " + getName() + " " + specialization + " fee:" + fee;
    }

    @Override
    public boolean matches(String query) {

        String q = query.toLowerCase();

        return getId().equalsIgnoreCase(query)
                || getName().toLowerCase().contains(q)
                || specialization.name().toLowerCase().contains(q);
    }

    @Override
    public boolean matches(int age) {
        return getAge() == age;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
