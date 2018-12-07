package com.example.jamesforey.mobileappscw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendeesMeetingRepo {


    private DBHelper dbHelper;

    public AttendeesMeetingRepo(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(int meeting_ID, int attendee_ID) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Attendees.KEY_ID, attendee_ID);
        values.put(Meeting.KEY_ID, meeting_ID);

        // Inserting Row
        long attendee_meeting_Id = db.insert(Meeting.TABLE + "_" + Attendees.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) attendee_meeting_Id;
    }

    public void delete(int meeting_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Meeting.TABLE + "_" + Attendees.TABLE, Meeting.KEY_ID + "= ?", new String[] { String.valueOf(meeting_Id) });
        db.close(); // Closing database connection
    }

    public ArrayList<Attendees> getAttendeeListByMeetingId(int id) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " + "a." + Attendees.KEY_name + ",a." + Attendees.KEY_ID + " FROM " + Attendees.TABLE +
                " AS a, " + Meeting.TABLE + "_" + Attendees.TABLE + " AS ma " + "WHERE "
                + "ma." + Attendees.KEY_ID + " = a." + Attendees.KEY_ID + " AND " + Meeting.KEY_ID + "=?";

        ArrayList<Attendees> attendeeList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Attendees attendees = new Attendees();
                attendees.setAttendee_ID(cursor.getInt(cursor.getColumnIndex(Attendees.KEY_ID)));
                attendees.setAttendeeName(cursor.getString(cursor.getColumnIndex(Attendees.KEY_name)));

                attendeeList.add(attendees);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return attendeeList;

    }
}
