package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.RecycleViewHolder> {
    Context context;
    ArrayList<OpinionModal> list;

    public OpinionAdapter(Context context, ArrayList<OpinionModal> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.opinion_container;
    }
    @NonNull
    @Override
    public OpinionAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new OpinionAdapter.RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpinionAdapter.RecycleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
