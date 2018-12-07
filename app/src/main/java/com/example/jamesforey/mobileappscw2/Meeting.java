package com.example.jamesforey.mobileappscw2;

public class Meeting {

    public static final String TABLE = "Meeting";

    // Labels Table Columns names
    public static final String KEY_ID = "m_id";
    public static final String KEY_title = "title";
    public static final String KEY_notes = "notes";
    public static final String KEY_date = "date";
    public static final String KEY_time = "time";
    public static final String KEY_lat = "latitude";
    public static final String KEY_long = "longitude";
//    public static final String KEY_attendeeID = "attendee_id";
//    public static final String KEY_attendees = "attendees";

    // property help us to keep data
    public int meeting_ID;
    public String title;
    public String notes;
    public String date;
    public String time;
    public double latitude;
    public double longitude;

    public Meeting() {
    }

    public Meeting(String title, String notes, String date, String time, double latitude, double longitude) {
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Meeting(int meeting_ID, String title, String notes, String date, String time, double latitude, double longitude) {
        this.meeting_ID = meeting_ID;
        this.title = title;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getMeeting_ID() {
        return meeting_ID;
    }

    public void setMeeting_ID(int meeting_ID) {
        this.meeting_ID = meeting_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //    public Attendees attendee;

//    public String attendee;

//    public String conAttendee(){
//        String con = "";
//
//        for (String attendee : )
//    }
}
