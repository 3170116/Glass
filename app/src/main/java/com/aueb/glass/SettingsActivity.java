package com.aueb.glass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aueb.glass.fragments.LoginFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial organizer;
    private MaterialCardView companyInfo;
    private TextInputEditText companyName;
    private EditText companyDescription;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Settings");
    }

    @Override
    protected void onStart() {
        super.onStart();

        sharedPreferences = getSharedPreferences(MainActivity.account.getId(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        companyInfo = findViewById(R.id.companyInfo);

        organizer = findViewById(R.id.organizerSwitch);
        organizer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    organizer.setText("I am an organizer");
                    companyInfo.setVisibility(View.VISIBLE);
                } else {
                    organizer.setText("I am not an organizer");
                    companyInfo.setVisibility(View.GONE);
                }
            }
        });

        companyName = findViewById(R.id.companyName);
        companyDescription = findViewById(R.id.companyDescription);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPreferences.getBoolean("IsOrganizer", false) == false) {
            organizer.setChecked(false);
            companyInfo.setVisibility(View.GONE);
        } else {
            organizer.setChecked(true);
            companyInfo.setVisibility(View.VISIBLE);

            companyName.setText(sharedPreferences.getString("CompanyName",""));
            companyDescription.setText(sharedPreferences.getString("CompanyDescription",""));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public void logout(View view) {
        AuthUI.getInstance()
                .signOut(getApplicationContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        MainActivity.FragmentIndex = 1;
                        finish();
                    }
                });
    }

    public void saveSettings(View view) {
        editor.putBoolean("IsOrganizer", organizer.isChecked());

        if (organizer.isChecked()) {
            editor.putString("CompanyName", companyName.getText() + "");
            editor.putString("CompanyDescription", companyDescription.getText() + "");
        } else {
            editor.putString("CompanyName", "");
            editor.putString("CompanyDescription", "");
        }

        editor.apply();

        Toast.makeText(getApplicationContext(), "Changes have been saved!", Toast.LENGTH_SHORT).show();

        finish();
    }
}
