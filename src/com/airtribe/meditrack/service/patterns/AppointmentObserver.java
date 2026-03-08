
package com.airtribe.meditrack.service.patterns;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.service.interfaces.Observer;

public class AppointmentObserver implements Observer {

    @Override
    public void update(Appointment appointment) {
        System.out.println("Appointment Details: " + appointment.getSummary());

    }
}
