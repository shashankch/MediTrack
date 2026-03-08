package com.airtribe.meditrack.service.interfaces;

import com.airtribe.meditrack.entity.Patient;
import java.util.List;

public interface PatientService {

    Patient addPatient(String name, int age);

    Patient updatePatient(String id, String name, int age);

    Patient deletePatient(String id);

    Patient getPatient(String id);

    List<Patient> getAllPatients();

    void listPatients();

}