package ca.T3.fab4.it.smart.home.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        RadioGroup radioGroup = view.findViewById(R.id.t3RG);
        RadioGroup radioGroup1 = view.findViewById(R.id.t3RG1);
        RadioGroup radioGroup2 = view.findViewById(R.id.t3RG2);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SettingsPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button button = view.findViewById(R.id.button6);
        button.setOnClickListener(view1 -> {
            RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            RadioButton radioButton1 = radioGroup1.findViewById(radioGroup1.getCheckedRadioButtonId());
            RadioButton radioButton2 = radioGroup2.findViewById(radioGroup2.getCheckedRadioButtonId());

            String background = null,temperature = null,clock = null;

            if(radioGroup.getCheckedRadioButtonId() == -1 && (radioGroup1.getCheckedRadioButtonId() == -1 &&
                    (radioGroup2.getCheckedRadioButtonId() == -1))){
                Toast.makeText(getActivity(), R.string.pref, Toast.LENGTH_SHORT).show();
            }else if(radioGroup.getCheckedRadioButtonId() == -1){
                Toast.makeText(getActivity(), R.string.pref_color,Toast.LENGTH_SHORT).show();
            }else if(radioGroup1.getCheckedRadioButtonId() == -1){
                Toast.makeText(getActivity(), R.string.pref_time,Toast.LENGTH_SHORT).show();
            }else if(radioGroup2.getCheckedRadioButtonId() == -1){
                Toast.makeText(getActivity(),"Temperature selection can not be empty",Toast.LENGTH_SHORT).show();
            }
            else{
                background = radioButton.getText().toString();
                temperature = radioButton1.getText().toString();
                clock = radioButton2.getText().toString();

            }

            editor.putString("Color", background);
            editor.putString("Temperature", temperature);
            editor.putString("Time", clock);

            editor.apply();

        });
         @SuppressLint("UseSwitchCompatOrMaterialCode") Switch lockScreen = view.findViewById(R.id.switch2);
        lockScreen.setOnClickListener(view12 -> {
            String swValue;
            if (lockScreen.isChecked()) {

                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                swValue = "true";
                editor.putString("Switch", swValue);
            }
            else{
                swValue = "false";
                editor.putString("Switch", swValue);
            }
            editor.apply();
        });


        return view;

    }
}