
package com.airtribe.meditrack.service.interfaces;

public interface Payable {
    void pay();

    double getAmount();

    boolean isPaidStatus();

    String generateBill();
}
