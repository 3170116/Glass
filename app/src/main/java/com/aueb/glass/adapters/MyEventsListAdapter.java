package com.aueb.glass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.aueb.glass.EventsActivity;
import com.aueb.glass.R;
import com.aueb.glass.fragments.EditEventFragment;
import com.aueb.glass.models.Event;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MyEventsListAdapter extends BaseAdapter {

    private Context context;
    private List<Event> myEvents;

    public MyEventsListAdapter(Context context, List<Event> events) {
        this.context = context;
        this.myEvents = events;
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

        TextInputEditText endDate = convertView.findViewById(R.id.myEventEndDate);
        endDate.setText(myEvent.getEndDateToDisplay());

        TextInputEditText myRemainingTickets = convertView.findViewById(R.id.myEventRemainingTickets);
        myRemainingTickets.setText(myEvent.getRemainingTickets() + "");

        MaterialButton edit = convertView.findViewById(R.id.myEventEditButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditEventFragment editEventFragment = new EditEventFragment(myEvent);
                editEventFragment.show(EventsActivity.fragmentManager, "Edit Event");
            }
        });

        MaterialButton options = convertView.findViewById(R.id.myEventOptionsButton);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }

}
