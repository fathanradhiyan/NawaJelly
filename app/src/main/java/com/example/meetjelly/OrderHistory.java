package com.example.meetjelly;

public class OrderHistory {
    private String mDateOrder;
    private String mTotalPrice;
    private String mListOrderHistory;
    private int id;

    public OrderHistory(String mTotalPrice, String mDateOrder) {
        this.mDateOrder = mDateOrder;
        this.mTotalPrice = mTotalPrice;
        this.mListOrderHistory = mListOrderHistory;
        this.id = id;
    }

    public String getmDateOrder() {
        return mDateOrder;
    }

    public String getmTotalPrice() {
        return mTotalPrice;
    }

    public String getmListOrderHistory() {
        return mListOrderHistory;
    }

    public int getId() {
        return id;
    }
}
