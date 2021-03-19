package com.aueb.glass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;

import com.aueb.glass.fragments.MyEventsFragment;

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

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();

        fragmentManager.beginTransaction().replace(R.id.eventsFrameLayout, new MyEventsFragment()).commit();
    }
}