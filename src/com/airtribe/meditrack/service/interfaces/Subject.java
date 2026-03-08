package com.airtribe.meditrack.service.interfaces;

import com.airtribe.meditrack.entity.Appointment;

public interface Subject {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Appointment appointment);
}