package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.service.interfaces.AppointmentService;
import com.airtribe.meditrack.service.interfaces.DoctorService;
import com.airtribe.meditrack.service.interfaces.PatientService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static boolean isDataLoadingRequired(String args[]) {

        return args.length > 0 && args[0].equals("--loadData");
    }

    private static List<String[]> read(String path) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (Exception e) {
            System.out.println("CSV read skipped: " + e.getMessage());
        }
        return rows;
    }

    private static void write(String path, List<String> rows) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            for (String row : rows) {
                bw.write(row);
                bw.newLine();
            }

        } catch (Exception e) {
            System.out.println("CSV write skipped: " + e.getMessage());
        }
    }

    public static void loadData(DoctorService doctorService, PatientService patientService,
            AppointmentService appointmentService) {

        List<String[]> doctors = read(Constants.DOCTOR_FILE);
        for (String[] r : doctors) {
            doctorService.addDoctor(r[0], Integer.parseInt(r[1]), Specialization.valueOf(r[2]),
                    Double.parseDouble(r[3]));
        }

        List<String[]> patients = read(Constants.PATIENT_FILE);
        for (String[] r : patients) {
            patientService.addPatient(
                    r[0],
                    Integer.parseInt(r[1]));
        }

        List<String[]> appointments = read(Constants.APPOINTMENT_FILE);
        for (String[] r : appointments) {
            appointmentService.createAppointment(
                    r[0],
                    r[1]);
        }

        System.out.println("CSV data loaded...");
    }

    public static void saveData(DoctorService doctorService,
            PatientService patientService, AppointmentService appointmentService) {

        List<String> doctorRows = new ArrayList<>();

        doctorRows.add("name,age,specialization,fee");

        for (Doctor d : doctorService.getAllDoctors()) {

            doctorRows.add(
                    d.getName() + "," +
                            d.getAge() + "," +
                            d.getSpecialization() + "," +
                            d.getFee());
        }

        write(Constants.DOCTOR_FILE, doctorRows);

        List<String> patientRows = new ArrayList<>();

        patientRows.add("name,age");

        for (Patient p : patientService.getAllPatients()) {

            patientRows.add(
                    p.getName() + "," +
                            p.getAge());
        }

        write(Constants.PATIENT_FILE, patientRows);

        List<String> appointmentRows = new ArrayList<>();

        appointmentRows.add("patientId,doctorId,status");

        for (Appointment a : appointmentService.getAllAppointments()) {

            appointmentRows.add(
                    a.getPatientId() + "," +
                            a.getDoctorId() + "," + a.getStatus());
        }

        write(Constants.APPOINTMENT_FILE, appointmentRows);

        System.out.println("CSV data saved...");
    }

}
