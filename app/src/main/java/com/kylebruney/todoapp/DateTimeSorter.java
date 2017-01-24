package com.kylebruney.todoapp;

// Class to create DateTime objects for easy sorting
class DateTimeSorter {
    private int mIndex;
    private String mDateTime;


    DateTimeSorter(int index, String DateTime){
        mIndex = index;
        mDateTime = DateTime;
    }

    int getIndex() {
        return mIndex;
    }


    String getDateTime() {
        return mDateTime;
    }

}
