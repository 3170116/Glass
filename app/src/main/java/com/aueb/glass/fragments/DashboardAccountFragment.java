package com.aueb.glass.fragments;

import androidx.fragment.app.Fragment;

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

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.dashboardMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.search:
                        if (!isSearchFragmentOpen()) {
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new SearchFragment()).commit();
                            getActivity().setTitle("Αναζήτηση εκδηλώσεων");
                            setSearchFragmentIndex();
                        }
                        break;
                    case R.id.ticket:
                        if (!isTicketsFragmentOpen()) {
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new TicketsFragment()).commit();
                            getActivity().setTitle("Εισιτήρια");
                            setTicketsFragmentIndex();
                        }
                        break;
                    case R.id.home:
                        if (!isProfileFragmentOpen()) {
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new HomeFragment()).commit();
                            getActivity().setTitle("Προφίλ");
                            setProfileFragmentIndex();
                        }
                        break;
                }

                return true;

            }
        });

        if (MainActivity.FragmentIndex == 0 || MainActivity.FragmentIndex == 1) {
            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new SearchFragment()).commit();
            getActivity().setTitle("Αναζήτηση εκδηλώσεων");
            bottomNavigationView.setSelectedItemId(R.id.search);
            setSearchFragmentIndex();
        } else if(MainActivity.FragmentIndex == 2) {
            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new TicketsFragment()).commit();
            getActivity().setTitle("Εισιτήρια");
            bottomNavigationView.setSelectedItemId(R.id.ticket);
            setTicketsFragmentIndex();
        } else {
            MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new HomeFragment()).commit();
            getActivity().setTitle("Προφίλ");
            bottomNavigationView.setSelectedItemId(R.id.home);
            setProfileFragmentIndex();
        }

        return view;
    }


    private void setSearchFragmentIndex() {
        MainActivity.FragmentIndex = 1;
    }

    private boolean isSearchFragmentOpen() {
        return MainActivity.FragmentIndex == 1;
    }

    private void setTicketsFragmentIndex() {
        MainActivity.FragmentIndex = 2;
    }

    private boolean isTicketsFragmentOpen() {
        return MainActivity.FragmentIndex == 2;
    }

    private void setProfileFragmentIndex() {
        MainActivity.FragmentIndex = 3;
    }

    private  boolean isProfileFragmentOpen() {
        return MainActivity.FragmentIndex == 3;
    }
}
