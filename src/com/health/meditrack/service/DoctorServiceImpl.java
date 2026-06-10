package com.health.meditrack.service;

import com.health.meditrack.entity.Doctor;
import com.health.meditrack.entity.enums.Specialization;
import com.health.meditrack.exception.EntityNotFoundException;
import com.health.meditrack.service.interfaces.DoctorService;
import com.health.meditrack.util.DataStore;
import com.health.meditrack.util.IdGenerator;
import static com.health.meditrack.util.Validator.isNull;
import java.util.List;

public class DoctorServiceImpl implements DoctorService {

    private final DataStore<Doctor> store = new DataStore<>();
    private final IdGenerator idGenerator = IdGenerator.getInstance();

    // CREATE
    @Override
    public Doctor addDoctor(String name, int age, Specialization specialization, double fee) {

        String id = idGenerator.nextDoctor();

        Doctor doctor = new Doctor(id, name, age, specialization, fee);

        store.save(doctor.getId(), doctor);

        return doctor;
    }

    // UPDATE
    @Override
    public Doctor updateDoctor(String id, String name, int age,
            Specialization specialization, double fee) {

        Doctor doctor = store.get(id);

        if (isNull(doctor)) {
            throw new EntityNotFoundException("Doctor not found");
        }

        doctor.setName(name);
        doctor.setAge(age);
        doctor.setSpecialization(specialization);
        doctor.setFee(fee);

        return store.update(id, doctor);
    }

    // DELETE
    @Override
    public Doctor deleteDoctor(String id) {
        return store.delete(id);
    }

    // READ
    @Override
    public void listDoctors() {

        List<Doctor> doctors = store.all();

        if (doctors.isEmpty()) {
            throw new EntityNotFoundException("No doctors found.");
        }

        System.out.println("\n--- Doctor List ---");

        doctors.forEach(d -> {

            System.out.println(
                    "DoctorID: " + d.getId()
                            + " | Name: " + d.getName()
                            + " | Age: " + d.getAge()
                            + " | Specialization: " + d.getSpecialization()
                            + " | Fee: ₹" + d.getFee());

        });
    }

    // GET BY ID
    @Override
    public Doctor getDoctor(String id) {
        return store.get(id);
    }

    // STREAM ANALYTICS
    @Override
    public double averageDoctorFee() {

        return store.all()
                .stream()
                .mapToDouble(Doctor::getFee)
                .average()
                .orElse(0);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return store.all();
    }
}