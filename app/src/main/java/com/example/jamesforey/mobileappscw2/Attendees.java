package com.example.jamesforey.mobileappscw2;

public class Attendees {

    public static final String TABLE = "Attendees";

    // Labels Table Columns names
    public static final String KEY_ID = "a_id";
    public static final String KEY_name = "name";

    public int attendee_ID;
    public String attendeeName;

    public Attendees (){

    }

    public Attendees(int attendee_ID, String attendeeName) {
        this.attendee_ID = attendee_ID;
        this.attendeeName = attendeeName;
    }

    public Attendees(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public int getAttendee_ID() {
        return attendee_ID;
    }

    public void setAttendee_ID(int attendee_ID) {
        this.attendee_ID = attendee_ID;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }
}
