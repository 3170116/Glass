package com.aueb.glass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.aueb.glass.EventsActivity;
import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.fragments.EditEventFragment;
import com.aueb.glass.fragments.EditParticipantFragment;
import com.aueb.glass.fragments.VotingOptionsFragment;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.VotingOption;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchEventsListAdapter extends BaseAdapter {

    private Context context;
    private List<Event> myEvents;

    private CollectionReference events;

    public SearchEventsListAdapter(Context context, CollectionReference events, List<Event> myEvents) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.row_event, parent, false);

        // get current item to be displayed
        Event myEvent = (Event) getItem(position);

        TextView name = convertView.findViewById(R.id.event_name);
        name.setText(myEvent.getName());

        TextView description = convertView.findViewById(R.id.event_description);
        description.setText(myEvent.getDescription());

        TextView startDate = convertView.findViewById(R.id.event_start_date);
        startDate.setText(myEvent.getStartDateToDisplay());

        Button addTicket = convertView.findViewById(R.id.addTicketButton);
        addTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditParticipantFragment editParticipantFragment = new EditParticipantFragment(events, myEvent);
                editParticipantFragment.show(MainActivity.fragmentManager, "Edit Event");
            }
        });

        return convertView;
    }


    public void addEvent(Event event) {
        this.myEvents.add(event);
    }

    public void clear() {
        this.myEvents.clear();
    }

}
