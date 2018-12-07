package com.example.jamesforey.mobileappscw2;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AttendeeActivity extends Activity implements android.view.View.OnClickListener{

    Button createAttendeeBtn, refreshListBtn, addAttendeeButton, selectBtn;

    private Context context;
    private GridListAdapter adapter;
    private ArrayList<Attendees> arrayList;
    private ListView listView;
    TextView attendee_Id;
    private ArrayList<Integer> selectedIds = new ArrayList<>();
    private ArrayList<String> selectedNames = new ArrayList<>();
    private AttendeesRepo repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee);
        repo = new AttendeesRepo(this);

        createAttendeeBtn = findViewById(R.id.btnCreateAttendee);
        refreshListBtn = findViewById(R.id.btnGetAllAttendees);
//        addAttendeeButton = findViewById(R.id.btnAddAttendee);
        selectBtn = findViewById(R.id.select_btn);

        createAttendeeBtn.setOnClickListener(this);
        refreshListBtn.setOnClickListener(this);
//        addAttendeeButton.setOnClickListener(this);
        selectBtn.setOnClickListener(this);

        Intent intent = getIntent();
        intent.getIntExtra("attendee",0);



        arrayList = new ArrayList<>();
        arrayList.addAll(repo.getAttendeeList()); // don't need to refresh
        adapter = new GridListAdapter(this, arrayList);

        listView = findViewById(R.id.listOfAttendees);
        listView.setAdapter(adapter);


    }


    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.btnCreateAttendee)){

            Intent intent = new Intent(this,CreateAttendee.class);
            intent.putExtra("attendee_Id",0);
            startActivity(intent);

        }
        else {


//            ArrayList<Attendees> attendeeList = repo.getAttendeeList() ;

            arrayList.clear();
            arrayList.addAll(repo.getAttendeeList());

            adapter.notifyDataSetChanged();

        }

        if (view == findViewById(R.id.select_btn)){
            SparseBooleanArray selectedRows = adapter.getSelectedIds();
            if (selectedRows.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < selectedRows.size(); i++) {
                    if (selectedRows.valueAt(i)) {
                        int selectedRowLabel = arrayList.get(selectedRows.keyAt(i)).getAttendee_ID();
                        selectedIds.add(arrayList.get(selectedRows.keyAt(i)).getAttendee_ID());
                        selectedNames.add(arrayList.get(selectedRows.keyAt(i)).getAttendeeName());
                        stringBuilder.append(selectedRowLabel + "\n");
                    }
                }
                Toast.makeText(AttendeeActivity.this, "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                Log.d("tag","Selected Rows\n" + stringBuilder.toString());
            }

//            Integer[] attendeeSizeIDArray = new Integer[selectedIds.size()];
//            attendeeSizeIDArray = selectedIds.toArray(attendeeSizeIDArray);
//            String[] attendeeSizeNameArray = new String[selectedIds.size()];
//            attendeeSizeNameArray = selectedNames.toArray(attendeeSizeNameArray);


            Intent objIntent = new Intent(AttendeeActivity.this, CreateMeeting.class);
            objIntent.putExtra("ATTENDEE_ID", selectedIds);
            objIntent.putExtra("ATTENDEE_NAME", selectedNames);


            setResult(RESULT_OK, objIntent);
            finish();
        }
    }
}
