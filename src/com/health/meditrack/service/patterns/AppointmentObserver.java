
package com.health.meditrack.service.patterns;

import com.health.meditrack.entity.Appointment;
import com.health.meditrack.service.interfaces.Observer;

public class AppointmentObserver implements Observer {

    @Override
    public void update(Appointment appointment) {
        System.out.println("Appointment Details: " + appointment.getSummary());

    }
}
