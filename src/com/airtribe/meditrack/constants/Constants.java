
package com.airtribe.meditrack.constants;

public class Constants {
    public static final double TAX_RATE = 0.18;
    public static final long NOTIFICATION_TIME_DELAY = 5000;

    public static final String PATIENT_FILE = "data/patients.csv";
    public static final String DOCTOR_FILE = "data/doctors.csv";
    public static final String APPOINTMENT_FILE = "data/appointments.csv";

    static {
        System.out.println("[Config] Constants loaded");
    }
}
