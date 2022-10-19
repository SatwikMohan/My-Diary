package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyDayAdapter extends RecyclerView.Adapter<MyDayAdapter.RecycleViewHolder> {
    Context context;
    ArrayList<MyDayModal> list;

    public MyDayAdapter(Context context, ArrayList<MyDayModal> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.myday_container;
    }

    @NonNull
    @Override
    public MyDayAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(viewType,parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDayAdapter.RecycleViewHolder holder, int position) {

        MyDayModal myDayModal=list.get(position);
        holder.date.setText(myDayModal.getMyDay_date());
        holder.text.setText(myDayModal.getMyDay_text());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView date,text;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.myDay_Container_date);
            text=itemView.findViewById(R.id.myDay_Container_text);
        }
    }
}
