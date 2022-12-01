/*

Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;


import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;

public class MotionFragment extends Fragment {

    String value = "";
    ImageView imageView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MotionFragment() {
        // Required empty public constructor
    }

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference motion = ref.child("somemail@mail/Motion");
    LinkedList<String> records_list = new LinkedList<String>();

    public static MotionFragment newInstance(String param1, String param2) {
        MotionFragment fragment = new MotionFragment();
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
        View view =  inflater.inflate(R.layout.fragment_motion, container, false);
        Switch readData = view.findViewById(R.id.switch3);


        TextView records = (TextView) view.findViewById(R.id.motion_records_tv);
        TextView real_time = (TextView) view.findViewById(R.id.real_time_motion_tv);
        imageView = view.findViewById(R.id.imageView);
        Button onButton = view.findViewById(R.id.button8);
        Button offButton = view.findViewById(R.id.button9);

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink);
                imageView.startAnimation(animation);

            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.clearAnimation();
            }
        });

        motion.addValueEventListener(new ValueEventListener() {
            String changedData = "";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                changedData = dataSnapshot.child("Real_Time").getValue(String.class);
                real_time.setText(changedData);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(),error.toException().toString(), Toast.LENGTH_LONG).show();
            }
        });

        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readData.isChecked()) {
                    String logslist = "";
                    Iterator<String> iterator = records_list.iterator();
                    while (iterator.hasNext()){
                        logslist = logslist + iterator.next() + "\n";
                    }
                    records.setText(logslist);
                }else
                {
                    records.setText("");
                }


            }
        });
        motion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.child("Records").getChildren()) {
                    records_list.addFirst(child.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}