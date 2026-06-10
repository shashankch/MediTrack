
package com.health.meditrack.entity;

import com.health.meditrack.entity.enums.AppointmentStatus;
import com.health.meditrack.exception.InvalidDataException;
import com.health.meditrack.service.interfaces.Searchable;

public class Appointment extends MedicalEntity implements Searchable, Cloneable {

    private final String patientId;
    private final String doctorId;
    private AppointmentStatus status;
    // Status used for simulation of appointment, can be extended to include more
    // details like time, date for appointment slots.

    public Appointment(String id, String p, String d) {
        super(id);
        this.patientId = p;
        this.doctorId = d;
        this.status = AppointmentStatus.PENDING;
    }

    public void cancel() {
        status = AppointmentStatus.CANCELLED;
    }

    public void confirm() {
        status = AppointmentStatus.CONFIRMED;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    // CLONE DEMO
    @Override
    public Appointment clone() throws CloneNotSupportedException {
        try {
            return (Appointment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InvalidDataException(e.getMessage());
        }
    }

    @Override
    public String getSummary() {
        return "Appt " + id + " patient:" + patientId + " doctor:" + doctorId + " " + status;
    }

    public String getPatientId() {
        return patientId;
    }

    @Override
    public boolean matches(String query) {

        return getId().equalsIgnoreCase(query)
                || getPatientId().equalsIgnoreCase(query)
                || getDoctorId().equalsIgnoreCase(query);
    }
}
