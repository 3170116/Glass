package com.aueb.glass.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aueb.glass.R;
import com.aueb.glass.adapters.MyTicketsListAdapter;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.Participant;

import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends Fragment {

    public TicketsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        ListView ticketsList = view.findViewById(R.id.ticketsList);

        List<Participant> myTickets = new ArrayList<>();

        //για δοκιμές
        Event testEvent = new Event();

        testEvent.setName("Σεμινάριο πρώτων βοηθειών");
        testEvent.setDescription("Ελάτε για να μάθετε τα μυστικά των πρώτων βοηθειών.");

        myTickets.add(new Participant(testEvent));


        MyTicketsListAdapter myTicketsListAdapter = new MyTicketsListAdapter(getActivity(), myTickets);

        ticketsList.setAdapter(myTicketsListAdapter);
        myTicketsListAdapter.notifyDataSetChanged();

        return view;
    }
}
