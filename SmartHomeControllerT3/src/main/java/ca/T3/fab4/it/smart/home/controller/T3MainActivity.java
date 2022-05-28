/*
Abdulrhman Ragab    n01440938    0NA
Tanushree Ray    n01440938    0NA
Safwan Shaib        0NA
Nkeiru Johnson-Achilike        0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class T3MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TemperatureFragment temperatureFragment;
    private MotionFragment motionFragment;
    private SmokeFragment smokeFragment;
    private RFIDFragment rfidFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.BTMNAV);

        temperatureFragment = new TemperatureFragment();
        motionFragment = new MotionFragment();
        smokeFragment = new SmokeFragment();
        rfidFragment = new RFIDFragment();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.MENU2:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, motionFragment).commit();
                    return true;

                case R.id.MENU3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, smokeFragment).commit();
                    return true;

                case R.id.MENU4:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT, rfidFragment).commit();
                    return true;

                default:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FRAGMENT,temperatureFragment).commit();
                    return true;

            }

        });
     }
    }

