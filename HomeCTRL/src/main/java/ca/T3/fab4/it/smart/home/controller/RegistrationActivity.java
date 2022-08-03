package ca.T3.fab4.it.smart.home.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    private EditText email_id;
    private EditText user_name;
    private EditText pass_word;
    private EditText confirmPassword;
    private EditText phone_number;

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
        Button signup = (Button) findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_id.getText().toString().trim();
                String password = pass_word.getText().toString().trim();
                String conPassword = confirmPassword.getText().toString().trim();
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
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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
                if(!password.equals(conPassword)){
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    pass_word.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "User Created!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        }else{
                            Toast.makeText(RegistrationActivity.this, "User not created!", Toast.LENGTH_LONG).show();
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