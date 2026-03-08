
package com.airtribe.meditrack.service.patterns;

import com.airtribe.meditrack.entity.Bill;

public class BillFactory {

    public static Bill createStandard(String id, double fee) {
        return new Bill(id, fee, new StandardBillingStrategy());
    }

    public static Bill createSenior(String id, double fee) {
        return new Bill(id, fee, new SeniorBillingStrategy());
    }

}
