package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its paramete
        ColorDrawable colorDrawable
                = new ColorDrawable(getColor(R.color.brown));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Save the configuration list in an adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getResources().getStringArray(R.array.configlist));

        ListView listView = (ListView) findViewById(R.id.configlist);
        //setAdapter on the ListView object
        listView.setAdapter(adapter);

        //Create a SharedPreference file
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpref_filename), Context.MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();


    }

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