package rs.raf.projekat1.sara_petrovic_rn4520.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import rs.raf.projekat1.sara_petrovic_rn4520.model.MyCalendar;
import rs.raf.projekat1.sara_petrovic_rn4520.model.Priority;
import rs.raf.projekat1.sara_petrovic_rn4520.model.Task;

public class CalendarViewModel extends ViewModel {

    private final MutableLiveData<List<MyCalendar>> cals = new MutableLiveData<>();
    private ArrayList<MyCalendar> days = new ArrayList<>();

    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>();



    public CalendarViewModel() {
        LocalDate date = LocalDate.now().withDayOfMonth(1);//prvi u mesecu

        int dayOfWeek = date.getDayOfWeek().getValue();

        for (int i = 1; i < dayOfWeek; i++) {
            days.add(null);
        }

        for (int i = 0; i < date.lengthOfMonth(); i++) {
            LocalDate date1 = date.plusDays(i);
            MyCalendar myCalendar = new MyCalendar(date1);
            if(i % 2 == 0){
                LocalDateTime startTime = LocalDateTime.of(myCalendar.getDate(), LocalTime.of(13, 10));
                LocalDateTime endTime = LocalDateTime.of(myCalendar.getDate(), LocalTime.of(16, 10));
                myCalendar.getTasks().add(new Task(startTime, endTime, "Algebra", "ALgebra class", Priority.HIGH));
            }
            LocalDateTime startTime = LocalDateTime.of(myCalendar.getDate(), LocalTime.of(11, 10));
            LocalDateTime endTime = LocalDateTime.of(myCalendar.getDate(), LocalTime.of(14, 10));
            myCalendar.getTasks().add(new Task(startTime, endTime, "Gym", "Bring your water", Priority.MID));

            days.add(myCalendar);
        }

        for (int i = 0; i < 365; i++){
            LocalDate date1 = date.plusDays(i);
            MyCalendar myCalendar = new MyCalendar(date1);
            days.add(myCalendar);
        }

        ArrayList<MyCalendar> listToSubmit = new ArrayList<>(days);
        cals.setValue(listToSubmit);
    }

    public MutableLiveData<List<MyCalendar>> getCals() {
        return cals;
    }

    public MutableLiveData<List<Task>> getTasksForDate(LocalDate date){
        for(MyCalendar myCalendar : days){
            if(myCalendar != null && myCalendar.getDate().equals(date)){
                ArrayList<Task> listToSubmit = new ArrayList<>(myCalendar.getTasks());
                tasks.setValue(listToSubmit);
                return tasks;
            }
        }
        ArrayList<Task> listToSubmit = new ArrayList<>();
        tasks.setValue(listToSubmit);
        return tasks;
    }

    public void filterTasks(String filter, MyCalendar calendar) {

//        ArrayList<Task> filteredList = (ArrayList<Task>) getTasksForDate(calendar).stream().filter(task -> task.getTitle().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
//        calendar.setTasks(filteredList);
//        cars.setValue(filteredList);
    }

    public int addTask(Task task, LocalDate date) {
        for(MyCalendar myCalendar : days){
            if(myCalendar != null && myCalendar.getDate().equals(date)){
//                myCalendar.getTasks().add(task);
                ArrayList<Task> listToSubmit = new ArrayList<>(myCalendar.getTasks());
                listToSubmit.add(task);
                tasks.setValue(listToSubmit);
                return myCalendar.getTasks().size()-1;
            }
        }
        ArrayList<Task> listToSubmit = new ArrayList<>();
        listToSubmit.add(task);
        tasks.setValue(listToSubmit);
        return -1;
    }

    public void removeTask(int id) {
//        Optional<Car> carObject = carList.stream().filter(car -> car.getId() == id).findFirst();
//        if (carObject.isPresent()) {
//            carList.remove(carObject.get());
//            ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//            cars.setValue(listToSubmit);
//        }
    }
}
