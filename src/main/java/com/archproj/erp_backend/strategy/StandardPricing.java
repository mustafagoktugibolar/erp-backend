package com.archproj.erp_backend.strategy;

import org.springframework.stereotype.Component;

@Component
public class StandardPricing implements PricingStrategy {
    @Override
    public double getPrice(double basePrice) {
        return basePrice;
    }

    @Override
    public double applyDiscount(double basePrice, double discountPercentage) {
        return basePrice; // No discount for standard pricing
    }

    @Override
    public double applyPremium(double basePrice, double premiumPercentage) {
        return basePrice; // No premium for standard pricing
    }
}
