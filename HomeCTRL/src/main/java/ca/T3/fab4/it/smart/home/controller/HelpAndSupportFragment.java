package ca.T3.fab4.it.smart.home.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpAndSupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpAndSupportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HelpAndSupportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpAndSupportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpAndSupportFragment newInstance(String param1, String param2) {
        HelpAndSupportFragment fragment = new HelpAndSupportFragment();
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
        View view = inflater.inflate(R.layout.fragment_help_and_support, container, false);
        Spinner spinner = view.findViewById(R.id.t3SPINNER);
        TextView displayContact = view.findViewById(R.id.textView5);
        TextView textMsg = view.findViewById(R.id.textView6);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if(position==0){
                    displayContact.setText(getString(R.string.email_label) + getString(R.string.email_id) + getString(R.string.spc1) + getString(R.string.contact_label) + getString(R.string.ph_number));
                }
                else if(position==1){
                    displayContact.setText(getString(R.string.email_label1) + getString(R.string.email_id1) + getString(R.string.spc2) + getString(R.string.contact_label1) + getString(R.string.ph_number1));
                }
                else if(position==2){
                    displayContact.setText(getString(R.string.email_label2) + getString(R.string.email_id2) + getString(R.string.spc3) + getString(R.string.contact_label2) + getString(R.string.ph_number2));
                }
                else{
                    displayContact.setText(getString(R.string.email_label3) + getString(R.string.email_id3) + getString(R.string.spc4) + getString(R.string.contact_label3) + getString(R.string.ph_number3));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }


        });

        textMsg.setText(getString(R.string.general_label1)  + getString(R.string.spc5) + getString(R.string.general_label2));

        return view;
    }
}