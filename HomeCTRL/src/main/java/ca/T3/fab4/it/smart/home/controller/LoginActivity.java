package ca.T3.fab4.it.smart.home.controller;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    private ImageView googleIcon;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])"
                    //+"(?=.*[A-Z])"
//            "(?=.*[a-zA-Z])" +
//            "(?=.*[@#$%^&=])" +
//            ".{8,20}" +
//            "$"
            );
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.pref_label), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText editTextUserName = findViewById(R.id.login_username);
        EditText editTextPassword = findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();
        Button register = findViewById(R.id.button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        Button btn = findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                Boolean validate = true;
                email = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();

                if (email.isEmpty()) {
                    editTextUserName.setError("User Name Field cannot be Empty !!");
                    editTextUserName.requestFocus();
                    validate = false;
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextUserName.setError("A valid user name is required!!");
                    editTextUserName.requestFocus();
                    validate = false;
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password Field Can't be Empty!!");
                    validate = false;
                    editTextPassword.requestFocus();
                    return;
                }
               /* else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    editTextPassword.setError("Too Week Password!!");
                    editTextPassword.requestFocus();
                    validate = false;
                    return;


                } */else {
                    editTextPassword.setError(null);
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });


        CheckBox chk = findViewById(R.id.checkBox);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();

                if (!checked) {
                    editTextUserName.setText("");
                    editTextPassword.setText("");
                    editor.putBoolean("bool1", false);
                } else {
                    editor.putBoolean("bool1", true);
                    editor.putString(getString(R.string.u_name), editTextUserName.getText().toString());
                    editor.putString(getString(R.string.pass), editTextPassword.getText().toString());
                    editor.commit();

                }
            }
        });

        chk.setChecked(sharedPreferences.getBoolean(getString(R.string.check), true));
        editTextUserName.setText(sharedPreferences.getString(getString(R.string.u_name), getString(R.string.blank1)));
        editTextPassword.setText(sharedPreferences.getString(getString(R.string.pass), getString(R.string.blank2)));

        googleIcon = (ImageView) findViewById(R.id.imageView3);
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = (GoogleSignInAccount)task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (Throwable e) {
                startActivity(new Intent(LoginActivity.this, T3MainActivity.class));
                //Toast.makeText(LoginActivity.this,"Google Sign In Failed!!!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);


                        } else {
                            Toast.makeText(LoginActivity.this,"Google Sign In Failed!!!", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }
    public void updateUI(FirebaseUser currentUser){

        Intent profileIntent = new Intent(LoginActivity.this, T3MainActivity.class);
        profileIntent.putExtra("email",currentUser.getEmail());
        startActivity(profileIntent);

    }


}
