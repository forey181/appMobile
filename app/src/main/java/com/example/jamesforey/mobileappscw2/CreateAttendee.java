package com.example.jamesforey.mobileappscw2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAttendee extends AppCompatActivity implements android.view.View.OnClickListener {

    EditText editTextAttendee;
    Button saveBtn, deleteBtn;

    private int _Attendee_Id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_attendee);

        editTextAttendee = findViewById(R.id.attendeeItem);

        saveBtn = findViewById(R.id.save_attendee);
        saveBtn.setOnClickListener(this);

        deleteBtn = findViewById(R.id.deleteAttendee);
        deleteBtn.setOnClickListener(this);

        _Attendee_Id = 0;
        Intent intent = getIntent();
        _Attendee_Id = intent.getIntExtra("attendee_Id", 0);
        AttendeesRepo repo = new AttendeesRepo(this);
        Attendees attendees = new Attendees();
        attendees = repo.getAttendeeById(_Attendee_Id);

        editTextAttendee.setText(attendees.attendeeName);
    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.save_attendee)) {
            AttendeesRepo repo = new AttendeesRepo(this);
            Attendees attendees = new Attendees();
            attendees.attendeeName = editTextAttendee.getText().toString();
            attendees.attendee_ID = _Attendee_Id;

            if (_Attendee_Id == 0) {
                _Attendee_Id = repo.insert(attendees);

                Toast.makeText(this, getString(R.string.new_attendees_toast), Toast.LENGTH_SHORT).show();
            } else {

                repo.update(attendees);
                Toast.makeText(this, getString(R.string.attendees_updated_toast), Toast.LENGTH_SHORT).show();
            }

            finish();
        }

        if (v == findViewById(R.id.deleteAttendee)) {
            AttendeesRepo repo = new AttendeesRepo(this);
            repo.delete(_Attendee_Id);
            Toast.makeText(this, getString(R.string.Attendee_deleted_toast), Toast.LENGTH_SHORT);
            finish();
        }
    }
}
