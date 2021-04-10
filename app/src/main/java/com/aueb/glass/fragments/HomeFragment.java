package com.aueb.glass.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView profileImage = view.findViewById(R.id.userProfileImage);
        Picasso.with(getActivity().getApplicationContext()).load(MainActivity.account.getPhotoUrl()).into(profileImage);

        TextInputEditText firstName = view.findViewById(R.id.fullNameText);

        if (MainActivity.sharedPreferences.getString("FullName", "").isEmpty()) {
            firstName.setText(MainActivity.account.getDisplayName());
        } else {
            firstName.setText(MainActivity.sharedPreferences.getString("FullName", ""));
        }

        TextInputEditText email = view.findViewById(R.id.email);
        email.setText(MainActivity.account.getEmail());

        TextInputEditText phone = view.findViewById(R.id.phone);
        phone.setText(MainActivity.sharedPreferences.getString("Phone", ""));

        return view;
    }
}
