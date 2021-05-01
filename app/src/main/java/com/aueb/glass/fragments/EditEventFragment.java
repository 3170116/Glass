package com.aueb.glass.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.adapters.MyEventsListAdapter;
import com.aueb.glass.models.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditEventFragment extends BottomSheetDialogFragment {

    private CollectionReference events;
    private MyEventsListAdapter myEventsListAdapter;
    private Event myEvent;

    private Calendar calendar;

    private int startHour = 0;
    private int startMinute = 0;

    private TextInputEditText name;
    private TextInputEditText description;
    private AutoCompleteTextView category;
    private TextInputEditText url;
    private CalendarView date;
    private AutoCompleteTextView startHourText;
    private AutoCompleteTextView startMinuteText;
    private TextInputEditText maxTickets;
    private SwitchMaterial isPublished;

    private Button save;
    private Button delete;

    public EditEventFragment(MyEventsListAdapter myEventsListAdapter, CollectionReference events, Event event) {
        this.events = events;
        this.myEventsListAdapter = myEventsListAdapter;
        this.myEvent = event;

        calendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.categories, R.layout.adapter_dropdown_text);
        ArrayAdapter<CharSequence> hoursAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.hours, R.layout.adapter_dropdown_text);
        ArrayAdapter<CharSequence> minutesAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.minutes, R.layout.adapter_dropdown_text);

        name = view.findViewById(R.id.eventName);
        description = view.findViewById(R.id.eventDescription);
        category = view.findViewById(R.id.eventCategory);
        url = view.findViewById(R.id.eventUrl);

        long now = new Date().getTime();

        date = view.findViewById(R.id.eventDate);
        date.setMinDate(now);

        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
            }
        });

        startHourText = view.findViewById(R.id.eventStartHour);
        startHourText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startHour = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }
        });

        startMinuteText = view.findViewById(R.id.eventStartMinute);
        startMinuteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startMinute = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }
        });

        maxTickets = view.findViewById(R.id.eventMaxTickets);
        isPublished = view.findViewById(R.id.eventIsPublished);

        save = view.findViewById(R.id.editEventButton);
        delete = view.findViewById(R.id.deleteEventButton);

        if (myEvent.getId() != null && !myEvent.getId().isEmpty()) {
            name.setText(myEvent.getName());
            description.setText(myEvent.getDescription());
            category.setText(myEvent.getCategory());
            url.setText(myEvent.getUrl());

            date.setDate(myEvent.getStartDate().getTime());
            calendar.setTime(myEvent.getStartDate());
            startHourText.setText(myEvent.getStartDate().getHours() + "");
            startMinuteText.setText(myEvent.getStartDate().getMinutes() + "");
            startHour = myEvent.getStartDate().getHours();
            startMinute = myEvent.getStartDate().getMinutes();

            maxTickets.setText(myEvent.getMaxTickets() + "");
            isPublished.setChecked(myEvent.isPublished());

            delete.setVisibility(View.VISIBLE);
        } else {
            calendar.setTime(new Date(now));
            category.setText("Σεμινάριο");
        }

        category.setAdapter(categoriesAdapter);
        startMinuteText.setAdapter(minutesAdapter);
        startHourText.setAdapter(hoursAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEvent.setName(name.getText() + "");
                myEvent.setDescription(description.getText() + "");
                myEvent.setCategory(category.getText() + "");
                myEvent.setUrl(url.getText() + "");

                calendar.set(Calendar.HOUR_OF_DAY, startHour);
                calendar.set(Calendar.MINUTE, startMinute);

                myEvent.setStartDate(calendar.getTime());

                myEvent.resetMaxAndRemainingTickets(Integer.parseInt(maxTickets.getText() == null || maxTickets.getText().toString().isEmpty() ? "0" :maxTickets.getText().toString()));
                myEvent.setPublished(isPublished.isChecked());

                if (myEvent.getId() == null || myEvent.getId().isEmpty()) {
                    myEvent.setOrganizerId(MainActivity.account.getId());

                    Map<String, Object> data = new HashMap<>();

                    data.put("organizerId", myEvent.getOrganizerId());
                    data.put("name", myEvent.getName());
                    data.put("description", myEvent.getDescription());
                    data.put("category", myEvent.getCategory());
                    data.put("url", myEvent.getUrl());
                    data.put("startDate", myEvent.getStartDate());
                    data.put("maxTickets", myEvent.getMaxTickets());
                    data.put("remainingTickets", myEvent.getMaxTickets());
                    data.put("isPublished", myEvent.isPublished());

                    events
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Οι αλλαγές αποθηκεύτηκαν!", Toast.LENGTH_SHORT).show();
                                    myEvent.setId(documentReference.getId());

                                    myEventsListAdapter.addEvent(myEvent);
                                    myEventsListAdapter.notifyDataSetChanged();

                                    dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Map<String, Object> data = new HashMap<>();

                    data.put("organizerId", myEvent.getOrganizerId());
                    data.put("name", myEvent.getName());
                    data.put("description", myEvent.getDescription());
                    data.put("category", myEvent.getCategory());
                    data.put("url", myEvent.getUrl());
                    data.put("startDate", myEvent.getStartDate());
                    data.put("maxTickets", myEvent.getMaxTickets());
                    data.put("remainingTickets", myEvent.getMaxTickets());
                    data.put("isPublished", myEvent.isPublished());

                    events
                            .document(myEvent.getId())
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Οι αλλαγές αποθηκεύτηκαν!", Toast.LENGTH_SHORT).show();

                                    myEventsListAdapter.resortEvents();
                                    myEventsListAdapter.notifyDataSetChanged();

                                    dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                events
                        .document(myEvent.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity().getApplicationContext(), "Η εκδήλωση διαγράφηκε!", Toast.LENGTH_SHORT).show();

                                myEventsListAdapter.removeEvent(myEvent);
                                myEventsListAdapter.notifyDataSetChanged();

                                dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }

}
