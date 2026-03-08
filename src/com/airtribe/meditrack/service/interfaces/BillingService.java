package com.airtribe.meditrack.service.interfaces;

import com.airtribe.meditrack.entity.BillSummary;

public interface BillingService {

    BillSummary generateStandardBill(String appointmentId);

    BillSummary generateDiscountBill(String appointmentId);

}
