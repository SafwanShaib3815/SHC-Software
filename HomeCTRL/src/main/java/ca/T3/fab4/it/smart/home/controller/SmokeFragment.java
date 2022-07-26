/*
Abdulrhman Ragab    n01440938    0NA
Tanushree Ray    n01440938    0NA
Safwan Shaib    n01343815    0NA
Nkeiru Johnson-Achilike   n01411707 0NA
*/
package ca.T3.fab4.it.smart.home.controller;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SmokeFragment extends Fragment {


    private View view;
    int img= 1;
    int[] images = {R.mipmap.smokeclear_foreground, R.mipmap.smokedetected_foreground};
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
        view = inflater.inflate(R.layout.fragment_smoke,container,false);
        ImageView imageView=view.findViewById(R.id.smokeiv1);
        Button button = view.findViewById(R.id.smokebutton2);
        final MediaPlayer mediaPlayer= MediaPlayer.create(getActivity(), R.raw.alarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
                imageView.setImageResource(images[img]);
                img++;
                if(img==1)
                    mediaPlayer.setLooping(false);
                if(img==2)
                    img=0;
            }
        });
        return view;
    }
}