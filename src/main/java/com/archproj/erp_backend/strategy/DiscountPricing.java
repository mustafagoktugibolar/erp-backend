package com.archproj.erp_backend.strategy;

import org.springframework.stereotype.Component;

@Component
public class DiscountPricing implements PricingStrategy {
    @Override
    public double getPrice(double basePrice) {
        return basePrice;
    }

    @Override
    public double applyDiscount(double basePrice, double discountPercentage) {
        return basePrice * ((100 - discountPercentage) / 100); // Apply discount
    }

    @Override
    public double applyPremium(double basePrice, double premiumPercentage) {
        return basePrice; // No premium for discount pricing
    }
}
