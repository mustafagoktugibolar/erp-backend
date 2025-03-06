package com.archproj.erp_backend.strategy;

import org.springframework.stereotype.Component;

@Component
public class PremiumPricing implements PricingStrategy {
    @Override
    public double getPrice(double basePrice) {
        return basePrice;
    }

    @Override
    public double applyDiscount(double basePrice, double discountPercentage) {
        return basePrice; // No discount for premium pricing
    }

    @Override
    public double applyPremium(double basePrice, double premiumPercentage) {
        return basePrice * ((100 + premiumPercentage) / 100); // Apply price increase
    }
}
