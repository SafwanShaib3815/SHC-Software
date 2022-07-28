package ca.T3.fab4.it.smart.home.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;


public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient gsc;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.pref_label), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();// Build a GoogleSignInClient with the options specified by gso.
//
//        gsc = GoogleSignIn.getClient(this, gso);
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        EditText editTextUserName = findViewById(R.id.login_username);
        EditText editTextPassword = findViewById(R.id.editText2);

        Button btn = findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = null, password = null;

                userName = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();

                if (userName.isEmpty()) {
                    editTextUserName.setError(getString(R.string.ET_Validation1));
                } else if (userName.length() < 3) {

                    editTextUserName.setError(getString(R.string.ET_Validation2));

                } else if (userName.matches(getString(R.string.check_numeric))) {
                    editTextUserName.setError(getString(R.string.ET_validation3));

                }

                if (password.isEmpty()) {
                    editTextPassword.setError(getString(R.string.pass_validation1));
                } else if (password.length() < 5) {

                    editTextPassword.setError(getString(R.string.pass_validation2));

                }

                editor.putString(getString(R.string.u_name), userName);
                editor.putString(getString(R.string.pass), password);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, T3MainActivity.class);
                startActivity(intent);

            }
        });


        CheckBox chk = findViewById(R.id.checkBox);
        chk.setChecked(sharedPreferences.getBoolean(getString(R.string.check), true));
        editTextUserName.setText(sharedPreferences.getString(getString(R.string.u_name), getString(R.string.blank1)));
        editTextPassword.setText(sharedPreferences.getString(getString(R.string.pass), getString(R.string.blank2)));

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS) {
                    Log.d("Message", "availabale******************");
                } else {
                    Log.d("Message","unavailabale******************");
                }
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                if (acct != null) {
                    String personName = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    String personFamilyName = acct.getFamilyName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();
                    Uri personPhoto = acct.getPhotoUrl();

                    Toast.makeText(this,"User email : " +personEmail, Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(LoginActivity.this, T3MainActivity.class));

            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                Log.d("Message", e.toString());
              }

            }
        }



