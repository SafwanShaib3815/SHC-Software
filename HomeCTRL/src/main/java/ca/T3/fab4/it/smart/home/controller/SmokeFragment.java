/*
Abdulrhman Ragab    n01440938    0NA
Tanushree Ray    n01440938    0NA
Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SmokeFragment extends Fragment {

    String valuee = "";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private View view;
    int img= 1;
    int[] images = {R.mipmap.smokedetected_foreground,R.mipmap.smokeclear_foreground};
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SmokeFragment() {
        // Required empty public constructor
    }

    public static SmokeFragment newInstance(String param1, String param2) {
        SmokeFragment fragment = new SmokeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_smoke, container, false);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference smoke = ref.child("Dummy Sensors").child("Smoke").child("smoke2");
        TextView txtmessage = (TextView) view.findViewById(R.id.smokeretrive);
        ImageView imageView=view.findViewById(R.id.smokeiv1);
        FloatingActionButton floatingActionButton=view.findViewById(R.id.button2);
        final MediaPlayer mediaPlayer= MediaPlayer.create(getActivity(), R.raw.alarm);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("aa", "aa",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        smoke.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valuee = dataSnapshot.getValue(String.class);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),"aa");
                builder.setContentTitle(getString(R.string.smokesensed));
                builder.setContentText("there is a smoke in the house please follow the safety measures and clear the situation");
                builder.setAutoCancel(true);
                builder.setSmallIcon(R.drawable.ic_smoke);

                NotificationManagerCompat managerCompat= NotificationManagerCompat.from(getActivity());
                imageView.setImageResource(images[img]);

                txtmessage.setText(" " + valuee);
                int number = Integer.parseInt(valuee);
                if(number <= 30) {
                    mediaPlayer.setLooping(false);
                    img = 0;
                }
                else if (number >=31){
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    img = 1;
                    managerCompat.notify(1,builder.build());

                }

                }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


       floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CODE_ASK_PERMISSIONS);

                }
                makePhoneCall();

            }
        });
        return view;
    }



        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults){
            switch (requestCode) {
                case REQUEST_CODE_ASK_PERMISSIONS:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission Granted
                        Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.snackbar_allow, Snackbar.LENGTH_LONG)
                                .show();
                        makePhoneCall();
                    } else {
                        // Permission Denied
                        Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.snackbar_deny, Snackbar.LENGTH_LONG)
                                .show();
                    }
                    break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }

        private void makePhoneCall () {
            try {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse(getString(R.string.phone_number)));

                startActivity(intent);
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

}