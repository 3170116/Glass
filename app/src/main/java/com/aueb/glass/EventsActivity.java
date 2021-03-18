package com.aueb.glass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.aueb.glass.adapters.MyEventsListAdapter;
import com.aueb.glass.fragments.MyEventsFragment;
import com.aueb.glass.fragments.SearchFragment;
import com.aueb.glass.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
    }

    @Override
    protected void onStart() {
        super.onStart();

        fragmentManager = getFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fragmentManager.beginTransaction().replace(R.id.eventsFrameLayout, new MyEventsFragment()).commit();
    }
}