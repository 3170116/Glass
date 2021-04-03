package com.aueb.glass.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aueb.glass.R;
import com.aueb.glass.adapters.SearchEventsListAdapter;
import com.aueb.glass.models.Event;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public SearchFragment() {

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

        //για δοκιμες
        Event testEvent = new Event();

        testEvent.setName("Σεμινάριο πρώτων βοηθειών");
        testEvent.setDescription("Ελάτε για να μάθετε τα μυστικά των πρώτων βοηθειών.");

        searchEvents.add(testEvent);


        SearchEventsListAdapter searchEventsListAdapter = new SearchEventsListAdapter(getActivity().getApplicationContext(), searchEvents);

        eventsList.setAdapter(searchEventsListAdapter);
        searchEventsListAdapter.notifyDataSetChanged();

        return view;
    }

}
