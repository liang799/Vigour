package com.sp.vigour.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{
    private Cursor cursor;
    private Context context;
    private Addhelper helper;

    public StepsAdapter(Context ct, Cursor c, Addhelper h) {
        context = ct;
        cursor = c;
        helper = h;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.steps_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.date.setText(helper.getDate(cursor));
        holder.steps.setText(cursor.getString(1));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView steps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            steps = itemView.findViewById(R.id.steps_hist_main);
            date = itemView.findViewById(R.id.steps_hist_date);
        }
    }
}
