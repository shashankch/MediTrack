package com.airtribe.meditrack.test;

/*
 * Manual test runner for MediTrack project.
 *
 * This class exercises the core services without using any testing framework.
 * Run the main method and inspect console output to verify correct behavior.
 * The tests cover:
 *   - patient CRUD and listing
 *   - doctor CRUD, listing and fee analytics
 *   - appointment creation, cancellation, listing, and analytics
 *   - billing generation (standard and discount)
 *   - search functionality
 *   - observer notification when appointments are created/cancelled
 *
 * No assertions are enforced; failures will normally manifest as exceptions
 * or missing expected output.  Use this file as a starting point for manual
 * exploratory testing and debugging during development.
 */

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.enums.Specialization;
import com.airtribe.meditrack.service.AppointmentServiceImpl;
import com.airtribe.meditrack.service.BillingServiceImpl;
import com.airtribe.meditrack.service.DoctorServiceImpl;
import com.airtribe.meditrack.service.NotificationServiceImpl;
import com.airtribe.meditrack.service.PatientServiceImpl;
import com.airtribe.meditrack.service.SearchService;
import com.airtribe.meditrack.service.interfaces.AppointmentService;
import com.airtribe.meditrack.service.interfaces.BillingService;
import com.airtribe.meditrack.service.interfaces.DoctorService;
import com.airtribe.meditrack.service.interfaces.PatientService;
import com.airtribe.meditrack.service.interfaces.Searchable;
import com.airtribe.meditrack.service.interfaces.Subject;
import com.airtribe.meditrack.service.patterns.AppointmentObserver;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) throws Exception {
        System.out.println("--- Starting manual test suite ---\n");

        PatientService patientService = new PatientServiceImpl();
        DoctorService doctorService = new DoctorServiceImpl();
        Subject notificationService = new NotificationServiceImpl();
        AppointmentService appointmentService = new AppointmentServiceImpl(doctorService, patientService,
                notificationService);
        BillingService billingService = new BillingServiceImpl(appointmentService, doctorService);
        SearchService searchService = new SearchService(patientService, doctorService, appointmentService);

        /*
         * Note: If you want to test CSV data loading, uncomment the block below and
         * ensure you have the appropriate CSV files in place. This will load initial
         * data into the services before running the tests. Also adjust test methods by
         * commenting out manual creation of patients/doctors if you want to rely solely
         * on CSV data.
         *
         */
        // if (CSVUtil.isDataLoadingRequired(args)) {
        // System.out.println("Loading CSV data...");
        // CSVUtil.loadData(doctorService, patientService, appointmentService);
        // }

        // observer tests
        notificationService.addObserver(new AppointmentObserver());

        runPatientTests(patientService);
        runDoctorTests(doctorService);
        runAppointmentTests(patientService, doctorService, appointmentService);
        runBillingTests(appointmentService, doctorService, billingService);
        runSearchTests(searchService);

        System.out.println("\n--- Manual tests finished ---");
        System.out.println("Observe the console output above for PASS/FAIL messages and state changes.");
    }

    private static void runPatientTests(PatientService patientService) {
        System.out.println("[Patient Tests]");
        Patient p1 = patientService.addPatient("Alice", 30);
        Patient p2 = patientService.addPatient("Bob", 45);
        System.out.println("  created patients: " + p1.getId() + ", " + p2.getId());
        patientService.listPatients();
        Patient updated = patientService.updatePatient(p1.getId(), "Alicia", 31);
        System.out.println("  updated patient name -> " + updated.getName());
        patientService.listPatients();
        Patient deleted = patientService.deletePatient(p2.getId());
        System.out.println("  deleted patient " + deleted.getId());
        patientService.listPatients();
        System.out.println("[Patient Tests] complete\n");
    }

    private static void runDoctorTests(DoctorService doctorService) {
        System.out.println("[Doctor Tests]");
        Doctor d1 = doctorService.addDoctor("Dr. Smith", 50, Specialization.CARDIOLOGY, 1200);
        Doctor d2 = doctorService.addDoctor("Dr. Jones", 40, Specialization.DERMATOLOGY, 800);
        System.out.println("  created doctors: " + d1.getId() + ", " + d2.getId());
        doctorService.listDoctors();
        Doctor updated = doctorService.updateDoctor(d1.getId(), "Dr. John Smith", 51,
                Specialization.CARDIOLOGY, 1300);
        System.out.println("  updated doctor name -> " + updated.getName());
        doctorService.listDoctors();
        double avgFee = doctorService.averageDoctorFee();
        System.out.println("  average fee: " + avgFee);
        Doctor deleted = doctorService.deleteDoctor(d2.getId());
        System.out.println("  deleted doctor " + deleted.getId());
        doctorService.listDoctors();
        System.out.println("[Doctor Tests] complete\n");
    }

    private static void runAppointmentTests(PatientService patientService, DoctorService doctorService,
            AppointmentService appointmentService) {
        System.out.println("[Appointment Tests]");
        // assume at least one patient and doctor exist
        Patient patient = patientService.getAllPatients().get(0);
        Doctor doctor = doctorService.getAllDoctors().get(0);

        Appointment a1 = appointmentService.createAppointment(patient.getId(), doctor.getId());
        System.out.println("  created appointment " + a1.getId());
        appointmentService.listAppointments();
        boolean cancelled = appointmentService.cancelAppointment(a1.getId());
        System.out.println("  cancelled appointment returned " + cancelled);
        appointmentService.listAppointments();

        // analytics
        System.out.println("  appointments per doctor: " + appointmentService.appointmentsPerDoctor());
        System.out.println("[Appointment Tests] complete\n");
    }

    private static void runBillingTests(AppointmentService appointmentService,
            DoctorService doctorService,
            BillingService billingService) {
        System.out.println("[Billing Tests]");
        // pick first appointment's patient/doctor for billing
        if (!doctorService.getAllDoctors().isEmpty() && !appointmentService.getAllAppointments().isEmpty()) {
            Appointment first = appointmentService.getAllAppointments().get(0);
            String pid = first.getPatientId();
            String did = first.getDoctorId();
            Appointment billAppt = appointmentService.createAppointment(pid, did);
            System.out.println("  appointment for billing: " + billAppt.getId());

            BillSummary std = billingService.generateStandardBill(billAppt.getId());
            System.out.println("  standard bill generated: " + std.getTotal());
            BillSummary disc = billingService.generateDiscountBill(billAppt.getId());
            System.out.println("  discount bill generated: " + disc.getTotal());
        } else {
            System.out.println("  skipping billing tests - preconditions not met");
        }
        System.out.println("[Billing Tests] complete\n");
    }

    private static void runSearchTests(SearchService searchService) {
        System.out.println("[Search Tests]");
        List<Searchable> results = searchService.globalSearch("Alice");
        System.out.println("  search for 'Alice' returned " + results.size() + " items");
        results = searchService.globalSearch("50");
        System.out.println("  search for '50' returned " + results.size() + " items");
        System.out.println("[Search Tests] complete\n");
    }
}
// manual tests