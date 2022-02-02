package com.sp.vigour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.vigour.R;
import com.sp.vigour.Vigouritem;

import java.util.ArrayList;

public class CustomadapterSteps extends RecyclerView.Adapter<CustomadapterSteps.MyViewHolder>{

    Context context;
    ArrayList<Vigouritem> vigouritemArrayList;
    ArrayList historyID;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public CustomadapterSteps(Context context, ArrayList historyID, ArrayList vigouritemArrayList){
        this.context = context;
        this.vigouritemArrayList = vigouritemArrayList;
        this.historyID = historyID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_row, parent, false);
        MyViewHolder evh = new MyViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vigouritem vigouritem = vigouritemArrayList.get(position);

        //holder.usermoneytxt.setText(vigouritem.getUsercrypto());
        //holder.steps.setText(vigouritem.getUsersteps());
        holder.steps_text.setText(vigouritem.getUsersteps());

        String totaltime = vigouritem.getUserdate() + " " + vigouritem.getUsertime();

        holder.steps_date.setText(totaltime);

    }

    @Override
    public int getItemCount() {
        return vigouritemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //TextView usermoneytxt;
       // TextView steps;

        TextView steps_text;
        TextView steps_date;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            //usermoneytxt = itemView.findViewById(R.id.money_row);
            //steps = itemView.findViewById(R.id.steps_row);

            steps_text = itemView.findViewById(R.id.steps_hist_main);
            steps_date = itemView.findViewById(R.id.steps_hist_date);

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
