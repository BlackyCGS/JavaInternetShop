package com.myshop.internetshop.classes.enums;

public enum UserPermission {
    /* Can do basically everything */
    USER_PERMISSION_ADMIN(1),
    /*Can add, delete and change products, change order statuses */
    USER_PERMISSION_MERCHANT(2),
    /*Can change order status*/
    USER_PERMISSION_DELIVERY(3),
    /*Just user*/
    USER_PERMISSION_REGULAR(4);

    private final int permission;

    UserPermission(int userPermission) {
        this.permission = userPermission;
    }

    public int getPermissionType() {
        return permission;
    }
}
