package com.example.meetjelly;

public class paymentList {
    private String mCode;
    private String mName;
    private String mEmail;

    public paymentList(String mCode, String mName, String mEmail) {
        this.mCode = mCode;
        this.mName = mName;
        this.mEmail = mEmail;
    }

    public String getmCode() {
        return mCode;
    }

    public String getmName() {
        return mName;
    }

    public String getmEmail() {
        return mEmail;
    }
}
