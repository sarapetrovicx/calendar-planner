package rs.raf.projekat1.sara_petrovic_rn4520.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rs.raf.projekat1.sara_petrovic_rn4520.R;
import rs.raf.projekat1.sara_petrovic_rn4520.viewpager.PagerAdapter;

public class BottomNavigationActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;

        });

        ActivityResultLauncher<Intent> sharedPreferencesActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                        String message = sharedPreferences.getString(LoginActivity.IS_LOGGED_IN, "");
                        if (message == null) message = "Error";
                        Toast.makeText(this, "We have written to pref: " + message, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Nothing was written", Toast.LENGTH_SHORT).show();
                    }
                });

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String isLoggedIn = sharedPreferences.getString(LoginActivity.IS_LOGGED_IN, null);

        if(isLoggedIn == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            sharedPreferencesActivityResultLauncher.launch(intent);
        } else {
            setContentView(R.layout.activity_bottom_navigation);
            init();
        }
        setContentView(R.layout.activity_bottom_navigation);
        init();
    }

    private void init() {
        initViewPager();
        initNavigation();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    private void initNavigation() {
        ((BottomNavigationView)findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.navigation_1: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                case R.id.navigation_2: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                case R.id.navigation_3: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
            }
            return true;
        });
    }

}