package com.aueb.glass.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.VotingOption;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class VotesListAdapter extends BaseAdapter {

    private Context context;
    private Event myEvent;
    private List<VotingOption> myOptions;
    private List<VotingOption> myVotes;
    private CollectionReference votingOptions;

    private boolean disableAll;

    public VotesListAdapter(Context context, CollectionReference votingOptions, Event myEvent, List<VotingOption> myVotes) {
        this.context = context;
        this.votingOptions = votingOptions;
        this.myEvent = myEvent;
        this.myVotes = myVotes;
        this.myOptions = new ArrayList<>();

        this.disableAll = false;
    }

    @Override
    public int getCount() {
        return myOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return myOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_vote, parent, false);

        // get current item to be displayed
        VotingOption option = (VotingOption) getItem(position);

        RadioButton optionRadio = convertView.findViewById(R.id.optionCheck);
        TextView votesText = convertView.findViewById(R.id.votesText);

        optionRadio.setText(option.getText());
        optionRadio.setChecked(false);

        if (disableAll) {
            optionRadio.setEnabled(false);
        }

        for (VotingOption vote: myVotes) {
            if (vote.getId().equals(option.getId())) {
                optionRadio.setChecked(true);
            }
        }

        if (!myEvent.getOrganizerId().equals(MainActivity.account.getId())) {
            optionRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    option.setSelected(b);
                    myVotes.add(option);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myOptions.clear();
                            notifyDataSetChanged();

                            votingOptions
                                    .whereEqualTo("eventId", myEvent.getId())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot doc: task.getResult()) {
                                                    if (Integer.parseInt(doc.getData().get("typeId").toString()) == option.getTypeId()) {
                                                        Map<String, Object> data = new HashMap<>();

                                                        data.put("eventId", option.getEventId());
                                                        data.put("typeId", option.getTypeId());
                                                        data.put("text", option.getText());
                                                        data.put("votes", Integer.parseInt(doc.getData().get("votes").toString()) + 1);

                                                        votingOptions
                                                                .document(doc.getId())
                                                                .set(data)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

                                                                    }
                                                                });
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }
                    }, 700);
                }
            });
        } else {
            optionRadio.setEnabled(false);
        }

        if (!myEvent.getOrganizerId().equals(MainActivity.account.getId())) {
            votesText.setVisibility(View.GONE);
        } else {
            votesText.setText("Ψήφοι: " + option.getVotes());
        }

        return convertView;
    }

    public void setOptions(List<VotingOption> options) {
        this.myOptions.clear();

        for (VotingOption option : options) {
            if (option.isSelected()) {
                this.myOptions.add(option);
            }
        }
    }

    public void addOption(VotingOption option) {
        this.myOptions.add(option);
    }

    public Event getMyEvent() {
        return myEvent;
    }

    public boolean isOptionSelected(int typeId) {
        for (VotingOption option: myOptions) {
            if (option.getTypeId() == typeId && option.isSelected()) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        this.myOptions.clear();
    }

    public void resetDisableAll() {
        for (VotingOption vote: myVotes) {
            for (VotingOption option: myOptions)
                if (vote.getId().equals(option.getId())) {
                    this.disableAll = true;
                }
        }
    }
}
