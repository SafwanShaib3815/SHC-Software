/*
Abdulrhman Ragab    n01440938    0NA
Tanushree Ray    n01440938    0NA
Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.util.Date;

public class SplashActivity extends Activity {

    Handler handler;
    ConstraintLayout constraintlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashfile);
        constraintlayout = findViewById(R.id.constraintlayout);
        SharedPreferences sharedPref = this.getSharedPreferences("SettingsPref", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.pref_label), Context.MODE_PRIVATE);

        TextView dateDisplay= findViewById(R.id.textView15);
        TextClock clock = findViewById(R.id.tanushreeTC);
       // TextView receive = findViewById(R.id.textView16);
//        receive.setText(getString(R.string.msg1) + getString(R.string.space1)
        //           + sharedPreferences.getString(getString(R.string.u_name), getString(R.string.blank1))
        //        + getString(R.string.space2) + getString(R.string.msg2));

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

//        if (sharedPref.getString("Color", "").equalsIgnoreCase("Dark")) {
//
//            constraintlayout.setBackgroundColor(Color.BLUE);
//
//        } else {
//
//            constraintlayout.setBackgroundColor(Color.CYAN);
//        }

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
