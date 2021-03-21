package com.aueb.glass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.aueb.glass.R;
import com.aueb.glass.models.VotingOption;

import java.util.List;

public class OptionsListAdapter extends BaseAdapter {

    private Context context;
    private List<VotingOption> myOptions;

    public OptionsListAdapter(Context context, List<VotingOption> myOptions) {
        this.context = context;
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
        convertView = LayoutInflater.from(context).inflate(R.layout.row_option, parent, false);

        // get current item to be displayed
        VotingOption option = (VotingOption) getItem(position);

        CheckBox optionCheck = convertView.findViewById(R.id.optionCheck);

        optionCheck.setText(option.getText());
        optionCheck.setChecked(option.isSelected());

        optionCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                option.setSelected(b);
            }
        });

        return convertView;
    }
}
