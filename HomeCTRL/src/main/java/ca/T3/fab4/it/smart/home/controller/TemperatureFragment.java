/*
Abdulrhman Ragab    n01440938    0NA
Tanushree Ray    n01440938    0NA
Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureFragment extends Fragment implements Animation.AnimationListener{

    String value = "";
    ImageView imageView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TemperatureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperatureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperatureFragment newInstance(String param1, String param2) {
        TemperatureFragment fragment = new TemperatureFragment();
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
        View view =  inflater.inflate(R.layout.fragment_temperature, container, false);
        Switch readData = view.findViewById(R.id.switch1);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("SettingsPref", Context.MODE_PRIVATE);

        // Write a message to the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference temp = ref.child("Dummy Sensors").child("Temp").child("Temp2");
        TextView txtMessage = (TextView) view.findViewById(R.id.textView10);
        imageView = view.findViewById(R.id.imageView4);
        Button onButton = view.findViewById(R.id.button4);
        Button offButton = view.findViewById(R.id.button5);
        Animation an = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        an.setAnimationListener(this);


        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(an);
            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.clearAnimation();
            }
        });

        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        readData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float fahrenheit;
                if (readData.isChecked() && sharedPref.getString("Temperature", "").equalsIgnoreCase("Celsius")) {

                    txtMessage.setText("Temp is: " + value);
                }

                else if (readData.isChecked() && sharedPref.getString("Temperature", "").equalsIgnoreCase("Fahrenheit")) {
                    fahrenheit = (Float.valueOf(value).floatValue() * 9 / 5 ) + 32 ;
                    txtMessage.setText("Temp is: " + fahrenheit);
                }
                else
                {
                    txtMessage.setText(" ");
                }

            }
        });




        return view;

    }



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}