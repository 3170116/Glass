package com.aueb.glass.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.adapters.MyTicketsListAdapter;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.Participant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TicketsFragment extends Fragment {

    private CollectionReference events;
    private CollectionReference tickets;

    public TicketsFragment() {
        this.events = MainActivity.firebaseFirestore.collection("Events");
        this.tickets = MainActivity.firebaseFirestore.collection("Participants");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        ListView ticketsList = view.findViewById(R.id.ticketsList);
        List<Participant> myTickets = new ArrayList<>();

        MyTicketsListAdapter myTicketsListAdapter = new MyTicketsListAdapter(getActivity(), myTickets);
        ticketsList.setAdapter(myTicketsListAdapter);

        this.tickets
                .whereEqualTo("accountId", MainActivity.account.getId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Map<String, Object> data = doc.getData();
                            Participant currParticipant = new Participant();

                            currParticipant.setId(doc.getId());
                            currParticipant.setAccountId(MainActivity.account.getId());
                            currParticipant.setEventId(data.get("eventId").toString());
                            currParticipant.setFirstName(data.get("firstName").toString());
                            currParticipant.setLastName(data.get("lastName").toString());
                            currParticipant.setEmail(data.get("email").toString());
                            currParticipant.setPhone(data.get("phone").toString());
                            currParticipant.setPosition(data.get("position").toString());

                            events
                                    .document(currParticipant.getEventId())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Event currEvent = new Event();
                                                Map<String, Object> data = task.getResult().getData();

                                                if (data != null) {
                                                    currEvent.setId(currParticipant.getEventId());
                                                    currEvent.setOrganizerId(data.get("organizerId").toString());
                                                    currEvent.setName(data.get("name").toString());
                                                    currEvent.setDescription(data.get("description").toString());
                                                    currEvent.setCategory(data.get("category").toString());
                                                    currEvent.setUrl(data.get("url").toString());

                                                    Timestamp timestamp = (Timestamp) data.get("startDate");
                                                    currEvent.setStartDate(timestamp.toDate());

                                                    currEvent.setMaxTickets(Integer.parseInt(data.get("maxTickets").toString()));
                                                    currEvent.setRemainingTickets(Integer.parseInt(data.get("remainingTickets").toString()));
                                                    currEvent.setPublished(Boolean.parseBoolean(data.get("isPublished").toString()));

                                                    currParticipant.setEvent(currEvent);
                                                    myTickets.add(currParticipant);

                                                    myTicketsListAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return view;
    }
}
