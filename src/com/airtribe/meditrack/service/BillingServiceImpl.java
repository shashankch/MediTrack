package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.service.interfaces.AppointmentService;
import com.airtribe.meditrack.service.interfaces.BillingService;
import com.airtribe.meditrack.service.interfaces.DoctorService;
import com.airtribe.meditrack.service.patterns.BillFactory;
import com.airtribe.meditrack.util.IdGenerator;
import static com.airtribe.meditrack.util.Validator.isNull;

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