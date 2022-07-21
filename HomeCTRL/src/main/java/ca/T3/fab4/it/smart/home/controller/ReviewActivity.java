package ca.T3.fab4.it.smart.home.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewActivity extends AppCompatActivity {

    private EditText name, phone, email, comment;   //variables to hold the user info

    Button submit;  //variable to hold the button object

    FirebaseDatabase firebaseDatabase; //firebase database object

    DatabaseReference databaseReference;//firebase database reference

    T3UserInfo userInfo;//User info class object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        //initializing user info variables to the edit text values
        name = findViewById(R.id.review_name);
        phone = findViewById(R.id.review_phone);
        email = findViewById(R.id.review_email);
        comment = findViewById(R.id.review_comment);

        submit = findViewById(R.id.review_submit);// initializing the submit variable to the xml button

        //get an instance of firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        //get reference for firebase
        databaseReference = firebaseDatabase.getReference("EmployeeInfo");
        //user info class object initialization
        userInfo = new T3UserInfo();

    }
}