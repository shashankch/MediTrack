
package com.health.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private static final IdGenerator INSTANCE = new IdGenerator();

    private final AtomicInteger patient = new AtomicInteger(100);
    private final AtomicInteger doctor = new AtomicInteger(200);
    private final AtomicInteger appt = new AtomicInteger(300);
    private final AtomicInteger bill = new AtomicInteger(400);

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    public String nextPatient() {
        return "PAT" + patient.incrementAndGet();
    }

    public String nextDoctor() {
        return "DOC" + doctor.incrementAndGet();
    }

    public String nextAppointment() {
        return "APT" + appt.incrementAndGet();
    }

    public String nextBill() {
        return "BL" + bill.incrementAndGet();
    }
}
