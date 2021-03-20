package com.aueb.glass.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aueb.glass.R;
import com.aueb.glass.models.Event;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class EditEventFragment extends BottomSheetDialogFragment {

    private Event myEvent;

    private TextInputEditText name;
    private TextInputEditText description;
    private TextInputEditText url;
    private CalendarView date;
    private AutoCompleteTextView startHour;
    private AutoCompleteTextView startMinute;
    private AutoCompleteTextView endHour;
    private AutoCompleteTextView endMinute;
    private TextInputEditText maxTickets;
    private TextInputEditText remainingTickets;
    private SwitchMaterial showLiveParticipants;
    private  SwitchMaterial isPublished;

    private Button save;
    private Button delete;

    public EditEventFragment(Event event) {
        this.myEvent = event;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);

        ArrayAdapter<CharSequence> hoursAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.hours, R.layout.adapter_dropdown_text);
        ArrayAdapter<CharSequence> minutesAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.minutes, R.layout.adapter_dropdown_text);

        name = view.findViewById(R.id.eventName);
        description = view.findViewById(R.id.eventDescription);

        url = view.findViewById(R.id.eventUrl);

        date = view.findViewById(R.id.eventDate);
        date.setMinDate(new Date().getTime());

        startHour = view.findViewById(R.id.eventStartHour);
        startHour.setAdapter(hoursAdapter);

        startMinute = view.findViewById(R.id.eventStartMinute);
        startMinute.setAdapter(minutesAdapter);

        endHour = view.findViewById(R.id.eventEndHour);
        endHour.setAdapter(hoursAdapter);

        endMinute = view.findViewById(R.id.eventEndMinute);
        endMinute.setAdapter(minutesAdapter);

        maxTickets = view.findViewById(R.id.eventMaxTickets);
        remainingTickets = view.findViewById(R.id.eventRemainingTickets);

        showLiveParticipants = view.findViewById(R.id.eventShowLiveParticipants);
        isPublished = view.findViewById(R.id.eventIsPublished);

        save = view.findViewById(R.id.editEventButton);
        delete = view.findViewById(R.id.deleteEventButton);

        if (myEvent.getId() != null && !myEvent.getId().isEmpty()) {

            name.setText(myEvent.getName());
            description.setText(myEvent.getDescription());

            url.setText(myEvent.getUrl());
            date.setDate(myEvent.getStartDate().getTime());

            maxTickets.setText(myEvent.getMaxTickets() + "");
            remainingTickets.setText(myEvent.getRemainingTickets() + "");

            showLiveParticipants.setChecked(myEvent.isShowLiveParticipants());
            isPublished.setChecked(myEvent.isPublished());

            delete.setVisibility(View.VISIBLE);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO να καλει το firebase
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO να καλει το firebase
            }
        });

        return view;
    }

}
