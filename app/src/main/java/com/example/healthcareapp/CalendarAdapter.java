package com.example.healthcareapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private List<String> dates;
    private String selectedDate;
    private OnItemClickListener listener;

    public CalendarAdapter(List<String> dates) {
        this.dates = dates;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String date = dates.get(position);
        holder.bind(date);

        // Set click listener for the calendar item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(date);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void setSelectedDate(String date) {
        this.selectedDate = date;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(String date);
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        public void bind(String date) {
            dateTextView.setText(date);

            // Highlight the selected date
            if (date.equals(selectedDate)) {
                dateTextView.setBackgroundResource(R.drawable.bg_selected_date);
            } else {
                dateTextView.setBackgroundResource(R.drawable.bg_default_date);
            }
        }
    }
}
