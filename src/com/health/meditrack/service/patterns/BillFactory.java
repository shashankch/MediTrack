
package com.health.meditrack.service.patterns;

import com.health.meditrack.entity.Bill;

public class BillFactory {

    public static Bill createStandard(String id, double fee) {
        return new Bill(id, fee, new StandardBillingStrategy());
    }

    public static Bill createSenior(String id, double fee) {
        return new Bill(id, fee, new SeniorBillingStrategy());
    }

}
