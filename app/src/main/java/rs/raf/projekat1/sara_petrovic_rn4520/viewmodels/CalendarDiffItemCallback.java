package rs.raf.projekat1.sara_petrovic_rn4520.viewmodels;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.sara_petrovic_rn4520.model.MyCalendar;

public class CalendarDiffItemCallback extends DiffUtil.ItemCallback<MyCalendar> {
    @Override
    public boolean areItemsTheSame(@NonNull MyCalendar oldItem, @NonNull MyCalendar newItem) {
        return oldItem.getDate().equals(newItem.getDate());
    }

    @Override
    public boolean areContentsTheSame(@NonNull MyCalendar oldItem, @NonNull MyCalendar newItem) {
        return oldItem.getDate().equals(newItem.getDate());
    }
}
