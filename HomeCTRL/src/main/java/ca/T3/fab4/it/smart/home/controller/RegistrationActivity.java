package ca.T3.fab4.it.smart.home.controller;

import static android.content.ContentValues.TAG;
import static java.util.regex.Pattern.compile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText fullName, email, password, confirmPassword,phone;
    private static final Pattern PASSWORD_PATTERN =
            compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.*[@#$%^&=])" +
                    ".{8,}" +
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        fullName = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.password2);

        Button register = findViewById(R.id.sign);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = fullName.getText().toString().trim();
                String uPhone = phone.getText().toString().trim();
                String uEmail = email.getText().toString().trim();
                String uPassword = password.getText().toString().trim();
                String cPassword = confirmPassword.getText().toString().trim();

                if (uName.isEmpty()) {
                    fullName.setError("Name Field Can't be Blank!!");
                    fullName.requestFocus();
                    return;
                }

                if (uPhone.isEmpty()) {
                    phone.setError("Phone field can't be empty!!");
                    phone.requestFocus();
                    return;


                }
                if (uEmail.isEmpty()) {
                    email.setError("Email field can't be empty!!");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
                    email.setError("Enter a valid email address!!");
                    email.requestFocus();
                    return;

                }
                if (uPassword.isEmpty()) {
                    password.setError("Phone field can't be empty!!");
                    password.requestFocus();
                    return;
                } else if (!PASSWORD_PATTERN.matcher(uPassword).matches()) {
                    password.setError("Password should have : " + "\n" +
                            "At least one digit " + "\n" + "At least one upper case letter " + "\n"
                            + "At least one special charecter ");
                    password.requestFocus();
                    return;

                } else {
                    password.setError(null);


                }
                if (cPassword.isEmpty()) {
                    confirmPassword.setError("Confirm password can't be empty!!");
                    confirmPassword.requestFocus();
                    return;
                }
                if (!uPassword.equals(cPassword)) {
                    confirmPassword.setError("Confirm password should match with the password field!!");
                    confirmPassword.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(uEmail, uPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    //if user is registered successfully
                                    T3UserInfoDatabase user = new T3UserInfoDatabase();
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(RegistrationActivity.this, "user has been registered successfully", Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        //finish();
                                                    } else {
                                                        Toast.makeText(RegistrationActivity.this, "user was not registered", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                } else {
                                    // If registration fails, display a message to the user.
                                    Toast.makeText(RegistrationActivity.this, "Registration Failed",
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



