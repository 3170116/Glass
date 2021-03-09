package com.aueb.glass.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new HomeFragment()).commit();
        getActivity().setTitle("Home");

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.dashboardMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new HomeFragment()).commit();
                        getActivity().setTitle("Home");
                        break;
                    case R.id.transactions:
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new TransactionsFragment()).commit();
                        getActivity().setTitle("Transactions");
                        break;
                    case R.id.validating:
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.dashboardFrameLayout, new MiningFragment()).commit();
                        getActivity().setTitle("Mining");
                        break;
                }

                return true;

            }
        });

        return view;
    }
}
