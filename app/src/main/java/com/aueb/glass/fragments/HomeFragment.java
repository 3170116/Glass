package com.aueb.glass.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

        TextInputEditText address = view.findViewById(R.id.addressText);
        address.setText(MainActivity.account.getId());

        TextInputEditText firstName = view.findViewById(R.id.fullNameText);
        firstName.setText(MainActivity.account.getDisplayName());

        TextInputEditText email = view.findViewById(R.id.email);
        email.setText(MainActivity.account.getEmail());

        ImageView qrCode = view.findViewById(R.id.qrCodeImg);
        Picasso.with(getActivity().getApplicationContext()).load("https://chart.googleapis.com/chart?chs=450x450&cht=qr&chl=" + MainActivity.account.getId()).into(qrCode);

        Button logout = view.findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(getActivity().getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                LoginFragment loginFragment = new LoginFragment();
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.frameLayout, loginFragment).commit();

                                getActivity().setTitle("Glass");
                            }
                        });
            }
        });

        return view;
    }
}
