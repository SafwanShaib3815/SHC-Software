/*
Abdulrhman Ragab    n01440938    0NA
Tanushree Ray    n01440938    0NA
Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.Fragment;

import android.Manifest;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Date;

public class T3MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TemperatureFragment temperatureFragment;
    private MotionFragment motionFragment;
    private SmokeFragment smokeFragment;
    private RFIDFragment rfidFragment;
    ConstraintLayout constraintlayout;

    public FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintlayout = findViewById(R.id.constraintlayout);

        mAuth = FirebaseAuth.getInstance();

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its paramete
        ColorDrawable colorDrawable
                = new ColorDrawable(getColor(R.color.green));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        SharedPreferences sharedPref = this.getSharedPreferences("SettingsPref", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.pref_label), Context.MODE_PRIVATE);

        TextView dateDisplay= findViewById(R.id.textView15);
        TextClock clock = findViewById(R.id.tanushreeTC);
        TextView receive = findViewById(R.id.textView16);
        receive.setText(getString(R.string.msg1) + getString(R.string.space1)
                + sharedPreferences.getString(getString(R.string.u_name), getString(R.string.blank1))
                + getString(R.string.space2) + getString(R.string.msg2));

        String currentDateString = DateFormat.getDateInstance().format(new Date());
       // textView is the TextView view that should display it
       dateDisplay.setText(currentDateString);
       if(sharedPref.getString("Time","").equalsIgnoreCase("12hrs"))
        {
           clock.setFormat12Hour("hh:mm:ss a");
        } else {
            CharSequence x = clock.getFormat24Hour();
            clock.setFormat24Hour(x);
       }

        if (sharedPref.getString("Color", "").equalsIgnoreCase("Dark")) {

            constraintlayout.setBackgroundColor(Color.BLUE);

        } else {

            constraintlayout.setBackgroundColor(Color.CYAN);
        }

        bottomNavigationView = findViewById(R.id.BTMNAV);

        temperatureFragment = new TemperatureFragment();
        motionFragment = new MotionFragment();
        smokeFragment = new SmokeFragment();
        rfidFragment = new RFIDFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.MENU2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, motionFragment).commit();
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.motionscreen, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                    toast.show();
                    return true;

                case R.id.MENU3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, smokeFragment).commit();
                    Toast toast1 = Toast.makeText(getApplicationContext(), R.string.smokescreen, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                    toast1.show();
                    return true;

                case R.id.MENU4:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, rfidFragment).commit();
                    Toast toast2 = Toast.makeText(getApplicationContext(), R.string.rfidscreen, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                    toast2.show();
                    return true;

                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, temperatureFragment).commit();
                    Toast toast3 = Toast.makeText(getApplicationContext(), R.string.temperaturescreen, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                    toast3.show();
                    return true;

            }

        });
    }



    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title)
                .setMessage(R.string.alert_msg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setIcon(R.drawable.exit_icon)
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Code for the right upper corner menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
         //HelpAndSupportFragment hlpSupportFrag;
        //decide what to do based on what option is selected
        switch (item.getItemId()) {
            case R.id.T3_menu_help:
                Snackbar.make(findViewById(android.R.id.content), R.string.help, Snackbar.LENGTH_LONG)
                        .show();
                getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, new HelpAndSupportFragment()).commit();

                break;

            case R.id.T3_menu_home:
                Intent intent1 = new Intent(T3MainActivity.this,
                        T3MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.T3_menu_notification:
                Intent intent2 = new Intent(T3MainActivity.this,
                        NotificationActivity.class);
                startActivity(intent2);
                break;
            case R.id.T3_menu_settings:
                Snackbar.make(findViewById(android.R.id.content), R.string.str1, Snackbar.LENGTH_LONG)
                        .show();
                getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, new SettingsFragment()).commit();
                break;
            case R.id.T3_menu_review:
                Intent intent3 = new Intent(T3MainActivity.this,
                        ReviewActivity.class);
                startActivity(intent3);
                break;

            case R.id.T3_menu_logout:
                Snackbar.make(findViewById(android.R.id.content), "Logging In!!!", Snackbar.LENGTH_LONG)
                        .show();

                mAuth.signOut();
                Intent intent4 = new Intent(T3MainActivity.this, LoginActivity.class);
                startActivity(intent4);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
