package rs.raf.projekat1.sara_petrovic_rn4520.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.time.LocalDateTime;

import rs.raf.projekat1.sara_petrovic_rn4520.R;

public class TaskInfoActivity extends AppCompatActivity {

    TextView time;
    TextView name;
    TextView content;
    ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task_display);
        init();
    }


    public void init(){
        initView();
        initListeners();
    }

    void initView(){
        time = findViewById(R.id.time);
        name = findViewById(R.id.task_name);
        content = findViewById(R.id.note);
        back = findViewById(R.id.backB);


        SharedPreferences sharedPreferences = getSharedPreferences("task_key", Context.MODE_PRIVATE);
        LocalDateTime startTime = LocalDateTime.parse(sharedPreferences.getString("start", ""));
        LocalDateTime endTime = LocalDateTime.parse(sharedPreferences.getString("end", ""));


        String timeTV = startTime.getHour()+":"+startTime.getMinute()+" - "+endTime.getHour()+":"+endTime.getMinute();

        time.setText(timeTV);
        name.setText(sharedPreferences.getString("title", ""));
        content.setText(sharedPreferences.getString("content", ""));

    }

    void initListeners(){
        back.setOnClickListener(l ->{
            finish();
        });
    }

}
