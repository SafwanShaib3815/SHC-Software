package ca.T3.fab4.it.smart.home.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel("My Channel", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String CHANNEL_ID = "My Notification";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_msg)
                        .setContentTitle("My notification")
                        .setContentText("Some Unauthorised Activity Encountered")
                        .setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationActivity.this);
                managerCompat.notify(1, builder.build());

            }
        });

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
