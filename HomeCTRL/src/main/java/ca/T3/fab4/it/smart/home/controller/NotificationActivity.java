package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView notificationLabel = findViewById(R.id.textView7);
        notificationLabel.setText("Don't Miss the Notification!!!");

        Button btn = findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNotification();

            }
        });

    }

    private void addNotification() {
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= 26)
        {
            //When sdk version is larger than26
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
//                     channel.enableLights(true);
//                     channel.enableVibration(true);//
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(NotificationActivity.this, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.drawable.ic_msg)
                    .setContentTitle("New Alert")
                    .setContentText("Some unauthorised activity encountered. Action Requited!!!!")
                    //.setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, notification);
        }
        else
        {
            //When sdk version is less than26
            Notification notification = new NotificationCompat.Builder(NotificationActivity.this)
                    .setContentTitle("New Alert")
                    .setContentText("Some unauthorised activity encountered. Action Requited!!!!")
                    //.setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            manager.notify(1,notification);
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
