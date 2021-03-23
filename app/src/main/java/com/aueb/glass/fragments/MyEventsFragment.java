package com.aueb.glass.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.aueb.glass.EventsActivity;
import com.aueb.glass.R;
import com.aueb.glass.adapters.MyEventsListAdapter;
import com.aueb.glass.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyEventsFragment extends Fragment {

    public MyEventsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Οι εκδηλώσεις μου");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);

        ListView myEventsList = view.findViewById(R.id.myEventsList);

        List<Event> myEvents = new ArrayList<>();

        //για δοκιμες
        Event testEvent = new Event();

        testEvent.setId("fdg80d");
        testEvent.setName("Σεμινάριο πρώτων βοηθειών");

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 13);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.HOUR, 15);
        calendar.set(Calendar.MINUTE, 0);

        testEvent.setStartDate(calendar.getTime());

        calendar.set(Calendar.HOUR, 17);
        calendar.set(Calendar.MINUTE, 30);

        testEvent.setEndDate(calendar.getTime());
        testEvent.setRemainingTickets(12);

        testEvent.setPublished(true);

        myEvents.add(testEvent);
        //

        MyEventsListAdapter myEventsListAdapter = new MyEventsListAdapter(getActivity().getApplicationContext(), myEvents);

        myEventsList.setAdapter(myEventsListAdapter);
        myEventsListAdapter.notifyDataSetChanged();

        FloatingActionButton addEvent = view.findViewById(R.id.fabAddEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEventFragment editEventFragment = new EditEventFragment(new Event());
                editEventFragment.show(EventsActivity.fragmentManager, "Create Event");
            }
        });

        return view;
    }

}
