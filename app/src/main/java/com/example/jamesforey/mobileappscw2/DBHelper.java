package com.example.jamesforey.mobileappscw2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MeetingApp.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 10);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_TABLE_MEETING = "CREATE TABLE " + Meeting.TABLE  + "("
                + Meeting.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Meeting.KEY_title + " TEXT, "
                + Meeting.KEY_notes + " TEXT, "
                + Meeting.KEY_date + " TEXT, "
                + Meeting.KEY_time + " TEXT, "
                + Meeting.KEY_lat + " DOUBLE, "
                + Meeting.KEY_long + " DOUBLE) ";
//                + Meeting.KEY_attendeeID + " INTEGER) ";
//                + Meeting.KEY_attendees + " TEXT)";

        String CREATE_TABLE_ATTENDEE = "CREATE TABLE " + Attendees.TABLE  + "("
                + Attendees.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Attendees.KEY_name + " TEXT) ";

        String CREATE_TABLE_ATTENDEE_MEETING = "CREATE TABLE IF NOT EXISTS "
                + Meeting.TABLE + "_" + Attendees.TABLE + " ("
                + Attendees.KEY_ID + " INTEGER, "
                + Meeting.KEY_ID + " INTEGER,"
                + " FOREIGN KEY (" + Attendees.KEY_ID + ")"
                + " REFERENCES " + Attendees.TABLE + "(" + Attendees.KEY_ID + "),"
                + " FOREIGN KEY (" + Meeting.KEY_ID + ")"
                + " REFERENCES " + Meeting.TABLE + "(" + Meeting.KEY_ID + ") )";

        db.execSQL(CREATE_TABLE_MEETING);
        db.execSQL(CREATE_TABLE_ATTENDEE);
        db.execSQL(CREATE_TABLE_ATTENDEE_MEETING);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Meeting.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Attendees.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Meeting.TABLE + "_" + Attendees.TABLE);

        // Create tables again
        onCreate(db);
    }
}