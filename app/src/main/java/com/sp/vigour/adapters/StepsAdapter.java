package com.sp.vigour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.vigour.R;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {
    private Context context;
    private int steps[];
    private String date[];

    public StepsAdapter(Context ct, int s[], String chronous[]) {
        context = ct;
        steps = s;
        date = chronous;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.steps_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.stepText.setText( String.valueOf(steps[position]) );
        holder.dateText.setText(date[position]);

    }

    @Override
    public int getItemCount() {
        return date.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView stepText, dateText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stepText = itemView.findViewById(R.id.steps_hist_main);
            dateText = itemView.findViewById(R.id.steps_hist_date);
        }
    }
}
