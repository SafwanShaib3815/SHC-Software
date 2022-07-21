package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReviewActivity extends AppCompatActivity {

    private EditText name, phone, email, comment;   //variables to hold the user info
    String nameStr, phoneStr, emailStr, commentStr; //variables to hold the user info as a stringb
    int usrCounter = 1; //Used to create more than one user in the database

    Button submit;  //variable to hold the button object
    TextView modelTv; //variable to hold the "user phone model" text view object
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

        submit = findViewById(R.id.review_submit); //initializing the submit variable to the xml button
        modelTv = findViewById(R.id.review_model_TV); //initializing the modelTv variable to the xml textview


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //converting user info fields' values into a string and saving them in string variables
                nameStr = name.getText().toString();
                phoneStr = phone.getText().toString();
                emailStr = email.getText().toString();
                commentStr = comment.getText().toString();


                //writing user info to the database
                sendToDb(nameStr, phoneStr, emailStr, commentStr);

            }
        });
    }


    private void sendToDb(String name, String phone, String email, String comment) {


        firebaseDatabase = FirebaseDatabase.getInstance(); //get an instance of firebase
        databaseReference = firebaseDatabase.getReference("User"+usrCounter); //get reference for firebase
        usrCounter++;

        userInfo = new T3UserInfo(); //user info class object initialization

        //setting data in the user info class
        userInfo.setUserName(name);
        userInfo.setUserPhone(phone);
        userInfo.setUserEmail(email);
        userInfo.setUserCmnt(comment);

        // adding value event listener, called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(userInfo); //Setting user info object class to the data base reference
                Toast.makeText(getApplicationContext(), R.string.user_saved, Toast.LENGTH_SHORT).show(); // displayed on success
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.save_failed) + error, Toast.LENGTH_SHORT).show();//displayed on cancellation
            }
        });
    }




}