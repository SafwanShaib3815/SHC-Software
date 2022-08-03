package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ReviewActivity extends AppCompatActivity {

    private EditText name, phone, email, comment;   //variables to hold the user info
    private RatingBar ratingBar; //variable to hold the rating bar object
    float rating; //variable to hold the user's rating
    String nameStr, phoneStr, emailStr, commentStr; //variables to hold the user info as a stringb

    Button submit;  //variable to hold the button object
    TextView modelTv; //variable to hold the "user phone model" text view object
    String model = Build.MODEL; //gets the user's device Model

    //firebase database objects
    FirebaseDatabase fbDatabase;
    FirebaseFirestore fbFireStore;
    ProgressBar progressBar;
    int count = 0;

    DatabaseReference databaseReference;//firebase database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //********* Action Bar *********
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(getColor(R.color.brown));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        //********* Action Bar *********
        progressBar = findViewById(R.id.t3PB);
        progressBar.setVisibility(View.INVISIBLE);
        //initializing user info variables to the edit text values
        name = findViewById(R.id.review_name);
        phone = findViewById(R.id.review_phone);
        email = findViewById(R.id.review_email);
        comment = findViewById(R.id.review_comment);
        submit = findViewById(R.id.review_submit); //initializing the submit variable to the xml button
        modelTv = findViewById(R.id.review_model_TV); //initializing the modelTv variable to the xml textview
        ratingBar = findViewById(R.id.T3_ratingBar);
        //concatenating the text model view value with the device model as a string
        String concat = getResources().getString(R.string.model_review) + " " + model;
        //setting the model textview valu with the resultant string (this way allows for multiple languages)
        modelTv.setText(concat);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //converting user info fields' values into a string and saving them in string variables
                nameStr = name.getText().toString();
                phoneStr = phone.getText().toString();
                emailStr = email.getText().toString();
                commentStr = comment.getText().toString();
                rating = ratingBar.getRating(); //assigning the rating float value to the rating variable


                if(nameStr.length()==0 || phoneStr.length()==0 || emailStr.length()==0 || commentStr.length()==0 || rating == 0.0 ) {
                Toast.makeText(getApplicationContext(), "Please fill all fields above", Toast.LENGTH_LONG).show();
                }
                else {
                    new AsyncTaskExample().execute();
                    //writing user info to the database
                    sendToDb(nameStr, phoneStr, emailStr, commentStr, rating);
                    name.setText("");
                    phone.setText("");
                    email.setText("");
                    comment.setText("");
                    ratingBar.setRating(0.0f);
                }

            }
        });


    }


    private void sendToDb(String name, String phone, String email, String comment, float rating) {

        //fbDatabase = FirebaseDatabase.getInstance(); //get an instance of firebase
        //databaseReference = fbDatabase.getReference("User"+usrCounter); //get reference for firebase

        fbFireStore = FirebaseFirestore.getInstance(); //get instance of firebase firestore

        // Create a new user info k/v data table
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Phone", phone);
        user.put("Email", email);
        user.put("Comment", comment);
        user.put("Rating", rating);
        user.put("Device model", model);


        //Search database for the user's phone number
        //if found display a toast user exists
        //if not add the new review to the database
        fbFireStore.collection("Users").whereEqualTo("Phone", phone)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot query = task.getResult();
                            //When the query result is empty, the phone number doesn't exist in the database
                            if (query.isEmpty()) {
                                // Add a new document with ID of parameter "name" in the collection "Users"
                                fbFireStore.collection("Users").document(name)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast toast1 = Toast.makeText(getApplicationContext(), R.string.userreview_added_to_db, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                                                toast1.show();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast toast2 = Toast.makeText(getApplicationContext(), R.string.error_submitting_review, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                                                toast2.show();
                                            }
                                        });
                            } else {
                                //if the query is not empty then phone number has already been used
                                Toast toast3 = Toast.makeText(getApplicationContext(), R.string.user_exists, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                                toast3.show();
                            }
                        } else {
                            Toast toast4 = Toast.makeText(getApplicationContext(), R.string.db_error, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                            toast4.show();
                        }
                    }
                });
    }

    class AsyncTaskExample extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... arg0) {

            for(int i =0; i<100; i++){
                publishProgress(i);
                try{
                    Thread.sleep(100);
                }catch(InterruptedException e){
                  e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setVisibility(View.VISIBLE);

        }
        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
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