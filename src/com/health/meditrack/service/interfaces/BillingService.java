package com.health.meditrack.service.interfaces;

import com.health.meditrack.entity.BillSummary;

public interface BillingService {

    BillSummary generateStandardBill(String appointmentId);

    BillSummary generateDiscountBill(String appointmentId);

}
