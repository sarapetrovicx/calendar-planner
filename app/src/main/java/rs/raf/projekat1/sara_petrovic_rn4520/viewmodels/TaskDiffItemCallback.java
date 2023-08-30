package rs.raf.projekat1.sara_petrovic_rn4520.viewmodels;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.sara_petrovic_rn4520.model.Task;

public class TaskDiffItemCallback extends DiffUtil.ItemCallback<Task> {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getStartTime().equals(newItem.getStartTime()) && oldItem.getEndTime().equals(newItem.getEndTime());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getContent().equals(newItem.getContent());
    }
}
