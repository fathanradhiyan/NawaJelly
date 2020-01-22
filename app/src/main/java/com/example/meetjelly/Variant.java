package com.example.meetjelly;

public class Variant {
    private String mImageVariant;
    private String mVariant;
    private int mVariantPrice;
    //private int mVariantQuantity;

    public Variant(String mImageVariant, String mVariant, int mVariantPrice) {
        this.mImageVariant = mImageVariant;
        this.mVariant = mVariant;
        this.mVariantPrice = mVariantPrice;
        //this.mVariantQuantity = mVariantQuantity;
    }

    public String getmImageVariant() {
        return mImageVariant;
    }

    public String getmVariant() {
        return mVariant;
    }

    public int getmVariantPrice() {
        return mVariantPrice;
    }

    /*public int getmVariantQuantity() {
        return mVariantQuantity;
    }*/
}
