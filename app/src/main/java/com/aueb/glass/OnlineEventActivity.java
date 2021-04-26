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

import com.aueb.glass.fragments.VotingOptionsFragment;
import com.aueb.glass.models.Event;
import com.aueb.glass.models.VotingOption;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OnlineEventActivity extends AppCompatActivity {

    private WebView youtubeVideo;
    private ListView optionsList;
    private MaterialButton optionsButton;

    private FragmentManager fragmentManager;
    private FrameLayout onlineEventFrameLayout;

    private Event myEvent;
    private List<VotingOption> myOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_event);

        fragmentManager = getSupportFragmentManager();
        onlineEventFrameLayout = findViewById(R.id.onlineEventFrameLayout);

        myOptions = new ArrayList<>();
        String[] allOptions = getResources().getStringArray(R.array.availableOptions);

        for (int i = 0; i < allOptions.length; i++) {
            myOptions.add(new VotingOption(allOptions[i], i + 1, false));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        myEvent = intent.getParcelableExtra("event");

        setTitle(myEvent.getName());

        youtubeVideo = findViewById(R.id.youtubeVideo);
        optionsList = findViewById(R.id.optionsList);
        optionsButton = findViewById(R.id.optionsButton);

        if (myEvent.getUrl() != null && !myEvent.getUrl().isEmpty()) {
            WebSettings webSettings = youtubeVideo.getSettings();
            webSettings.setJavaScriptEnabled(true);

            youtubeVideo.getSettings().setLoadWithOverviewMode(true);
            youtubeVideo.getSettings().setUseWideViewPort(true);

            youtubeVideo.loadData("<iframe width=\"100%\" height=\"600px\" src=\"" + myEvent.getUrl() +"\" frameborder=\"0\"  allowfullscreen/>", "text/html", "utf-8");
        }

        if (myEvent.getOrganizerId().equals(MainActivity.account.getId())) {
            optionsButton.setVisibility(View.VISIBLE);
            optionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VotingOptionsFragment votingOptionsFragment = new VotingOptionsFragment(myOptions);
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