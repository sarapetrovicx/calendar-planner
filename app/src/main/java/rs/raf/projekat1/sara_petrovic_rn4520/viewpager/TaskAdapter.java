package rs.raf.projekat1.sara_petrovic_rn4520.viewpager;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import rs.raf.projekat1.sara_petrovic_rn4520.R;
import rs.raf.projekat1.sara_petrovic_rn4520.model.MyCalendar;
import rs.raf.projekat1.sara_petrovic_rn4520.model.Priority;
import rs.raf.projekat1.sara_petrovic_rn4520.model.Task;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.ViewHolder> {

    private final Consumer<Task> onTaskClicked;


    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, Consumer<Task> onCarClicked) {
        super(diffCallback);
        this.onTaskClicked = onCarClicked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_task_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new ViewHolder(view, parent.getContext(), position -> {
            Task cal = getItem(position);
            onTaskClicked.accept(cal);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task cal = getItem(position);
        holder.bind(cal);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(Task task) {
            if(task == null){
                ((TextView) itemView.findViewById(R.id.date)).setText("");
            } else {
                String timeTV = task.getStartTime().getHour()+":"+task.getStartTime().getMinute()+" - "+task.getEndTime().getHour()+":"+task.getEndTime().getMinute();
                ((TextView) itemView.findViewById(R.id.timeTV)).setText(timeTV);

                ((TextView) itemView.findViewById(R.id.title)).setText(task.getTitle());

                if(task.getPriority().equals(Priority.HIGH)){
                    itemView.findViewById(R.id.priority).setBackgroundColor(Color.RED);
                } else if(task.getPriority().equals(Priority.MID)){
                    itemView.findViewById(R.id.priority).setBackgroundColor(Color.YELLOW);
                } else {
                    itemView.findViewById(R.id.priority).setBackgroundColor(Color.GREEN);
                }


            }
        }

    }
}
