package com.example.jamesforey.mobileappscw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MeetingRepo {

    private DBHelper dbHelper;

    public MeetingRepo(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(Meeting meeting) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Meeting.KEY_title, meeting.title);
        values.put(Meeting.KEY_notes, meeting.notes);
        values.put(Meeting.KEY_date, meeting.date);
        values.put(Meeting.KEY_time, meeting.time);
        values.put(Meeting.KEY_lat, meeting.latitude);
        values.put(Meeting.KEY_long, meeting.longitude);
//        values.put(Meeting.KEY_attendees, meeting.attendee);

        // Inserting Row
        long meeting_Id = db.insert(Meeting.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) meeting_Id;
    }

    public void delete(int meeting_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Meeting.TABLE, Meeting.KEY_ID + "= ?", new String[] { String.valueOf(meeting_Id) });
        db.close(); // Closing database connection
    }

    public void update(Meeting meeting) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Meeting.KEY_title, meeting.title);
        values.put(Meeting.KEY_notes, meeting.notes);
        values.put(Meeting.KEY_date, meeting.date);
        values.put(Meeting.KEY_time, meeting.time);
        values.put(Meeting.KEY_lat, meeting.latitude);
        values.put(Meeting.KEY_long, meeting.longitude);
//        values.put(Meeting.KEY_attendees, meeting.attendee);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Meeting.TABLE, values, Meeting.KEY_ID + "= ?", new String[] { String.valueOf(meeting.meeting_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getMeetingList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Meeting.KEY_ID + "," +
                Meeting.KEY_title + "," +
                Meeting.KEY_notes + "," +
                Meeting.KEY_date + "," +
                Meeting.KEY_time + "," +
                Meeting.KEY_lat + "," +
                Meeting.KEY_long +
//                Meeting.KEY_attendees +
                " FROM " + Meeting.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> meetingList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> meeting = new HashMap<>();
                meeting.put("id", cursor.getString(cursor.getColumnIndex(Meeting.KEY_ID)));
                meeting.put("title", cursor.getString(cursor.getColumnIndex(Meeting.KEY_title)));
                meeting.put("notes", cursor.getString(cursor.getColumnIndex(Meeting.KEY_notes)));
                meeting.put("date", cursor.getString(cursor.getColumnIndex(Meeting.KEY_date)));
                meeting.put("time", cursor.getString(cursor.getColumnIndex(Meeting.KEY_time)));
                meeting.put("latitude", cursor.getString(cursor.getColumnIndex(Meeting.KEY_lat)));
                meeting.put("longitude", cursor.getString(cursor.getColumnIndex(Meeting.KEY_long)));
//                meeting.put("attendees", cursor.getString(cursor.getColumnIndex(Meeting.KEY_attendees)));
                meetingList.add(meeting);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return meetingList;

    }

    public Meeting getMeetingById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Meeting.KEY_ID + "," +
                Meeting.KEY_title + "," +
                Meeting.KEY_notes +"," +
                Meeting.KEY_date + "," +
                Meeting.KEY_time + "," +
                Meeting.KEY_lat + "," +
                Meeting.KEY_long +
//                Meeting.KEY_attendees +
                " FROM " + Meeting.TABLE
                + " WHERE " +
                Meeting.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Meeting meeting = new Meeting();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                meeting.meeting_ID =cursor.getInt(cursor.getColumnIndex(Meeting.KEY_ID));
                meeting.title =cursor.getString(cursor.getColumnIndex(Meeting.KEY_title));
                meeting.notes =cursor.getString(cursor.getColumnIndex(Meeting.KEY_notes));
                meeting.date =cursor.getString(cursor.getColumnIndex(Meeting.KEY_date));
                meeting.time =cursor.getString(cursor.getColumnIndex(Meeting.KEY_time));
                meeting.latitude =cursor.getDouble(cursor.getColumnIndex(Meeting.KEY_lat));
                meeting.longitude =cursor.getDouble(cursor.getColumnIndex(Meeting.KEY_long));
//                meeting.attendee =cursor.getString(cursor.getColumnIndex(Meeting.KEY_attendees));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return meeting;
    }
}
