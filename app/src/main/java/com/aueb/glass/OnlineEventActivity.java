package com.aueb.glass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import com.aueb.glass.adapters.VotesListAdapter;
import com.aueb.glass.fragments.VotingOptionsFragment;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.VotingOption;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnlineEventActivity extends AppCompatActivity {

    private WebView youtubeVideo;
    private ListView votesList;
    private MaterialButton optionsButton;

    private FragmentManager fragmentManager;
    private FrameLayout onlineEventFrameLayout;

    private Event myEvent;
    private List<VotingOption> myOptions;
    private CollectionReference votingOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_event);

        fragmentManager = getSupportFragmentManager();
        onlineEventFrameLayout = findViewById(R.id.onlineEventFrameLayout);

        myOptions = new ArrayList<>();
        this.votingOptions = MainActivity.firebaseFirestore.collection("VotingOptions");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        myEvent = intent.getParcelableExtra("event");

        setTitle(myEvent.getName());

        youtubeVideo = findViewById(R.id.youtubeVideo);
        votesList = findViewById(R.id.optionsList);
        optionsButton = findViewById(R.id.optionsButton);


        if (myEvent.getUrl() != null && !myEvent.getUrl().isEmpty()) {
            WebSettings webSettings = youtubeVideo.getSettings();
            webSettings.setJavaScriptEnabled(true);

            youtubeVideo.getSettings().setLoadWithOverviewMode(true);
            youtubeVideo.getSettings().setUseWideViewPort(true);

            youtubeVideo.loadData("<iframe width=\"100%\" height=\"600px\" src=\"" + myEvent.getUrl() +"\" frameborder=\"0\"  allowfullscreen/>", "text/html", "utf-8");
        }


        VotesListAdapter votesListAdapter = new VotesListAdapter(getApplicationContext(), votingOptions, myEvent, new ArrayList<>());
        votesList.setAdapter(votesListAdapter);

        votingOptions
                .whereEqualTo("eventId", myEvent.getId())
                .orderBy("typeId")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc: task.getResult()) {
                                Map<String, Object> data = doc.getData();
                                VotingOption option = new VotingOption();

                                option.setId(doc.getId());
                                option.setEventId(data.get("eventId").toString());
                                option.setTypeId(Integer.parseInt(data.get("typeId").toString()));
                                option.setText(data.get("text").toString());
                                option.setVotes(Integer.parseInt(data.get("votes").toString()));
                                option.setSelected(true);

                                votesListAdapter.addOption(option);
                            }

                            votesListAdapter.notifyDataSetChanged();

                            String[] allOptions = getResources().getStringArray(R.array.availableOptions);

                            for (int i = 0; i < allOptions.length; i++) {
                                VotingOption option = new VotingOption(allOptions[i], i + 1, false);

                                option.setEventId(myEvent.getId());
                                option.setVotes(0);

                                if (votesListAdapter.isOptionSelected(i + 1)) {
                                    option.setSelected(true);
                                }

                                myOptions.add(option);
                            }
                        }
                    }
                });



        if (myEvent.getOrganizerId().equals(MainActivity.account.getId())) {
            optionsButton.setVisibility(View.VISIBLE);
            optionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VotingOptionsFragment votingOptionsFragment = new VotingOptionsFragment(votingOptions, votesListAdapter, myOptions);
                    votingOptionsFragment.show(fragmentManager, "Edit Options");
                }
            });
        }


        myEvent.initStartDate();
        if (!myEvent.hasStarted()) {
            Toast.makeText(getApplicationContext(), "Η εκδήλωση δεν έχει ξεκινήσει ακόμα!", Toast.LENGTH_LONG).show();
        }
    }
}