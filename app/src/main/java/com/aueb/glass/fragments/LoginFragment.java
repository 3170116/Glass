package com.aueb.glass.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.app.Fragment;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;

public class LoginFragment extends Fragment {

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button googleBtn = view.findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = MainActivity.mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, MainActivity.RC_SIGN_IN);
            }
        });

        return view;
    }
}
