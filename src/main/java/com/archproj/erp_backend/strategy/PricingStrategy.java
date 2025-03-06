package com.archproj.erp_backend.strategy;

public interface PricingStrategy {
    double getPrice(double basePrice);
    double applyDiscount(double basePrice, double discountPercentage);
    double applyPremium(double basePrice, double premiumPercentage);
}
