package rs.raf.projekat1.sara_petrovic_rn4520.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rs.raf.projekat1.sara_petrovic_rn4520.R;
import rs.raf.projekat1.sara_petrovic_rn4520.activities.BottomNavigationActivity;
import rs.raf.projekat1.sara_petrovic_rn4520.activities.LoginActivity;

public class UserProfileFragment extends Fragment {

    private TextView username;
    private TextView email;
    private Button change;
    private Button logout;



    public UserProfileFragment() {
        super(R.layout.fragment_user_profile);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view){
        initView(view);
        initListeners();
    }

    void initView(View view){
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        change = view.findViewById(R.id.change);
        logout = view.findViewById(R.id.logout);
    }

    void initListeners(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        String usernameSP = sharedPreferences.getString(LoginActivity.USERNAME, "");
        String emailSP = sharedPreferences.getString(LoginActivity.EMAIL, "");

        username.setText(usernameSP);
        email.setText(emailSP);

        logout.setOnClickListener(l -> {
            sharedPreferences
                    .edit()
                    .putString(LoginActivity.IS_LOGGED_IN, null)
                    .putString(LoginActivity.USERNAME, "")
                    .putString(LoginActivity.EMAIL, "")
                    .apply();
            Toast.makeText(getActivity(), "Message deleted from preferences", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}