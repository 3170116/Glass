package com.aueb.glass.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.adapters.SearchEventsListAdapter;
import com.aueb.glass.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {

    private CollectionReference events;
    private SearchEventsListAdapter searchEventsListAdapter;

    public SearchFragment() {
        events = MainActivity.firebaseFirestore.collection("Events");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ListView eventsList = view.findViewById(R.id.searchEventsList);

        List<Event> searchEvents = new ArrayList<>();
        searchEventsListAdapter = new SearchEventsListAdapter(getActivity().getApplicationContext(), events, searchEvents);

        events
                .whereEqualTo("isPublished", true)
                .whereGreaterThan("remainingTickets", 0)
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

                                searchEvents.add(currEvent);
                            }

                            eventsList.setAdapter(searchEventsListAdapter);
                            searchEventsListAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("ORG", "Error getting documents: ", task.getException());
                            Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Button filter = view.findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FiltersFragment filtersFragment = new FiltersFragment(events, searchEventsListAdapter);
                filtersFragment.show(MainActivity.fragmentManager, "Filters");
            }
        });

        return view;
    }

}
