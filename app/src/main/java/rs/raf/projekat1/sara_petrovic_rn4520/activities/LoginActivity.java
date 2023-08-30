package rs.raf.projekat1.sara_petrovic_rn4520.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import rs.raf.projekat1.sara_petrovic_rn4520.R;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText username;
    EditText password;
    Button login;

    TextView emptyEmail;
    TextView emptyUsername;
    TextView emptyPassword;
    boolean valid = false;
    boolean messageWritten = false;

    ArrayList<String> passwords;
    public static final String IS_LOGGED_IN = "prefMessageKey";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    public void init(){
        initPassword();
        initView();
        initListeners();
    }

    public void initPassword(){
        passwords = new ArrayList<>();
        try {
            InputStream inputStream = getApplicationContext().getAssets().open("pass.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                passwords.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String pas: passwords){
            System.out.println(pas);
        }
    }

    public void initView(){
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);
        emptyUsername = findViewById(R.id.empty_username);
        emptyPassword = findViewById(R.id.empty_password);
        emptyEmail = findViewById(R.id.empty_email);
    }

    public void initListeners(){

        login.setOnClickListener(l ->{

            emptyEmail.setVisibility(View.INVISIBLE);
            emptyUsername.setVisibility(View.INVISIBLE);
            emptyPassword.setVisibility(View.INVISIBLE);

            if(email.getText().toString().equals("")){
                emptyEmail.setVisibility(View.VISIBLE);
                valid = false;
            }
            if(password.getText().toString().equals("")){
                emptyPassword.setVisibility(View.VISIBLE);
                valid = false;
            }
            if(username.getText().toString().equals("")){
                emptyUsername.setVisibility(View.VISIBLE);
                valid = false;
            }
            if(!isValidEmail(email.getText().toString().trim())){
                valid = false;
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            } else {
                if(isValidPassword(password.getText().toString().trim()) && passwords.contains(password.getText().toString().trim())){
                    if(!username.getText().toString().equals("")){
                        valid = true;
                    }
                    Toast.makeText(this, "Strong password", Toast.LENGTH_SHORT).show();
                } else {
                    valid = false;
                    Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }
            if(valid){
                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                sharedPreferences
                        .edit()
                        .putString(IS_LOGGED_IN, "yes")
                        .putString(USERNAME, username.getText().toString().trim())
                        .putString(EMAIL, email.getText().toString().trim())
                        .apply();
                messageWritten = true;
                Toast.makeText(this, "Message written to preferences", Toast.LENGTH_SHORT).show();
                int result;
                if(messageWritten) result = Activity.RESULT_OK;
                else result = Activity.RESULT_CANCELED;
                setResult(result);
                finish();
            }
        });
    }

    public static boolean isValidEmail(String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return input.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 5) {
            return false;
        }
        boolean hasDigit = false;
        boolean hasUppercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
        }
        if (!hasDigit || !hasUppercase) {
            return false;
        }

        String forbiddenChars = "~#^|$%&*!";
        for (char c : password.toCharArray()) {
            if (forbiddenChars.indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }

}
