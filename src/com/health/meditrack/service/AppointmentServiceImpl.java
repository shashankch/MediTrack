package com.health.meditrack.service;

import com.health.meditrack.constants.Constants;
import com.health.meditrack.entity.*;
import com.health.meditrack.exception.EntityNotFoundException;
import com.health.meditrack.service.interfaces.AppointmentService;
import com.health.meditrack.service.interfaces.DoctorService;
import com.health.meditrack.service.interfaces.PatientService;
import com.health.meditrack.service.interfaces.Subject;
import com.health.meditrack.util.*;
import static com.health.meditrack.util.Validator.isNull;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentServiceImpl implements AppointmentService {

    private final DataStore<Appointment> store = new DataStore<>();
    private final IdGenerator idGenerator = IdGenerator.getInstance();

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final Subject notificationService;
    private final Timer timer;

    // Constructor Injection
    public AppointmentServiceImpl(DoctorService doctorService,
            PatientService patientService, Subject notificationService) {

        this.doctorService = doctorService;
        this.patientService = patientService;
        this.notificationService = notificationService;
        timer = new Timer(true);
    }

    // CREATE APPOINTMENT
    @Override
    public Appointment createAppointment(String patientId, String doctorId) {

        Patient patient = patientService.getPatient(patientId);
        Doctor doctor = doctorService.getDoctor(doctorId);

        if (isNull(patient)) {
            throw new EntityNotFoundException("Patient not found: " + patientId);
        }

        if (isNull(doctor)) {
            throw new EntityNotFoundException("Doctor not found: " + doctorId);
        }

        String id = idGenerator.nextAppointment();

        Appointment appointment = new Appointment(id, patientId, doctorId);

        store.save(id, appointment);

        // NOTIFY PATIENT APPOINTMENT CONFIRMED - SIMULATION (DEMO FOR TIMER-TASK AND
        // OBSERVER)
        timer.schedule(
                new AppointmentReminderTask(appointment, notificationService,
                        "Appointment confirmed for doctor " + doctor.getName()),
                Constants.NOTIFICATION_TIME_DELAY); // 5 seconds delay for demo

        return appointment;
    }

    // CANCEL APPOINTMENT
    @Override
    public boolean cancelAppointment(String id) {

        Appointment appointment = store.get(id);

        if (isNull(appointment)) {
            return false;
        }

        appointment.cancel();

        store.update(id, appointment);

        // NOTIFY PATIENT SLOTS AVAILABLE - SIMULATION (DEMO FOR TIMER-TASK AND
        // OBSERVER)
        timer.schedule(
                new AppointmentReminderTask(appointment, notificationService,
                        "Appointment cancelled. Slots available for doctor " + appointment.getDoctorId()),
                Constants.NOTIFICATION_TIME_DELAY); // 5 seconds delay for demo

        return true;
    }

    // GET SINGLE APPOINTMENT
    @Override
    public Appointment getAppointment(String id) {
        return store.get(id);
    }

    // LIST ALL APPOINTMENTS
    @Override
    public void listAppointments() {
        List<Appointment> appointments = store.all();
        if (appointments.isEmpty()) {
            throw new EntityNotFoundException("No appointments found.");
        }
        System.out.println("\n--- Appointment List ---");
        appointments.forEach(a -> System.out.println(
                "AppointmentID: " + a.getId()
                        + " | PatientID: " + a.getPatientId()
                        + " | DoctorID: " + a.getDoctorId()
                        + " | Status: " + a.getStatus()));
    }

    // ANALYTICS: APPOINTMENTS PER DOCTOR
    @Override
    public Map<String, Long> appointmentsPerDoctor() {

        return store.all()
                .stream()
                .collect(Collectors.groupingBy(
                        appt -> {

                            Doctor doctor = doctorService.getDoctor(appt.getDoctorId());

                            if (!isNull(doctor)) {
                                return doctor.getName() +
                                        " (" + doctor.getId() + ")";
                            }

                            return "Unknown Doctor (" +
                                    appt.getDoctorId() + ")";
                        },
                        Collectors.counting()));
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return store.all();
    }

}