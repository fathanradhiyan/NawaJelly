package com.example.meetjelly;

public class Notification {
    //private int mIdNotification;
    //private String mTitle;
    //private String mDate;
    private String mSubject;
    private String mMessage;

    public Notification(String mSubject, String mMessage) {
//        this.mIdNotification = mIdNotification;
//        this.mTitle = mTitle;
//        this.mDate = mDate;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
    }

//    public int getmIdNotification() {
//        return mIdNotification;
//    }
//
//    public String getmTitle() {
//        return mTitle;
//    }
//
//    public String getmDate() {
//        return mDate;
//    }

    public String getmSubject() {
        return mSubject;
    }

    public String getmMessage() {
        return mMessage;
    }
}
