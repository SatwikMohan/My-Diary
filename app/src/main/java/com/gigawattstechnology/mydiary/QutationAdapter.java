package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        QutationModal qutationModal=list.get(position);
        holder.quote.setText(qutationModal.getQ());
        holder.quote_by.setText(qutationModal.getA());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView quote;
        TextView quote_by;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            quote=itemView.findViewById(R.id.quote);
            quote_by=itemView.findViewById(R.id.quote_by);
        }
    }
}
