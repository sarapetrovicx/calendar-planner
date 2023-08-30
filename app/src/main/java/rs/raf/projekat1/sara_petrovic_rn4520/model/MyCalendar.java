package rs.raf.projekat1.sara_petrovic_rn4520.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class MyCalendar {

    private LocalDate date;
    private ArrayList<Task> tasks;

    public MyCalendar(LocalDate date) {
        this.date = date;
        tasks = new ArrayList<>();
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
