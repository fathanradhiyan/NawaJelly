package com.example.meetjelly;

public class OrderList {
    private String mDateOrder;
    private String mTime;
    private String mTransaction;
    private String mTotalPrice;

    public OrderList(String mDateOrder, String mTime, String mTransaction, String mTotalPrice) {
        this.mDateOrder = mDateOrder;
        this.mTime = mTime;
        this.mTransaction = mTransaction;
        this.mTotalPrice = mTotalPrice;
    }

    public String getmDateOrder() {
        return mDateOrder;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmTransaction() {
        return mTransaction;
    }

    public String getmTotalPrice() {
        return mTotalPrice;
    }
}
