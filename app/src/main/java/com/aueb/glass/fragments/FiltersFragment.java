package com.aueb.glass.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aueb.glass.R;
import com.aueb.glass.adapters.SearchEventsListAdapter;
import com.aueb.glass.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class FiltersFragment extends BottomSheetDialogFragment {

    private CalendarView fromDate;
    private AutoCompleteTextView eventCategoryText;
    private Button searchButton;

    private SearchEventsListAdapter searchEventsListAdapter;
    private CollectionReference events;

    private Calendar calendar;

    public FiltersFragment(CollectionReference events, SearchEventsListAdapter searchEventsListAdapter) {
        this.events = events;
        this.searchEventsListAdapter = searchEventsListAdapter;

        this.calendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.categories, R.layout.adapter_dropdown_text);

        fromDate = view.findViewById(R.id.fromDate);
        eventCategoryText = view.findViewById(R.id.eventCategoryText);
        searchButton = view.findViewById(R.id.searchButton);

        long now = new Date().getTime();
        fromDate.setMinDate(now);
        fromDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
            }
        });
        calendar.setTime(new Date(now));

        eventCategoryText.setAdapter(categoriesAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEventsListAdapter.clear();

                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);

                events
                        .whereEqualTo("isPublished", true)
                        .whereEqualTo("category", eventCategoryText.getText() + "")
                        .whereGreaterThan("startDate", calendar.getTime())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Event currEvent = new Event();
                                        Map<String, Object> data = document.getData();

                                        if (Integer.parseInt(data.get("remainingTickets").toString()) > 0) {
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
                                            currEvent.setShowLiveParticipants(Boolean.parseBoolean(data.get("showLiveParticipants").toString()));
                                            currEvent.setPublished(Boolean.parseBoolean(data.get("isPublished").toString()));

                                            searchEventsListAdapter.addEvent(currEvent);
                                        }
                                    }

                                    searchEventsListAdapter.notifyDataSetChanged();
                                } else {
                                    Log.e("ORG", "Error getting documents: ", task.getException());
                                    Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return view;
    }
}
