package com.airtribe.meditrack.service.interfaces;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.enums.Specialization;
import java.util.List;

public interface DoctorService {

        Doctor addDoctor(String name, int age,
                        Specialization specialization,
                        double fee);

        Doctor updateDoctor(String id,
                        String name,
                        int age,
                        Specialization specialization,
                        double fee);

        Doctor deleteDoctor(String id);

        Doctor getDoctor(String id);

        List<Doctor> getAllDoctors();

        double averageDoctorFee();

        void listDoctors();

}
