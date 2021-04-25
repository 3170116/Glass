package com.aueb.glass.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aueb.glass.EventsActivity;
import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.adapters.MyEventsListAdapter;
import com.aueb.glass.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyEventsFragment extends Fragment {

    private CollectionReference events;

    public MyEventsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Οι εκδηλώσεις μου");
        events = MainActivity.firebaseFirestore.collection("Events");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);

        ListView myEventsList = view.findViewById(R.id.myEventsList);

        List<Event> myEvents = new ArrayList<>();

        MyEventsListAdapter myEventsListAdapter = new MyEventsListAdapter(getActivity().getApplicationContext(), events, myEvents);
        myEventsList.setAdapter(myEventsListAdapter);

        events
                .whereEqualTo("organizerId", MainActivity.account.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Event currEvent = new Event();
                                Map<String, Object> data = document.getData();

                                currEvent.setId(document.getId());
                                currEvent.setOrganizerId(data.get("organizerId").toString());
                                currEvent.setName(data.get("name").toString());
                                currEvent.setDescription(data.get("description").toString());
                                currEvent.setCategory(data.get("category").toString());
                                currEvent.setUrl(data.get("url").toString());

                                Timestamp timestamp = (Timestamp) data.get("startDate");
                                currEvent.setStartDate(timestamp.toDate());

                                currEvent.setMaxTickets(Integer.parseInt(data.get("maxTickets").toString()));
                                currEvent.setRemainingTickets(Integer.parseInt(data.get("remainingTickets").toString()));
                                currEvent.setPublished(Boolean.parseBoolean(data.get("isPublished").toString()));

                                myEvents.add(currEvent);
                            }

                            myEventsListAdapter.resortEvents();
                            myEventsListAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("ORG", "Error getting documents: ", task.getException());
                            Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FloatingActionButton addEvent = view.findViewById(R.id.fabAddEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEventFragment editEventFragment = new EditEventFragment(myEventsListAdapter, events, new Event());
                editEventFragment.show(EventsActivity.fragmentManager, "Create Event");
            }
        });

        return view;
    }

}
