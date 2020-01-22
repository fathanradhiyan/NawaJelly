package com.example.meetjelly;

public class CardCashier {
    private String mEmail;
    private String mCode;
    

    public CardCashier(String email, String code) {
        this.mEmail = email;
        this.mCode = code;
    }

    public CardCashier(String email) {
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmCode() {
        return mCode;
    }
}
