package rs.raf.projekat1.sara_petrovic_rn4520.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import rs.raf.projekat1.sara_petrovic_rn4520.R;
import rs.raf.projekat1.sara_petrovic_rn4520.activities.DailyTasksActivity;
import rs.raf.projekat1.sara_petrovic_rn4520.activities.LoginActivity;
import rs.raf.projekat1.sara_petrovic_rn4520.viewmodels.CalendarDiffItemCallback;
import rs.raf.projekat1.sara_petrovic_rn4520.viewmodels.CalendarViewModel;
import rs.raf.projekat1.sara_petrovic_rn4520.model.MyCalendar;
import rs.raf.projekat1.sara_petrovic_rn4520.viewpager.CalendarAdapter;

public class CalendarFragment extends Fragment{

    private TextView month;
    private RecyclerView recyclerView;
    private CalendarViewModel calendarViewModel;
    private LocalDate date;
    private CalendarAdapter calendarAdapter;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println(getActivity());
        calendarViewModel = new ViewModelProvider(getActivity()).get(CalendarViewModel.class);
        init(view);
    }

    public void init(View view){
        initView(view);
        initListeners(view);
        initObservers(view);
        initRecycler(view);
    }

    public void initView(View view){
        month = view.findViewById(R.id.month);
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void initObservers(View view) {
        //this
        calendarViewModel.getCals().observe(getViewLifecycleOwner(), cals -> {
            calendarAdapter.submitList(cals);
        });
    }


    private void initRecycler(View view) {
        calendarAdapter = new CalendarAdapter(new CalendarDiffItemCallback(), cal -> {
            Toast.makeText(view.getContext(), cal.getDate().getDayOfMonth() + "", Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("date_key", Context.MODE_PRIVATE);
            sharedPreferences
                    .edit()
                    .putString("DATE", cal.getDate().toString())
                    .apply();
            Intent intent = new Intent(getActivity(), DailyTasksActivity.class);
            getActivity().startActivity(intent);

        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);

    }

    public void initListeners(View view){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();


                LocalDate firstVisibleDate = LocalDate.now().withDayOfMonth(1);
                if(calendarAdapter.getCurrentList().get(firstVisiblePosition) != null)
                     firstVisibleDate = calendarAdapter.getCurrentList().get(lastVisiblePosition).getDate();


                for(MyCalendar myCalendar:  calendarAdapter.getCurrentList()){
                    if(myCalendar == null){
                        lastVisiblePosition++;
                    }
                }
                LocalDate lastVisibleDate = calendarAdapter.getCurrentList().get(lastVisiblePosition).getDate();

                int daysBetween = (int) ChronoUnit.DAYS.between(firstVisibleDate, lastVisibleDate);

                int firstVisibleMonth = firstVisibleDate.getMonthValue();

                int first = 0;
                int second = 0;

                for(int i = 0; i < daysBetween; i++){
                    LocalDate mc = firstVisibleDate.plusDays(i);
                    if(mc.getMonthValue() == firstVisibleMonth){
                        first++;
                    } else {
                        second++;
                    }
                }

                if(first > second) month.setText(monthYearFromDate(firstVisibleDate));
                else month.setText(monthYearFromDate(lastVisibleDate));

            }
        });
    }


    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth(); //30

        LocalDate firstOfMonth = date.withDayOfMonth(1); //1/4/2023
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); //saturday->6

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }



}
