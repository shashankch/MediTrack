package com.health.meditrack.service;

import com.health.meditrack.entity.*;
import com.health.meditrack.exception.AppointmentNotFoundException;
import com.health.meditrack.service.interfaces.AppointmentService;
import com.health.meditrack.service.interfaces.BillingService;
import com.health.meditrack.service.interfaces.DoctorService;
import com.health.meditrack.service.patterns.BillFactory;
import com.health.meditrack.util.IdGenerator;
import static com.health.meditrack.util.Validator.isNull;

public class BillingServiceImpl implements BillingService {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final IdGenerator idGen = IdGenerator.getInstance();

    public BillingServiceImpl(AppointmentService appointmentService,
            DoctorService doctorService) {

        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    // STANDARD BILL
    @Override
    public BillSummary generateStandardBill(String appointmentId) {

        Appointment appointment = appointmentService.getAppointment(appointmentId);

        if (isNull(appointment)) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

        Doctor doctor = doctorService.getDoctor(appointment.getDoctorId());

        double fee = doctor.getFee();

        String billId = idGen.nextBill();

        Bill bill = BillFactory.createStandard(billId, fee);

        // simulate payment
        bill.pay();

        return new BillSummary(bill.getId(), bill.getAmount());
    }

    // DISCOUNT BILL
    @Override
    public BillSummary generateDiscountBill(String appointmentId) {

        Appointment appointment = appointmentService.getAppointment(appointmentId);

        if (isNull(appointment)) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

        Doctor doctor = doctorService.getDoctor(appointment.getDoctorId());

        double fee = doctor.getFee();

        String billId = idGen.nextBill();

        Bill bill = BillFactory.createSenior(billId, fee);

        // simulate payment
        bill.pay();

        return new BillSummary(bill.getId(), bill.getAmount());
    }
}