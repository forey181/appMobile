package com.example.jamesforey.mobileappscw2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendeesRepo {

    private DBHelper dbHelper;

    public AttendeesRepo(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(Attendees attendees) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Attendees.KEY_name, attendees.attendeeName);

        // Inserting Row
        long attendee_Id = db.insert(Attendees.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) attendee_Id;
    }

    public void delete(int attendee_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Attendees.TABLE, Attendees.KEY_ID + "= ?", new String[] { String.valueOf(attendee_Id) });
        db.close(); // Closing database connection
    }

    public void update(Attendees attendees) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Attendees.KEY_name, attendees.attendeeName);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Attendees.TABLE, values, Attendees.KEY_ID + "= ?", new String[] { String.valueOf(attendees.attendee_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<Attendees> getAttendeeList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Attendees.KEY_ID + "," +
                Attendees.KEY_name +
                " FROM " + Attendees.TABLE;


        ArrayList<Attendees> attendeeList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
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

    public Attendees getAttendeeById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Attendees.KEY_ID + "," +
                Attendees.KEY_name +
                " FROM " + Attendees.TABLE
                + " WHERE " +
                Attendees.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Attendees attendees = new Attendees();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                attendees.attendee_ID =cursor.getInt(cursor.getColumnIndex(Attendees.KEY_ID));
                attendees.attendeeName =cursor.getString(cursor.getColumnIndex(Attendees.KEY_name));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return attendees;
    }
}


