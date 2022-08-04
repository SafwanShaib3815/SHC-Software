package ca.T3.fab4.it.smart.home.controller;

import static java.util.regex.Pattern.compile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

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
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.password2);

        Button register = findViewById(R.id.sign);
        register.setOnClickListener(v -> {
            String uName = fullName.getText().toString().trim();
            String uPhone = phone.getText().toString().trim();
            String uEmail = email.getText().toString().trim();
            String uPassword = password.getText().toString().trim();
            String cPassword = confirmPassword.getText().toString().trim();

            if (uName.isEmpty()) {
                fullName.setError(getString(R.string.reg_name_error));
                fullName.requestFocus();
                return;
            }

            if (uPhone.isEmpty()) {
                phone.setError(getString(R.string.reg_phone_error));
                phone.requestFocus();
                return;


            }
            if (uEmail.isEmpty()) {
                email.setError(getString(R.string.reg_email_error));
                email.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
                email.setError(getString(R.string.reg_email_error1));
                email.requestFocus();
                return;

            }
            if (uPassword.isEmpty()) {
                password.setError(getString(R.string.reg_phone_error1));
                password.requestFocus();
                return;
            }
            else if (!PASSWORD_PATTERN.matcher(uPassword).matches()){
                password.setError(getString(R.string.password_should_have) + "\n" +
                        getString(R.string.at_least_one_digit) + "\n" + getString(R.string.one_upper_case) + "\n"
                        + getString(R.string.one_special_char));
                password.requestFocus();
                return;

            } else {
                password.setError(null);


            }
            if (cPassword.isEmpty()) {
                confirmPassword.setError(getString(R.string.reg_conf_pswd_error));
                confirmPassword.requestFocus();
                return;
            }
            if (!uPassword.equals(cPassword)) {
                confirmPassword.setError(getString(R.string.reg_conf_pswd_error1));
                confirmPassword.requestFocus();
            }

        });

    }

    //On action bar back arrow pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==  android.R.id.home){
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}