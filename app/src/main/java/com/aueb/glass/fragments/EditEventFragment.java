package com.aueb.glass.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aueb.glass.R;
import com.aueb.glass.models.Event;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditEventFragment extends BottomSheetDialogFragment {

    private Event myEvent;

    public EditEventFragment(Event event) {
        this.myEvent = event;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);

        return view;
    }

}
