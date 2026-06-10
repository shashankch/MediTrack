package com.health.meditrack.service;

import com.health.meditrack.service.interfaces.AppointmentService;
import com.health.meditrack.service.interfaces.DoctorService;
import com.health.meditrack.service.interfaces.PatientService;
import com.health.meditrack.service.interfaces.Searchable;
import static com.health.meditrack.util.Validator.isNumber;
import java.util.ArrayList;
import java.util.List;

public class SearchService {

        private final PatientService patientService;
        private final DoctorService doctorService;
        private final AppointmentService appointmentService;

        public SearchService(
                        PatientService p,
                        DoctorService d,
                        AppointmentService a) {

                this.patientService = p;
                this.doctorService = d;
                this.appointmentService = a;
        }

        public List<Searchable> globalSearch(String query) {

                List<Searchable> results = new ArrayList<>();

                if (isNumber(query)) {

                        var age = Integer.parseInt(query);

                        patientService.getAllPatients()
                                        .stream()
                                        .filter(p -> p.matches(age))
                                        .forEach(results::add);

                        doctorService.getAllDoctors()
                                        .stream()
                                        .filter(d -> d.matches(age))
                                        .forEach(results::add);
                }

                patientService.getAllPatients()
                                .stream()
                                .filter(p -> p.matches(query))
                                .forEach(results::add);

                doctorService.getAllDoctors()
                                .stream()
                                .filter(d -> d.matches(query))
                                .forEach(results::add);

                appointmentService.getAllAppointments()
                                .stream()
                                .filter(a -> a.matches(query))
                                .forEach(results::add);

                return results;
        }
}