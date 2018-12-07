package com.example.jamesforey.mobileappscw2;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CreateMeeting extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnSave, btnDate, btnTime, btnDelete, btnLocation, btnAddAttendees, btnNotify, btnNotifySettings;
    EditText editTextTitle, editTextNotes, editTextDate, editTextTime, editTextLat, editTextLong, editTextAttendees;
    ListView listAttendees;
    String[] ListElements = new String[]{};

    ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();

    ArrayList<Attendees> getAttendees = new ArrayList<>();

    private AttendeesMeetingRepo repo2;


    private NotificationHelper notificationHelper;
    private static final int notification_one = 101;
    private static final String TAG = CreateMeeting.class.getSimpleName();


    private int mYear, mMonth, mDay, mHour, mMinute;
    private int _Meeting_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
//        notificationHelper = new NotificationHelper(this);

        btnSave = findViewById(R.id.save_meeting);
        btnDate = findViewById(R.id.btn_date);
        btnTime = findViewById(R.id.btn_time);
        btnDelete = findViewById(R.id.delete_btn);
        btnLocation = findViewById(R.id.btnSelectLocation);
        btnAddAttendees = findViewById(R.id.btnAdd);
//        btnNotify = findViewById(R.id.notify_btn);
//        btnNotifySettings = findViewById(R.id.notification_settings);

        editTextTitle = findViewById(R.id.title_edit);
        editTextNotes = findViewById(R.id.edit_notes);
        editTextDate = findViewById(R.id.edit_date);
        editTextTime = findViewById(R.id.edit_time);
        editTextLat = findViewById(R.id.edit_lat);
        editTextLong = findViewById(R.id.edit_long);
        editTextAttendees = findViewById(R.id.txtItem);


//        listAttendees = findViewById(R.id.attendeesList);
//
//        final List<String> ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
//
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateMeeting.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
//
//        listAttendees.setAdapter(adapter);

        btnSave.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnAddAttendees.setOnClickListener(this);
//        btnAddAttendees.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ListElementsArrayList.add(editTextAttendees.getText().toString());
//                adapter.notifyDataSetChanged();
//                editTextAttendees.getText().clear();
//            }
//        });

//        btnNotifySettings.setOnClickListener(this);
//        btnNotify.setOnClickListener(this);

        _Meeting_Id = 0;
        Intent intent = getIntent();
        _Meeting_Id = intent.getIntExtra("meeting_Id", 0);

        repo2 = new AttendeesMeetingRepo(this);
        MeetingRepo repo = new MeetingRepo(this);
        Meeting meeting = new Meeting();
        meeting = repo.getMeetingById(_Meeting_Id);



        editTextTitle.setText(meeting.title);
        editTextNotes.setText(meeting.notes);
        editTextDate.setText(meeting.date);
        editTextTime.setText(meeting.time);
        editTextLat.setText(Double.toString(meeting.latitude));
        editTextLong.setText(Double.toString(meeting.longitude));


        getAttendees.addAll(repo2.getAttendeeListByMeetingId(_Meeting_Id));
        Log.d("test", getAttendees.size()+"num");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getAttendees.size(); i++) {
            stringBuilder.append(getAttendees.get(i).getAttendeeName() + "\n");
        }
        editTextAttendees.setText(stringBuilder);

        editTextTitle.setTextColor(SettingsActivity.getColor(this));
        editTextTitle.setTextSize(SettingsActivity.getSize(this));

        editTextNotes.setTextColor(SettingsActivity.getColor(this));
        editTextNotes.setTextSize(SettingsActivity.getSize(this));

        editTextDate.setTextColor(SettingsActivity.getColor(this));
        editTextDate.setTextSize(SettingsActivity.getSize(this));

        editTextTime.setTextColor(SettingsActivity.getColor(this));
        editTextTime.setTextSize(SettingsActivity.getSize(this));

        editTextLat.setTextColor(SettingsActivity.getColor(this));
        editTextLat.setTextSize(SettingsActivity.getSize(this));

        editTextLong.setTextColor(SettingsActivity.getColor(this));
        editTextLong.setTextSize(SettingsActivity.getSize(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        System.out.println(repo.getMeetingList());

    }

    @Override
    public void onClick(View v) {

        if (v == btnDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            editTextTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == findViewById(R.id.save_meeting)) {
            MeetingRepo repo = new MeetingRepo(this);
            Meeting meeting = new Meeting();
            meeting.title = editTextTitle.getText().toString();
            meeting.notes = editTextNotes.getText().toString();
            meeting.date = editTextDate.getText().toString();
            meeting.time = editTextTime.getText().toString();
            meeting.latitude = Double.parseDouble(editTextLat.getText().toString());
            meeting.longitude = Double.parseDouble(editTextLong.getText().toString());
            meeting.meeting_ID = _Meeting_Id;

            if (_Meeting_Id == 0) {
                _Meeting_Id = repo.insert(meeting);
                for (int i = 0; i < idList.size(); i++) {

                    repo2.insert(_Meeting_Id,idList.get(i));
                }


                Toast.makeText(this, "New meeting Insert", Toast.LENGTH_SHORT).show();
            } else {

                repo.update(meeting);
                Toast.makeText(this, "Student meeting updated", Toast.LENGTH_SHORT).show();
            }
            finish();

        } else if (v == findViewById(R.id.delete_btn)) {
            MeetingRepo repo = new MeetingRepo(this);
            repo.delete(_Meeting_Id);
            Toast.makeText(this, "Meeting Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }

        if (v == findViewById(R.id.btnSelectLocation)) {
            Intent intent2 = new Intent(this, MapsActivity.class);
            String lon = editTextLong.getText().toString();
            String lat = editTextLat.getText().toString();
            if (lon.length() > 0 && lat.length() > 0) {
                intent2.putExtra("latitude", Double.parseDouble(lat));
                intent2.putExtra("longitude", Double.parseDouble(lon));
            } else {
                intent2.putExtra("latitude", 34.8098080980);
                intent2.putExtra("longitude", 67.09098898);
            }

            startActivityForResult(intent2, 1);

        }

        if (v == findViewById(R.id.btnAdd)){
            Intent intent = new Intent(this, AttendeeActivity.class);
            intent.putExtra("attendee",0);
            intent.putExtra("attendee",0);
            startActivityForResult(intent, 2);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data!= null) {

                    editTextLat.setText(data.getStringExtra("LATITUDE_ID"));
                    editTextLong.setText(data.getStringExtra("LONGITUDE_ID"));
                }
//                Toast.makeText(this, data.getStringExtra("LATITUDE_ID"), Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, data.getStringExtra("LONGITUDE_ID"), Toast.LENGTH_SHORT).show()
// ;
            }
        }

        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                if (data!= null) {

                    idList = data.getIntegerArrayListExtra("ATTENDEE_ID");
                    nameList = data.getStringArrayListExtra("ATTENDEE_NAME");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < nameList.size(); i++) {

                            stringBuilder.append(nameList.get(i)+ "\n");

                    }
                    editTextAttendees.setText(stringBuilder);
                }
            }
        }
    }
}