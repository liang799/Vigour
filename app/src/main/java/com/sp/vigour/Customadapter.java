package com.sp.vigour;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Customadapter extends RecyclerView.Adapter<Customadapter.MyViewHolder>{

    Context context;
    ArrayList<String> healthtip;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    Customadapter(Context context, ArrayList healthtip){
        this.context = context;
        this.healthtip = healthtip;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        MyViewHolder evh = new MyViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.healthtiptxt.setText(healthtip.get(position));

    }

    @Override
    public int getItemCount() {
        return healthtip.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView healthtiptxt;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            healthtiptxt = itemView.findViewById(R.id.healthtext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
