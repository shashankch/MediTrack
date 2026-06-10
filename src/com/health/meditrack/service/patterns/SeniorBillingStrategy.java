
package com.health.meditrack.service.patterns;
public class SeniorBillingStrategy implements BillingStrategy{
    @Override
    public double calculateDiscount(double a){return a*0.2;}
}
