package com.health.meditrack.service.interfaces;

import com.health.meditrack.entity.Appointment;

public interface Subject {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Appointment appointment);
}