package com.gigawattstechnology.mydiary;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageMemorialAdapter extends RecyclerView.Adapter<ImageMemorialAdapter.RecycleViewHolder> {
    @NonNull
    @Override
    public ImageMemorialAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageMemorialAdapter.RecycleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
