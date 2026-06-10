package com.health.meditrack.service.interfaces;

import com.health.meditrack.entity.Appointment;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    Appointment createAppointment(String patientId, String doctorId);

    boolean cancelAppointment(String appointmentId);

    Appointment getAppointment(String id);

    List<Appointment> getAllAppointments();

    Map<String, Long> appointmentsPerDoctor();

    void listAppointments();

}
