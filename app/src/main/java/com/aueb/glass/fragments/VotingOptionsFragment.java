package com.aueb.glass.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aueb.glass.R;
import com.aueb.glass.adapters.OptionsListAdapter;
import com.aueb.glass.models.VotingOption;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class VotingOptionsFragment extends BottomSheetDialogFragment {

    private List<VotingOption> myOptions;

    private ListView optionsList;
    private Button saveButton;

    public VotingOptionsFragment(List<VotingOption> optionsList) {
        this.myOptions = optionsList;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voting_options, container, false);

        optionsList = view.findViewById(R.id.optionsList);
        saveButton = view.findViewById(R.id.saveOptionsButton);

        OptionsListAdapter optionsListAdapter = new OptionsListAdapter(getActivity().getApplicationContext(), myOptions);

        optionsList.setAdapter(optionsListAdapter);
        optionsListAdapter.notifyDataSetChanged();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

}
