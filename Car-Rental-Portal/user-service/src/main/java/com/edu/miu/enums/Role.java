package com.edu.miu.enums;

/**
 * @author gasieugru
 */
public enum Role {
    ADMIN, MANAGER, CUSTOMER, FREQUENT_RENTER;

    public static boolean isAdminOrManager(Role role) {
        return role == ADMIN || role == MANAGER;
    }

    public static boolean isAdmin(Role role) {
        return role == ADMIN;
    }

    public static boolean isManager(Role role) {
        return role == MANAGER;
    }

    public static boolean isCustomer(Role role) {
        return role == CUSTOMER || role == FREQUENT_RENTER;
    }

}
