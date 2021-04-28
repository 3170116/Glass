package com.aueb.glass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial organizer;
    private MaterialCardView companyInfo;
    private TextInputEditText companyName;
    private EditText companyDescription;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private CollectionReference organizers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Ρυθμίσεις");
    }

    @Override
    protected void onStart() {
        super.onStart();

        sharedPreferences = getSharedPreferences(MainActivity.account.getId(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        organizers = MainActivity.firebaseFirestore.collection("Organizers");

        companyInfo = findViewById(R.id.companyInfo);

        organizer = findViewById(R.id.organizerSwitch);
        organizer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    organizer.setText("Είμαι οργανωτής");
                    companyInfo.setVisibility(View.VISIBLE);
                } else {
                    organizer.setText("Δεν είμαι οργανωτής");
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

        if (organizer.isChecked()) {
            organizers
                    .whereEqualTo("email", MainActivity.account.getEmail())
                    .limit(1)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().size() == 1) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        saveOrganizer(document);
                                        Toast.makeText(getApplicationContext(), "Οι αλλαγές αποθηκεύτηκαν!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                } else {
                                    createOrganizer();
                                }
                            } else {
                                Log.e("ORG", "Error getting documents: ", task.getException());
                                Toast.makeText(getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

        } else {
            finish();
        }
    }


    private void saveOrganizer(QueryDocumentSnapshot organizer) {
        Map<String, Object> data = organizer.getData();

        data.put("fullName", MainActivity.sharedPreferences.getString("FullName", ""));
        data.put("phone", MainActivity.sharedPreferences.getString("Phone", ""));
        data.put("companyName", companyName.getText() + "");
        data.put("companyDescription", companyDescription.getText() + "");

        organizers.document(organizer.getId()).set(data);
    }

    private void createOrganizer() {
        Map<String, Object> data = new HashMap<>();

        data.put("email", MainActivity.account.getEmail());
        data.put("fullName", MainActivity.sharedPreferences.getString("FullName", ""));
        data.put("phone", MainActivity.sharedPreferences.getString("Phone", ""));
        data.put("companyName", companyName.getText() + "");
        data.put("companyDescription", companyDescription.getText() + "");

        organizers
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Οι αλλαγές αποθηκεύτηκαν!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Κάτι πήγε στραβά...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
