
package com.airtribe.meditrack.entity;

public final class BillSummary{

    private final String billId;
    private final double total;

    public BillSummary(String id,double t){
        this.billId=id;
        this.total=t;
    }

    public String getBillId(){return billId;}
    public double getTotal(){return total;}
}
