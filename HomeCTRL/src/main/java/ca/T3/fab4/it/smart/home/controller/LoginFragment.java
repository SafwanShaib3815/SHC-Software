package ca.T3.fab4.it.smart.home.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_label), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText editTextUserName = view.findViewById(R.id.login_username);
        EditText editTextPassword = view.findViewById(R.id.editText2);


        Button btn = view.findViewById(R.id.button1);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = null, password = null;

                userName = editTextUserName.getText().toString();
                password = editTextPassword.getText().toString();

                if (userName.isEmpty()) {
                    editTextUserName.setError(getString(R.string.ET_Validation1));
                }
                else if (userName.length() < 3) {

                    editTextUserName.setError(getString(R.string.ET_Validation2));

                }

                else if(userName.matches(getString(R.string.check_numeric))){
                    editTextUserName.setError(getString(R.string.ET_validation3));

                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password field can not be blank");
                }
                else if (password.length() < 5 ) {

                    editTextPassword.setError("Password should contain at least 5 digits");

                }

                editor.putString(getString(R.string.u_name), userName);
                editor.putString(getString(R.string.pass), password);

                editor.commit();

            }
        });


        CheckBox chk = view.findViewById(R.id.checkBox);
        chk.setChecked(sharedPreferences.getBoolean(getString(R.string.check), true));
        editTextUserName.setText(sharedPreferences.getString(getString(R.string.u_name), getString(R.string.blank1)));
        editTextPassword.setText(sharedPreferences.getString(getString(R.string.pass), getString(R.string.blank2)));
        return view;
    }
}