package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

        //Save the configuration list in an adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getResources().getStringArray(R.array.configlist));

        ListView listView = (ListView) findViewById(R.id.configlist);
        //setAdapter on the ListView object
        listView.setAdapter(adapter);

        //set an onclick listener for the conflist in listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    //get the item selected from the list and save it to a string variable
                    long settingsOption = adapterView.getItemIdAtPosition(position);

                    //Decide actions based on the item selected
                    switch ((int) settingsOption){
                        case 0:  // "App Settings" option
                            intent = new Intent(getApplicationContext(),AppSettingsActivity.class);
                            startActivity(intent);
                            break;
                        case 1:  // "System Settings" option
                            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                            startActivity(intent); //open system settings
                            break;
                        case 2:  // "User Information" option
                            //do something
                            break;
                        case 3:  // "About Device" option
                            //do something
                            break;
                    }

                //Toast.makeText(getApplicationContext(), "Item id: "+ String.valueOf ((int) settingsOption), Toast.LENGTH_SHORT).show();
            }
        });
        //Create a SharedPreference file
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedpref_filename), Context.MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();



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