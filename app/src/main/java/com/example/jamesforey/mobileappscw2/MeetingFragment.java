package com.example.jamesforey.mobileappscw2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeetingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeetingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingFragment extends ListFragment implements android.view.View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeetingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeetingFragment newInstance(String param1, String param2) {
        MeetingFragment fragment = new MeetingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView meeting_Id;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_meeting, container, false);

//        Button createButton = rootView.findViewById(R.id.btnAdd);
//        createButton.setOnClickListener(this);
//        Button btnGetAll = rootView.findViewById(R.id.btnGetAll);
//        btnGetAll.setOnClickListener(this);
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        FloatingActionButton refresh = rootView.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        FloatingActionButton settings = rootView.findViewById(R.id.settings);
        settings.setOnClickListener(this);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == getActivity().findViewById(R.id.fab)){

            Intent intent = new Intent(getActivity(),CreateMeeting.class);
            intent.putExtra("meeting_Id",0);
            startActivity(intent);

        }
        else if (v == getActivity().findViewById(R.id.refresh)){

            MeetingRepo repo = new MeetingRepo(getActivity());

            ArrayList<HashMap<String, String>> meetingList =  repo.getMeetingList();
            if(meetingList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        meeting_Id = view.findViewById(R.id.meeting_Id);
                        String meetingId = meeting_Id.getText().toString();
                        Intent objIndent = new Intent(getActivity().getApplicationContext(),CreateMeeting.class);
                        objIndent.putExtra("meeting_Id", Integer.parseInt( meetingId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( getActivity(),meetingList, R.layout.view_meeting_entry, new String[] { "id","title"}, new int[] {R.id.meeting_Id, R.id.meeting_title});
                setListAdapter(adapter);
            }else{
                Toast.makeText(getActivity(),"No meeting!",Toast.LENGTH_SHORT).show();
            }

        } else if (v == getActivity().findViewById(R.id.settings)){
            Intent intent = new Intent(getActivity(),SettingsActivity.class);
            intent.putExtra("settings", 0);
            startActivity(intent);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
