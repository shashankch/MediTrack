package com.health.meditrack;

import com.health.meditrack.entity.Appointment;
import com.health.meditrack.entity.BillSummary;
import com.health.meditrack.entity.Doctor;
import com.health.meditrack.entity.Patient;
import com.health.meditrack.entity.enums.Specialization;
import com.health.meditrack.exception.AppointmentNotFoundException;
import com.health.meditrack.exception.EntityNotFoundException;
import com.health.meditrack.exception.InvalidDataException;
import com.health.meditrack.service.*;
import com.health.meditrack.service.interfaces.*;
import com.health.meditrack.util.AIHelper;
import com.health.meditrack.util.CSVUtil;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final PatientService patientService = new PatientServiceImpl();
    private static final DoctorService doctorService = new DoctorServiceImpl();
    private static final Subject notificationService = new NotificationServiceImpl();
    private static final AppointmentService appointmentService = new AppointmentServiceImpl(doctorService,
            patientService,
            notificationService);
    private static final SearchService searchService = new SearchService(patientService, doctorService,
            appointmentService);
    private static final BillingService billingService = new BillingServiceImpl(appointmentService, doctorService);

    public static void main(String[] args) {

        if (CSVUtil.isDataLoadingRequired(args)) {
            System.out.println("Loading CSV data...");
            CSVUtil.loadData(doctorService, patientService, appointmentService);
        }

        while (true) {

            System.out.println("\n===============================");
            System.out.println("        MediTrack - Clinic & Appointment Management System");
            System.out.println("===============================");
            System.out.println("MAIN MENU");
            System.out.println("1 Patient Management");
            System.out.println("2 Doctor Management");
            System.out.println("3 Appointment Management");
            System.out.println("4 Billing");
            System.out.println("5 Search");
            System.out.println("6 Analytics");
            System.out.println("7 AI Recommendation");
            System.out.println("8 Save/Load CSV");
            System.out.println("0 Exit");
            System.out.println("===============================");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1 -> patientMenu();
                    case 2 -> doctorMenu();
                    case 3 -> appointmentMenu();
                    case 4 -> billingMenu();
                    case 5 -> searchMenu();
                    case 6 -> analyticsMenu();
                    case 7 -> aiRecommendation();
                    case 8 -> csvMenu();
                    case 0 -> {
                        System.out.println("Exiting MediTrack...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (EntityNotFoundException | InvalidDataException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
            }
        }
    }

    // ===============================
    // PATIENT MENU
    // ===============================

    private static void patientMenu() {

        while (true) {

            System.out.println("\n--- Patient Management ---");
            System.out.println("1 Add Patient");
            System.out.println("2 Update Patient");
            System.out.println("3 Delete Patient");
            System.out.println("4 List Patients");
            System.out.println("0 Back");
            System.out.println("===============================");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            try {
                switch (choice) {

                    case 1 -> {
                        String name = readStringInput("Name: ");

                        System.out.print("Age: ");
                        int age = scanner.nextInt();

                        Patient patient = patientService.addPatient(name, age);
                        if (patient != null) {
                            System.out.println("Patient created with ID: " + patient.getId());
                        }
                    }
                    case 2 -> {
                        String id = readStringInput("Patient ID: ");

                        String name = readStringInput("New Name: ");

                        System.out.print("New Age: ");
                        int age = scanner.nextInt();

                        Patient patient = patientService.updatePatient(id, name, age);
                        if (patient != null) {
                            System.out.println("Patient updated with ID: " + patient.getId());
                        }
                    }
                    case 3 -> {
                        String id = readStringInput("Patient ID: ");

                        Patient patient = patientService.deletePatient(id);
                        if (patient != null) {
                            System.out.println("Patient deleted with ID: " + patient.getId());
                        }
                    }
                    case 4 -> patientService.listPatients();
                    case 0 -> {
                        return;
                    }

                    default -> System.out.println("Invalid option");
                }
            } catch (EntityNotFoundException | InvalidDataException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // ===============================
    // DOCTOR MENU
    // ===============================

    private static void doctorMenu() {

        while (true) {

            System.out.println("\n--- Doctor Management ---");
            System.out.println("1 Add Doctor");
            System.out.println("2 Update Doctor");
            System.out.println("3 Delete Doctor");
            System.out.println("4 List Doctors");
            System.out.println("0 Back");
            System.out.println("===============================");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            try {
                switch (choice) {

                    case 1 -> {
                        String name = readStringInput("Name: ");

                        System.out.print("Age: ");
                        int age = scanner.nextInt();

                        System.out.println("Select Specialization:");

                        Specialization[] values = Specialization.values();

                        for (int i = 0; i < values.length; i++) {
                            System.out.println((i + 1) + ". " + values[i]);
                        }

                        int specChoice = scanner.nextInt();

                        Specialization specialization = values[specChoice - 1];

                        System.out.print("Consultation Fee: ");
                        double fee = scanner.nextDouble();

                        Doctor doctor = doctorService.addDoctor(name, age, specialization, fee);

                        if (doctor != null) {
                            System.out.println("Doctor created with ID: " + doctor.getId());
                        }
                    }
                    case 2 -> {
                        String id = readStringInput("Doctor ID: ");

                        String name = readStringInput("New Name: ");

                        System.out.print("New Age: ");
                        int age = scanner.nextInt();

                        System.out.println("Select New Specialization:");

                        Specialization[] values = Specialization.values();

                        for (int i = 0; i < values.length; i++) {
                            System.out.println((i + 1) + ". " + values[i]);
                        }

                        int specChoice = scanner.nextInt();

                        Specialization specialization = values[specChoice - 1];

                        System.out.print("New Consultation Fee: ");
                        double fee = scanner.nextDouble();

                        Doctor doctor = doctorService.updateDoctor(id, name, age, specialization, fee);
                        if (doctor != null) {
                            System.out.println("Doctor updated with ID: " + doctor.getId());
                        }
                    }

                    case 3 -> {
                        String id = readStringInput("Doctor ID: ");

                        Doctor doctor = doctorService.deleteDoctor(id);
                        if (doctor != null) {
                            System.out.println("Doctor deleted with ID: " + doctor.getId());
                        }
                    }

                    case 4 -> doctorService.listDoctors();

                    case 0 -> {
                        return;
                    }

                    default -> System.out.println("Invalid option");
                }
            } catch (EntityNotFoundException | InvalidDataException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // ===============================
    // APPOINTMENT MENU
    // ===============================

    private static void appointmentMenu() {

        while (true) {

            System.out.println("\n--- Appointment Management ---");
            System.out.println("1 Create Appointment");
            System.out.println("2 Cancel Appointment");
            System.out.println("3 List Appointments");
            System.out.println("0 Back");
            System.out.println("===============================");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            try {
                switch (choice) {

                    case 1 -> {
                        String patientId = readStringInput("Patient ID: ");

                        String doctorId = readStringInput("Doctor ID: ");

                        Appointment appointment = appointmentService.createAppointment(patientId, doctorId);
                        if (appointment != null) {
                            System.out.println("Appointment created with ID: " + appointment.getId());
                        }
                    }

                    case 2 -> {
                        String id = readStringInput("Appointment ID: ");

                        boolean cancelled = appointmentService.cancelAppointment(id);
                        if (cancelled) {
                            System.out.println("Appointment cancelled successfully.");
                        } else {
                            System.out.println("Failed to cancel appointment.");
                        }
                    }
                    case 3 -> appointmentService.listAppointments();
                    case 0 -> {
                        return;
                    }

                    default -> System.out.println("Invalid option");
                }
            } catch (EntityNotFoundException | InvalidDataException | AppointmentNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // ===============================
    // BILLING MENU
    // ===============================

    private static void billingMenu() {

        while (true) {

            System.out.println("\n--- Billing ---");
            System.out.println("1 Generate Standard Bill");
            System.out.println("2 Generate Discount Bill");
            System.out.println("0 Back");
            System.out.println("===============================");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();
            try {
                switch (choice) {

                    case 1 -> {

                        String appointmentId = readStringInput("Enter Appointment ID: ");

                        try {

                            BillSummary bill = billingService.generateStandardBill(appointmentId);

                            System.out.println("Standard Bill Generated");
                            System.out.println(
                                    "BillID: " + bill.getBillId() +
                                            " | Amount: ₹" + bill.getTotal());

                        } catch (InvalidDataException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 2 -> {

                        String appointmentId = readStringInput("Enter Appointment ID: ");

                        try {

                            BillSummary bill = billingService.generateDiscountBill(appointmentId);

                            System.out.println("Discount Bill Generated");
                            System.out.println(
                                    "BillID: " + bill.getBillId() +
                                            " | Amount: ₹" + bill.getTotal());

                        } catch (InvalidDataException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 0 -> {
                        return;
                    }

                    default -> System.out.println("Invalid option");
                }
            } catch (EntityNotFoundException | InvalidDataException | AppointmentNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // ===============================
    // GLOBAL SEARCH MENU
    // ===============================

    private static void searchMenu() {

        while (true) {

            System.out.println("\n--- SEARCH MENU ---");
            System.out.println("1 Global Search");
            System.out.println("0 Back");
            System.out.println("===============================");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1 -> {

                    String query = readStringInput("Enter search query: ");

                    List<Searchable> results = searchService.globalSearch(query);

                    if (results.isEmpty()) {
                        System.out.println("No results found.");
                    }

                    results.forEach(System.out::println);
                }
                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid option");
            }
        }
    }

    // ===============================
    // ANALYTICS
    // ===============================

    private static void analyticsMenu() {

        System.out.println("\n--- Analytics ---");

        System.out.println(
                "Average Doctor Fee: " +
                        doctorService.averageDoctorFee());
        System.out.println("Appointments per Doctor: ");
        appointmentService.appointmentsPerDoctor();

    }

    // ===============================
    // AI RECOMMENDATION
    // ===============================

    private static void aiRecommendation() {

        String symptom = readStringInput("Enter symptom: ");

        System.out.println(
                "Recommended specialization: " +
                        AIHelper.recommend(symptom));
    }

    // ===============================
    // CSV MENU
    // ===============================

    private static void csvMenu() {

        while (true) {

            System.out.println("\n--- CSV Persistence ---");
            System.out.println("1 Save Data");
            System.out.println("2 Load Data");
            System.out.println("0 Back");
            System.out.println("===============================");
            System.out.println("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1 -> CSVUtil.saveData(doctorService, patientService, appointmentService);

                case 2 -> CSVUtil.loadData(doctorService, patientService, appointmentService);

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid option");
            }
        }
    }

    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        scanner.useDelimiter("\r?\n");
        String input = "";
        while (input.isEmpty() && scanner.hasNext()) {
            input = scanner.next().trim();
        }
        scanner.reset();
        return input;
    }
}