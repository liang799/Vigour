package com.sp.vigour.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.vigour.Addhelper;
import com.sp.vigour.R;

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.ViewHolder> {
    private Cursor cursor;
    private Context context;
    private Addhelper helper;
    private Bundle bundle;

    public TransAdapter(Context ct, Cursor c, Addhelper h) {
        context = ct;
        cursor = c;
        helper = h;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String amount = "+" + helper.getCoin(cursor);
        String steps = cursor.getString(1) + " steps";
        holder.amt.setText(amount);
        holder.steps.setText(steps);
        // Create the Bundle to pass, you can put String, Integer, or serializable object
        bundle = new Bundle();
        bundle.putString("date", helper.getDate(cursor));
        bundle.putString("amount", helper.getCoin(cursor));
        bundle.putString("steps", cursor.getString(1));
        holder.itemView.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_transactions_to_details, bundle)
        );
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView amt;
        private TextView steps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amt = itemView.findViewById(R.id.money_row);
            steps = itemView.findViewById(R.id.steps_row);
        }
    }
}