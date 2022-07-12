package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

public class AppSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        //Define action bar object
        ActionBar actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        ColorDrawable colorDrawable = new ColorDrawable(getColor(R.color.brown));

        //set actionbar background color to dark_grey
        actionBar.setBackgroundDrawable(colorDrawable);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


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