package ca.T3.fab4.it.smart.home.controller;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

public class RegistrationActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    private EditText email_id;
    private EditText user_name;
    private EditText pass_word;
    private EditText confirmPassword;
    private EditText phone_number;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        email_id = (EditText) findViewById(R.id.emailAddress);
        user_name =  (EditText) findViewById(R.id.username);
        pass_word = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        phone_number = (EditText) findViewById(R.id.phoneNumber);
        signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_id.getText().toString();
                String password = confirmPassword.getText().toString().trim();
                String username = user_name.getText().toString().trim();
                String phoneNumber = phone_number.getText().toString().trim();

                if (username.isEmpty()){
                user_name.setError("Full name is required");
                user_name.requestFocus();
                return;
                }
                if (phoneNumber.isEmpty()){
                    user_name.setError("Phone Number is required");
                    user_name.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    email_id.setError("Please provide a valid email");
                    email_id.requestFocus();
                    return;
                }
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    email_id.setError("Please provide a valid email");
                    email_id.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pass_word.setError("Enter a password");
                    pass_word.requestFocus();
                    return;
                }
                if (password.length() < 6){
                    pass_word.setError("Min password length should be more than 6 characters!");
                    pass_word.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    //if user is registered successfully
                                    T3UserInfoDatabase user = new T3UserInfoDatabase(username,email,phoneNumber);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(RegistrationActivity.this, "user has been registered successfully", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        //finish();
                                                    }else {
                                                        Toast.makeText(RegistrationActivity.this, "user was not registered", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                } else {
                                    // If registration fails, display a message to the user.
                                    Toast.makeText(RegistrationActivity.this, "Registration Failed.",
                                            Toast.LENGTH_LONG).show();

                                }

                            }


                        });
            }
        });
    }

    //On action bar back arrow pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}