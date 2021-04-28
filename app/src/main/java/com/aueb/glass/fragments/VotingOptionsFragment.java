package com.aueb.glass.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.adapters.OptionsListAdapter;
import com.aueb.glass.adapters.VotesListAdapter;
import com.aueb.glass.models.VotingOption;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingOptionsFragment extends BottomSheetDialogFragment {

    private VotesListAdapter myVotesListAdapter;
    private List<VotingOption> myOptions;

    private ListView optionsList;
    private Button saveButton;

    private CollectionReference votingOptions;

    public VotingOptionsFragment(CollectionReference votingOptions, VotesListAdapter votesListAdapter, List<VotingOption> optionsList) {
        this.myVotesListAdapter = votesListAdapter;
        this.myOptions = optionsList;
        this.votingOptions = votingOptions;
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
                votingOptions
                        .whereEqualTo("eventId", myVotesListAdapter.getMyEvent().getId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot doc: task.getResult()) {
                                        votingOptions.document(doc.getId()).delete();
                                    }
                                }

                                myVotesListAdapter.setOptions(myOptions);
                                myVotesListAdapter.notifyDataSetChanged();

                                for (VotingOption option: myOptions) {
                                    if (option.isSelected()) {
                                        Map<String, Object> data = new HashMap<>();

                                        data.put("eventId", option.getEventId());
                                        data.put("typeId", option.getTypeId());
                                        data.put("text", option.getText());
                                        data.put("votes", option.getVotes());

                                        votingOptions
                                                .add(data)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                                    }
                                                });
                                    }
                                }

                                dismiss();
                            }
                        });
            }
        });

        return view;
    }

}
