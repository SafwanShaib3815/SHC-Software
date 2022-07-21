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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class T3MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TemperatureFragment temperatureFragment;
    private MotionFragment motionFragment;
    private SmokeFragment smokeFragment;
    private RFIDFragment rfidFragment;
    private LoginFragment loginFragment;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        bottomNavigationView = findViewById(R.id.BTMNAV);

        temperatureFragment = new TemperatureFragment();
        motionFragment = new MotionFragment();
        smokeFragment = new SmokeFragment();
        rfidFragment = new RFIDFragment();
        loginFragment = new LoginFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.MENU1:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, temperatureFragment).commit();
                    Toast toast3 = Toast.makeText(getApplicationContext(), R.string.temperaturescreen, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                    toast3.show();
                    return true;

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
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, loginFragment).commit();
                    Toast toast4 = Toast.makeText(getApplicationContext(), R.string.loginScreen, Toast.LENGTH_SHORT); // initiate the Toast with context, message and duration for the Toast
                    toast4.show();
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




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.T3_menu_calls:
                if (ContextCompat.checkSelfPermission(T3MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CODE_ASK_PERMISSIONS);

                }
                makePhoneCall();
                break;
            case R.id.T3_menu_help:
                Snackbar.make(findViewById(android.R.id.content), R.string.help, Snackbar.LENGTH_LONG)
                        .show();
                break;

            case R.id.T3_menu_support:
                Snackbar.make(findViewById(android.R.id.content), R.string.support, Snackbar.LENGTH_LONG)
                        .show();
                break;
            case R.id.T3_menu_settings:
                Intent intent1 = new Intent(T3MainActivity.this,
                        SettingsActivity.class);
                startActivity(intent1);
                break;
            case R.id.T3_menu_review:
                Intent intent2 = new Intent(T3MainActivity.this,
                        ReviewActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_allow, Snackbar.LENGTH_LONG)
                            .show();
                    makePhoneCall();
                } else {
                    // Permission Denied
                    Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_deny, Snackbar.LENGTH_LONG)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void makePhoneCall() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse(getString(R.string.phone_number)));

            startActivity(intent);
        }catch (Exception e) {

            e.printStackTrace();
        }
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.website_icon)
                        .setContentTitle(getString(R.string.title))
                        .setContentText(getString(R.string.notification));

        Intent notificationIntent = new Intent(this, T3MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
