package rs.raf.projekat1.sara_petrovic_rn4520.viewpager;


import android.content.Context;
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

public class CalendarAdapter extends ListAdapter<MyCalendar, CalendarAdapter.ViewHolder> {

    private final Consumer<MyCalendar> onDayClicked;


    public CalendarAdapter(@NonNull DiffUtil.ItemCallback<MyCalendar> diffCallback, Consumer<MyCalendar> onDayClicked) {
        super(diffCallback);
        this.onDayClicked = onDayClicked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_day, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new ViewHolder(view, parent.getContext(), position -> {
            MyCalendar cal = getItem(position);
            onDayClicked.accept(cal);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCalendar cal = getItem(position);
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

        public void bind(MyCalendar cal) {
            if(cal == null){
                ((TextView) itemView.findViewById(R.id.txt_day)).setText("");
            } else {
                ((TextView) itemView.findViewById(R.id.txt_day)).setText(String.valueOf(cal.getDate().getDayOfMonth()));
            }
        }

    }
}
