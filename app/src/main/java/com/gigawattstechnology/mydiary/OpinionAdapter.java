package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        OpinionModal opinionModal=list.get(position);
        holder.topic.setText(opinionModal.getTopic());
        holder.solution.setText(opinionModal.getSolution());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView topic;
        TextView solution;
        ImageView delete;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("My Opinions");
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            topic=itemView.findViewById(R.id.opinion_container_topic);
            solution=itemView.findViewById(R.id.opinion_container_solution);
            delete=itemView.findViewById(R.id.delete_opinion);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child(topic.getText().toString()).removeValue();
                }
            });
        }
    }
}
