package com.aueb.glass.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.aueb.glass.R;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.Participant;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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

        ArrayAdapter<CharSequence> rolesAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.roles, R.layout.adapter_dropdown_text);

        AutoCompleteTextView roles = view.findViewById(R.id.participantPosition);
        roles.setAdapter(rolesAdapter);

        return view;
    }
}