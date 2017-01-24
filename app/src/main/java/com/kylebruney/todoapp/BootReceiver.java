package com.kylebruney.todoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.List;


//Class to create reminder notification following reboot
public class BootReceiver extends BroadcastReceiver {

    long mRepeatTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            ReminderDatabase rd = new ReminderDatabase(context);
            Calendar mCalendar = Calendar.getInstance();
            AlarmReceiver mAlarmReceiver = new AlarmReceiver();

            List<Reminder> reminders = rd.getAllReminders();

            for (Reminder rm : reminders) {
                int mReceivedID = rm.getID();
                String mRepeat = rm.getRepeat();
                String mActive = rm.getActive();
                String mDate = rm.getDate();
                String mTime = rm.getTime();

                String[] mDateSplit = mDate.split("/");
                String[] mTimeSplit = mTime.split(":");

                int mDay = Integer.parseInt(mDateSplit[0]);
                int mMonth = Integer.parseInt(mDateSplit[1]);
                int mYear = Integer.parseInt(mDateSplit[2]);
                int mHour = Integer.parseInt(mTimeSplit[0]);
                int mMinute = Integer.parseInt(mTimeSplit[1]);

                mCalendar.set(Calendar.MONTH, mMonth);
                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                mCalendar.set(Calendar.SECOND, 0);

                // Create a new notification
                if (mActive.equals("true")) {
                    if (mRepeat.equals("true")) {
                        mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);
                    } else if (mRepeat.equals("false")) {
                        mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                    }
                }
            }
        }
    }
}