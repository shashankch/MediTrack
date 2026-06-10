package com.health.meditrack.service;

import com.health.meditrack.entity.Patient;
import com.health.meditrack.exception.EntityNotFoundException;
import com.health.meditrack.service.interfaces.PatientService;
import com.health.meditrack.util.DataStore;
import com.health.meditrack.util.IdGenerator;
import static com.health.meditrack.util.Validator.isNull;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final DataStore<Patient> store = new DataStore<>();
    private final IdGenerator idGenerator = IdGenerator.getInstance();

    // CREATE
    @Override
    public Patient addPatient(String name, int age) {

        String id = idGenerator.nextPatient();

        Patient patient = new Patient(id, name, age);

        return store.save(patient.getId(), patient);

    }

    // UPDATE
    @Override
    public Patient updatePatient(String id, String name, int age) {

        Patient patient = store.get(id);

        if (isNull(patient)) {
            throw new EntityNotFoundException("Patient not found");
        }

        patient.setName(name);
        patient.setAge(age);

        return store.update(id, patient);
    }

    // DELETE
    @Override
    public Patient deletePatient(String id) {
        return store.delete(id);
    }

    // READ
    @Override
    public void listPatients() {

        List<Patient> patients = store.all();

        if (patients.isEmpty()) {
            throw new EntityNotFoundException("No patients found.");
        }

        System.out.println("\n--- Patient List ---");

        patients.forEach(p -> {

            System.out.println(
                    "PatientID: " + p.getId()
                            + " | Name: " + p.getName()
                            + " | Age: " + p.getAge());

        });
    }

    // GET BY ID
    @Override
    public Patient getPatient(String id) {
        return store.get(id);
    }

    @Override
    public List<Patient> getAllPatients() {
        return store.all();
    }

}