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

import com.aueb.glass.MainActivity;
import com.aueb.glass.R;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.VotingOption;
import com.google.firebase.firestore.CollectionReference;

import java.util.List;
import java.util.Timer;

public class VotesListAdapter extends BaseAdapter {

    private Context context;
    private Event myEvent;
    private List<VotingOption> myOptions;
    private CollectionReference votingOptions;

    public VotesListAdapter(Context context, CollectionReference votingOptions, Event myEvent, List<VotingOption> myOptions) {
        this.context = context;
        this.votingOptions = votingOptions;
        this.myEvent = myEvent;
        this.myOptions = myOptions;
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

        if (!myEvent.getOrganizerId().equals(MainActivity.account.getId())) {
            optionRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    option.setSelected(b);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myOptions.clear();
                            notifyDataSetChanged();

                            ////TODO να στελνει ειδοποιηση
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
            votesText.setText("Ψήφοι: 0");
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
}
