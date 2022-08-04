/*
Abdulrhman Ragab    n01440938    0NA
Tanushree Ray    n01440938    0NA
Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class SplashActivity extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashfile);

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
