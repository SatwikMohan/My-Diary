package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.RecycleViewHolder> {
    Context context;
    List<PlanData> list;

    public PlanAdapter(Context context, List<PlanData> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getItemViewType(final int position) {
        return R.layout.plan_container;
    }
    @NonNull
    @Override
    public PlanAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(viewType,parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.RecycleViewHolder holder, int position) {

        PlanData planData=list.get(position);
        holder.date.setText(planData.Date);
        holder.plan.setText(planData.plan);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView date,plan;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.plan_container_date);
            plan=itemView.findViewById(R.id.plan_container_plan);
        }
    }
}
