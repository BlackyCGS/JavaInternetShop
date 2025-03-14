package com.myshop.internetshop.classes.enums;

public enum UserPermission {
    USER_PERMISSION_ADMIN(0),
    USER_PERMISSION_REGULAR(1),
    USER_PERMISSION_DELIVERY(2);

    private final int permission;

    UserPermission(int userPermission) {
        this.permission = userPermission;
    }

    public int getPermissionType() {
        return permission;
    }
}
