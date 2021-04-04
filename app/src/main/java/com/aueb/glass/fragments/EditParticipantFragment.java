package com.aueb.glass.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.Participant;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class EditParticipantFragment extends BottomSheetDialogFragment {

    private Event myEvent;
    private Participant myParticipant;

    public EditParticipantFragment(Event event) {
        this.myEvent = event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_participant, container, false);

        TextInputEditText firstName = view.findViewById(R.id.participantFirstName);
        TextInputEditText lastName = view.findViewById(R.id.participantLastName);
        TextInputEditText email = view.findViewById(R.id.participantEmail);
        TextInputEditText phone = view.findViewById(R.id.participantPhone);
        AutoCompleteTextView role = view.findViewById(R.id.participantPosition);

        Button saveBtn = view.findViewById(R.id.saveParticipantButton);

        email.setText(MainActivity.account.getEmail());
        phone.setText(MainActivity.sharedPreferences.getString("Phone", ""));

        ArrayAdapter<CharSequence> rolesAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.roles, R.layout.adapter_dropdown_text);
        role.setAdapter(rolesAdapter);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstName.getText() == null || firstName.getText().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.required_not_found), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (lastName.getText() == null || lastName.getText().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.required_not_found), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.getText() == null || email.getText().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.required_not_found), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.getText() == null || phone.getText().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.required_not_found), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (role.getText() == null || role.getText().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.required_not_found), Toast.LENGTH_SHORT).show();
                    return;
                }

                //TODO να σώζει νέα εγγραφή Participant και να ενημερώνει το πεδίο των υπολειπόμενων εισιτηρίων κατά 1
            }
        });

        return view;
    }
}