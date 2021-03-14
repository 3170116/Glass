package com.aueb.glass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.aueb.glass.fragments.DashboardAccountFragment;
import com.aueb.glass.fragments.LoginFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 17;

    public static int FragmentIndex;

    public static FirebaseAuth mAuth;
    public static GoogleSignInOptions gso;
    public static GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInAccount account;

    public static FragmentManager fragmentManager;

    private Menu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize Firebase App
        FirebaseApp.initializeApp(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        fragmentManager = getFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account != null) {
            DashboardAccountFragment dashboardAccountFragment = new DashboardAccountFragment();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, dashboardAccountFragment).commit();

            if (myMenu != null) {
                myMenu.findItem(R.id.more).setVisible(true);
            }
        } else {
            LoginFragment loginFragment = new LoginFragment();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, loginFragment).commit();

            if (myMenu != null) {
                myMenu.findItem(R.id.more).setVisible(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_main_menu, menu);

        myMenu = menu;

        if (account == null) {
            menu.findItem(R.id.more).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("Account ID", "Google sign in failed", e);
            }
        }
    }


//    public void submitTransaction(View view) {
//        TextInputLayout orderIdLayout = findViewById(R.id.orderIdTextLayout);
//        TextInputEditText orderId = findViewById(R.id.orderIdText);
//
//        TextInputLayout transactionTypeLayout = findViewById(R.id.transactionTypeLayout);
//        AutoCompleteTextView transactionType = findViewById(R.id.transactionTypeText);
//
//        if (orderId.getText() == null || orderId.getText().length() == 0) {
//            orderIdLayout.setHelperText("Required *");
//        } else {
//            orderIdLayout.setHelperText("");
//        }
//
//        if (transactionType.getText() == null || transactionType.getText().length() == 0) {
//            transactionTypeLayout.setHelperText("Please select a type *");
//        } else {
//            transactionTypeLayout.setHelperText("");
//        }
//    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DashboardAccountFragment dashboardAccountFragment = new DashboardAccountFragment();
                            fragmentManager.beginTransaction().replace(R.id.frameLayout, dashboardAccountFragment).commit();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Result", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
}