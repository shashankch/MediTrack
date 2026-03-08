
package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.service.interfaces.Payable;
import com.airtribe.meditrack.service.patterns.BillingStrategy;

public class Bill extends MedicalEntity implements Payable{

    private final double baseFee;
    private final BillingStrategy strategy;
    private boolean paid=false;

    public Bill(String id,double baseFee,BillingStrategy strategy){
        super(id);
        this.baseFee=baseFee;
        this.strategy=strategy;
    }

    public double total(){
        return baseFee - strategy.calculateDiscount(baseFee);
    }

    @Override
    public void pay(){ paid=true; }

    @Override
    public double getAmount(){ return total(); }

    @Override
    public boolean isPaidStatus(){ return paid; }

    @Override
    public String generateBill(){
        return "Bill "+id+" total:"+total();
    }

    @Override
    public String getSummary(){ return generateBill(); }
}
