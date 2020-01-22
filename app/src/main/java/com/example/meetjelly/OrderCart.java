package com.example.meetjelly;

public class OrderCart {
    private int mProductId;
    private int mQuantity;

    public OrderCart(int mProductId, int mQuantity) {
        this.mProductId = mProductId;
        this.mQuantity = mQuantity;
    }

    public int getmProductId() {
        return mProductId;
    }

    public int getmQuantity() {
        return mQuantity;
    }
}
