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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RFIDFragment extends Fragment {
    View view;
    ImageButton openDoor;
    Button activityLog;
    TextView openDoorText, activityLogText, realtimeText;
    LinkedList<String> logs = new LinkedList<String>();
    String changedData;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference rfid = ref.child("somemail@mail/RFID");//.child("RFID"    );
    DatabaseReference rfid_records = ref.child("somemail@mail/RFID/Records");//.child("RFID"    );
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
                changedData = snapshot.child("Real_Time").getValue(String.class);
                realtimeText.setText(changedData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // onclick > iterate through the logs array list and display it
        activityLog = view.findViewById(R.id.activity_log);
        activityLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logslist = "";
                Iterator<String> iterator = logs.iterator();
                while (iterator.hasNext()){
                    logslist = logslist + iterator.next() + "\n";
                }
                activityLogText.setText(logslist);
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
        rfid_records.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()) {
                    logs.addFirst(child.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}