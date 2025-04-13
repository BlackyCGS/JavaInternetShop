package com.myshop.internetshop.classes.enums;

public enum UserPermission {
    /* Can do basically everything */
    ADMIN("ADMIN"),
    /*Can add, delete and change products, change order statuses */
    MERCHANT("MERCHANT"),
    /*Can change order status*/
    DELIVERY("DELIVERY"),
    /*Just user*/
    USER("USER");

    private final String permission;

    UserPermission(String userPermission) {
        this.permission = userPermission;
    }

    public String getPermissionType() {
        return permission;
    }
}
