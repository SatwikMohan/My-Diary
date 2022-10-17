package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QutationAdapter extends RecyclerView.Adapter<QutationAdapter.RecyclerViewHolder> {
    Context context;
    List<QutationModal> list;

    public QutationAdapter(Context context, List<QutationModal> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getItemViewType(final int position) {
        return R.layout.qutation_container;
    }
    @NonNull
    @Override
    public QutationAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QutationAdapter.RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
