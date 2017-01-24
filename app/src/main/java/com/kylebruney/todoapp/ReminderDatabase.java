package com.kylebruney.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


//Class to create the SQL Database
class ReminderDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ReminderDatabase";
    private static final String TABLE_REMINDERS = "ReminderTable";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_REPEAT_NO = "repeat_no";
    private static final String KEY_ACTIVE = "active";

    ReminderDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER,"
                + KEY_REPEAT + " BOOLEAN,"
                + KEY_REPEAT_NO + " INTEGER,"
                + KEY_ACTIVE + " BOOLEAN" + ")";
        db.execSQL(CREATE_REMINDERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        if (oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);

        // Create tables again
        onCreate(db);
    }

    // Adding new Reminder
    int addReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE , reminder.getTitle());
        values.put(KEY_DATE , reminder.getDate());
        values.put(KEY_TIME , reminder.getTime());
        values.put(KEY_REPEAT , reminder.getRepeat());
        values.put(KEY_ACTIVE, reminder.getActive());

        // Inserting Row
        long ID = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return (int) ID;
    }

    // Get single Reminder
    Reminder getReminder(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REMINDERS, new String[]
                        {
                                KEY_ID,
                                KEY_TITLE,
                                KEY_DATE,
                                KEY_TIME,
                                KEY_REPEAT,
                                KEY_ACTIVE
                        }, KEY_ID + "=?",

                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return new Reminder(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5));
    }

    // Get all Reminders
    List<Reminder> getAllReminders(){
        List<Reminder> reminderList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows
        if(cursor.moveToFirst()){
            do{
                Reminder reminder = new Reminder();
                reminder.setID(Integer.parseInt(cursor.getString(0)));
                reminder.setTitle(cursor.getString(1));
                reminder.setDate(cursor.getString(2));
                reminder.setTime(cursor.getString(3));
                reminder.setRepeat(cursor.getString(4));
                reminder.setActive(cursor.getString(5));

                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        return reminderList;
    }

    // Update single Reminder
    int updateReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE , reminder.getTitle());
        values.put(KEY_DATE , reminder.getDate());
        values.put(KEY_TIME , reminder.getTime());
        values.put(KEY_REPEAT , reminder.getRepeat());
        values.put(KEY_ACTIVE, reminder.getActive());

        return db.update(TABLE_REMINDERS, values, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getID())});
    }

    // Delete single Reminder
    void deleteReminder(Reminder reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, KEY_ID + "=?",
                new String[]{String.valueOf(reminder.getID())});
        db.close();
    }
}
