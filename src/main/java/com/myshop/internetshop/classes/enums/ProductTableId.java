package com.myshop.internetshop.classes.enums;

public enum ProductTableId {
        GPU(1);
    private final int tableId;

    ProductTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableId() {
        return tableId;
    }
}
