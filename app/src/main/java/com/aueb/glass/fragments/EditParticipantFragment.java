package com.aueb.glass.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class EditParticipantFragment extends BottomSheetDialogFragment {

    private Event myEvent;
    private Participant myParticipant;

    private CollectionReference events;
    private CollectionReference participants;

    public EditParticipantFragment(CollectionReference events, Event event) {
        this.myEvent = event;
        this.myParticipant = new Participant();

        this.events = events;
        this.participants = MainActivity.firebaseFirestore.collection("Participants");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit_participant, container, false);

        TextInputEditText firstName = view.findViewById(R.id.participantFirstName);
        TextInputEditText lastName = view.findViewById(R.id.participantLastName);
        TextInputEditText email = view.findViewById(R.id.participantEmail);
        TextInputEditText phone = view.findViewById(R.id.participantPhone);
        AutoCompleteTextView position = view.findViewById(R.id.participantPosition);

        Button saveBtn = view.findViewById(R.id.saveParticipantButton);

        email.setText(MainActivity.account.getEmail());
        phone.setText(MainActivity.sharedPreferences.getString("Phone", ""));

        ArrayAdapter<CharSequence> positionsAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.roles, R.layout.adapter_dropdown_text);
        position.setAdapter(positionsAdapter);

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

                if (position.getText() == null || position.getText().length() == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.required_not_found), Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> participantsData = new HashMap<>();

                participantsData.put("accountId", MainActivity.account.getId());
                participantsData.put("eventId", myEvent.getId());
                participantsData.put("firstName", firstName.getText() + "");
                participantsData.put("lastName", lastName.getText() + "");
                participantsData.put("email", email.getText() + "");
                participantsData.put("phone", phone.getText() + "");
                participantsData.put("position", position.getText() + "");

                participants
                        .add(participantsData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Map<String, Object> data = new HashMap<>();

                                data.put("organizerId", myEvent.getOrganizerId());
                                data.put("name", myEvent.getName());
                                data.put("description", myEvent.getDescription());
                                data.put("category", myEvent.getCategory());
                                data.put("url", myEvent.getUrl());
                                data.put("startDate", myEvent.getStartDate());
                                data.put("maxTickets", myEvent.getMaxTickets());
                                data.put("remainingTickets", myEvent.getRemainingTickets() - 1);
                                data.put("isPublished", myEvent.isPublished());

                                events
                                        .document(myEvent.getId())
                                        .set(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Κλείσατε ένα εισιτήριο!", Toast.LENGTH_SHORT).show();
                                                dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }
}