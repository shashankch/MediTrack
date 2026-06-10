package com.health.meditrack.util;

import com.health.meditrack.entity.Appointment;
import com.health.meditrack.service.interfaces.Subject;
import java.util.TimerTask;

public class AppointmentReminderTask extends TimerTask {

    private final Appointment appointment;
    private final Subject notificationService;
    private final String message;

    public AppointmentReminderTask(Appointment appointment,
            Subject notificationService, String message) {

        this.appointment = appointment;
        this.notificationService = notificationService;
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(message);
        notificationService.notifyObservers(appointment);
    }
}
