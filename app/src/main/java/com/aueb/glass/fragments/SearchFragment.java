package com.aueb.glass.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aueb.glass.R;

public class SearchFragment extends Fragment {

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.transactions, R.layout.adapter_dropdown_text);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        AutoCompleteTextView transactions = view.findViewById(R.id.transactionTypeText);
//        transactions.setAdapter(adapter);

        return view;
    }

}
