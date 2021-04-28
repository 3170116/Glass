package com.aueb.glass.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aueb.glass.EventsActivity;
import com.aueb.glass.OnlineEventActivity;
import com.aueb.glass.R;
import com.aueb.glass.fragments.EditEventFragment;
import com.aueb.glass.fragments.VotingOptionsFragment;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.VotingOption;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyEventsListAdapter extends BaseAdapter {

    private class EventStartDateComparator implements Comparator<Event> {

        @Override
        public int compare(Event e1, Event e2) {
            return e1.getStartDate().compareTo(e2.getStartDate());
        }
    }

    private Context context;
    private CollectionReference events;
    private List<Event> myEvents;

    public MyEventsListAdapter(Context context, CollectionReference events, List<Event> myEvents) {
        this.context = context;
        this.events = events;
        this.myEvents = myEvents;
    }

    @Override
    public int getCount() {
        return myEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return myEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_my_event, parent, false);

        // get current item to be displayed
        Event myEvent = (Event) getItem(position);

        TextView title = convertView.findViewById(R.id.myEventTitle);
        title.setText(myEvent.getName());

        TextInputEditText startDate = convertView.findViewById(R.id.myEventStartDate);
        startDate.setText(myEvent.getStartDateToDisplay());

        TextInputEditText myRemainingTickets = convertView.findViewById(R.id.myEventRemainingTickets);
        myRemainingTickets.setText(myEvent.getRemainingTickets() + "");

        MaterialButton edit = convertView.findViewById(R.id.myEventEditButton);
        MyEventsListAdapter myEventsListAdapter = this;

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEventFragment editEventFragment = new EditEventFragment(myEventsListAdapter, events, myEvent);
                editEventFragment.show(EventsActivity.fragmentManager, "Edit Event");
            }
        });

        MaterialButton visit = convertView.findViewById(R.id.myEventVisitButton);
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OnlineEventActivity.class);

                intent.putExtra("event", myEvent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public void addEvent(Event event) {
        this.myEvents.add(event);
        resortEvents();
    }

    public void removeEvent(Event event) {
        for (int i = 0; i < this.myEvents.size(); i++) {
            if (this.myEvents.get(i).getId() == event.getId()) {
                this.myEvents.remove(i);
                break;
            }
        }
    }

    public void resortEvents() {
        Collections.sort(this.myEvents, new EventStartDateComparator());
    }

}
