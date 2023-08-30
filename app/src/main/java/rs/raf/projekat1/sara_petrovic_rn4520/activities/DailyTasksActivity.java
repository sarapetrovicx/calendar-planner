package rs.raf.projekat1.sara_petrovic_rn4520.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalDateTime;

import rs.raf.projekat1.sara_petrovic_rn4520.R;
import rs.raf.projekat1.sara_petrovic_rn4520.model.MyCalendar;
import rs.raf.projekat1.sara_petrovic_rn4520.model.Priority;
import rs.raf.projekat1.sara_petrovic_rn4520.model.Task;
import rs.raf.projekat1.sara_petrovic_rn4520.viewmodels.CalendarViewModel;
import rs.raf.projekat1.sara_petrovic_rn4520.viewmodels.TaskDiffItemCallback;
import rs.raf.projekat1.sara_petrovic_rn4520.viewpager.TaskAdapter;

public class DailyTasksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView dateTV;
    private FloatingActionButton addBtn;
    private LinearLayout mainLayout;
    private ImageButton backBtn;
    private LocalDate localDate;
    private CalendarViewModel calendarViewModel;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences("date_key", Context.MODE_PRIVATE);
        String date = sharedPreferences.getString("DATE", "");
        localDate = LocalDate.parse(date);

        setContentView(R.layout.activity_daily_plan);
        init();
    }

    private void init() {
        initView();
        initListeners();
        initObservers();
        initRecycler();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view_tasks);
        addBtn = findViewById(R.id.add);
        dateTV = findViewById(R.id.date);
        backBtn = findViewById(R.id.backBtn);
        mainLayout = findViewById(R.id.mainL);

        String text = localDate.getDayOfMonth() + ". " + localDate.getMonth().toString().toLowerCase() + " " + localDate.getYear() + ".";

        dateTV.setText(text);
    }

    private void initListeners() {
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                recyclerViewModel.filterCars(s.toString());
//            }
//        });

        addBtn.setOnClickListener(v -> {
            showAddSnackBar(
                    calendarViewModel.addTask(new Task(LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Test", "Blabla", Priority.LOW), localDate));
        });
        backBtn.setOnClickListener(v->{
            finish();
        });
    }

    private void showAddSnackBar(int id) {
        Snackbar
                .make(mainLayout, "Item added", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view -> calendarViewModel.removeTask(id))
                .show();
    }

    private void initObservers() {
        calendarViewModel.getTasksForDate(localDate).observe(this, tasks -> {
            taskAdapter.submitList(tasks);
        });
    }

    private void initRecycler() {
        taskAdapter = new TaskAdapter(new TaskDiffItemCallback(), task -> {
            Toast.makeText(this, task.getPriority().toString() + "", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("task_key", Context.MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .putString("start", task.getStartTime().toString())
                    .putString("end", task.getEndTime().toString())
                    .putString("title", task.getTitle())
                    .putString("priority", task.getPriority().toString())
                    .putString("content", task.getContent())
                    .apply();
            Intent intent = new Intent(this, TaskInfoActivity.class);
            startActivity(intent);
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter);
    }
}
