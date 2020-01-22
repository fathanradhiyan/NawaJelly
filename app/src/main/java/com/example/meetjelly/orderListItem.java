package com.example.meetjelly;

public class orderListItem {
    private String mCodeOrder;
    private String mNameOrder;
    private String mEmailOrder;

    public orderListItem(String mCodeOrder, String mNameOrder, String mEmailOrder) {
        this.mCodeOrder = mCodeOrder;
        this.mNameOrder = mNameOrder;
        this.mEmailOrder = mEmailOrder;
    }

    public String getmCodeOrder() {
        return mCodeOrder;
    }

    public String getmNameOrder() {
        return mNameOrder;
    }

    public String getmEmailOrder() {
        return mEmailOrder;
    }
}
