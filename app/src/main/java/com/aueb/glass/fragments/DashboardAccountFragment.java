package com.aueb.glass.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardAccountFragment extends Fragment {

    private int fragmentIndex;

    public DashboardAccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new SearchFragment()).commit();
        getActivity().setTitle("Search Online Events");
        setSearchFragmentIndex();

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.dashboardMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.search:
                        if (!isSearchFragmentOpen()) {
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new SearchFragment()).commit();
                            getActivity().setTitle("Search Online Events");
                            setSearchFragmentIndex();
                        }
                        break;
                    case R.id.ticket:
                        if (!isTicketsFragmentOpen()) {
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new TicketsFragment()).commit();
                            getActivity().setTitle("My Tickets");
                            setTicketsFragmentIndex();
                        }
                        break;
                    case R.id.home:
                        if (!isProfileFragmentOpen()) {
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new HomeFragment()).commit();
                            getActivity().setTitle("Profile");
                            setProfileFragmentIndex();
                        }
                        break;
                }

                return true;

            }
        });

        return view;
    }


    private void setSearchFragmentIndex() {
        this.fragmentIndex = 1;
    }

    private boolean isSearchFragmentOpen() {
        return this.fragmentIndex == 1;
    }

    private void setTicketsFragmentIndex() {
        this.fragmentIndex = 2;
    }

    private boolean isTicketsFragmentOpen() {
        return this.fragmentIndex == 2;
    }

    private void setProfileFragmentIndex() {
        this.fragmentIndex = 3;
    }

    private  boolean isProfileFragmentOpen() {
        return this.fragmentIndex == 3;
    }
}
