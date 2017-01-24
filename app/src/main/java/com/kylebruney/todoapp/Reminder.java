package com.kylebruney.todoapp;

// Reminder class
class Reminder {
    private int mID;
    private String mTitle;
    private String mDate;
    private String mTime;
    private String mRepeat;
    private String mActive;


    Reminder(int ID, String Title, String Date, String Time, String Repeat, String Active){
        mID = ID;
        mTitle = Title;
        mDate = Date;
        mTime = Time;
        mRepeat = Repeat;
        mActive = Active;
    }

    Reminder(String Title, String Date, String Time, String Repeat, String Active){
        mTitle = Title;
        mDate = Date;
        mTime = Time;
        mRepeat = Repeat;
        mActive = Active;
    }

    Reminder(){}

    int getID() {
        return mID;
    }

    void setID(int ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    String getDate() {
        return mDate;
    }

    void setDate(String date) {
        mDate = date;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) { mTime = time; }

    String getRepeat() {
        return mRepeat;
    }

    void setRepeat(String repeat) {
        mRepeat = repeat;
    }

    String getActive() {
        return mActive;
    }

    void setActive(String active) {
        mActive = active;
    }
}
