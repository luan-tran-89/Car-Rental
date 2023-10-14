package com.edu.miu.enums;

/**
 * @author gasieugru
 */
public enum FrequentRenterType {
    NONE(0.0), // No discount for NONE type
    BRONZE(0.05), // 5% off
    SILVER(0.10), // 10% off
    GOLD(0.15);   // 15% off

    private final double discount;

    FrequentRenterType(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
