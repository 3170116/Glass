package com.aueb.glass.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.aueb.glass.OnlineEventActivity;
import com.aueb.glass.R;
import com.aueb.glass.fragments.EditEventFragment;
import com.aueb.glass.fragments.EditParticipantFragment;
import com.aueb.glass.fragments.VotingOptionsFragment;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.Participant;
import com.aueb.glass.models.VotingOption;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyTicketsListAdapter extends BaseAdapter {

    private Context context;
    private List<Participant> myTickets;

    public MyTicketsListAdapter(Context context, List<Participant> tickets) {
        this.context = context;
        this.myTickets = tickets;
    }

    @Override
    public int getCount() {
        return myTickets.size();
    }

    @Override
    public Object getItem(int position) {
        return myTickets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_ticket, parent, false);

        // get current item to be displayed
        Participant myTicket = (Participant) getItem(position);

        TextView name = convertView.findViewById(R.id.event_name);

        if (myTicket.getEvent() != null) {
            name.setText(myTicket.getEvent().getName());

            TextView description = convertView.findViewById(R.id.event_description);
            description.setText(myTicket.getEvent().getDescription());

            TextView startDate = convertView.findViewById(R.id.event_start_date);
            startDate.setText(myTicket.getEvent().getStartDateToDisplay());
        } else {
            name.setText("Η εκδήλωση έχει διαγραφεί.");
        }

        Button visitBtn = convertView.findViewById(R.id.visitEventButton);

        if (myTicket.getEvent() != null) {
            visitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OnlineEventActivity.class);
                    intent.putExtra("event", myTicket.getEvent());
                    context.startActivity(intent);
                }
            });
        } else {
            visitBtn.setVisibility(View.GONE);
        }

        return convertView;
    }

}
