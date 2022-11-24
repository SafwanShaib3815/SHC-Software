/*
Jagminder Sembi
Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.primitives.Chars;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class RFIDFragment extends Fragment {
    View view;
    ImageButton openDoor;
    Button activityLog;
    TextView openDoorText, activityLogText, realtimeText;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference rfid = ref.child("somemail@mail/RFID");//.child("RFID"    );
    public RFIDFragment() {
        // Required empty public constructor
    }

    public static RFIDFragment newInstance(String param1, String param2) {
        RFIDFragment fragment = new RFIDFragment();
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
        view = inflater.inflate(R.layout.fragment_r_f_i_d, container, false);
        openDoorText = view.findViewById(R.id.open_door_tv);
        activityLogText = view.findViewById(R.id.activity_log_tv);
        realtimeText = view.findViewById(R.id.real_time_rfid_tv);
        openDoor = view.findViewById(R.id.T3_open_door_button);
        openDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Door is open", Toast.LENGTH_LONG).show();
                openDoor.setImageResource(R.mipmap.ic_open_door); //change image button icon when door opened
                openDoorText.setTextColor(Color.GREEN); //change text color when door opened
            }
        });

        //updata real time text view with real time sensor data
        rfid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String changedData = snapshot.child("Real_Time").getValue(String.class);
                realtimeText.setText(changedData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        activityLog = view.findViewById(R.id.activity_log);
        activityLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDBreadings();
            }
        });

        Button notificationSend = view.findViewById(R.id.button10);
        notificationSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), NotificationActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

    //do on activity log button click
    public void getDBreadings() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rfid = ref.child("somemail@mail/RFID");//.child("RFID"    );
        rfid.addChildEventListener(new ChildEventListener() {
//            String listString;

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Iterable<DataSnapshot> recordsChilds = snapshot.child("Records").getChildren();
//                ArrayList<DataSnapshot> Records = new ArrayList<>();
//                for(DataSnapshot record : recordsChilds){
//                    DataSnapshot r = record.getValue(DataSnapshot.class);
//                    Records.add(r);
//                    listString = Records.stream().map(Object::toString)
//                            .collect(Collectors.joining(", "));
//                    //activityLogText.setText(listString); //set the log activity text view to the resultant string
//                }
//                activityLogText.setText(listString); //set the log activity text view to the resultant string
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}