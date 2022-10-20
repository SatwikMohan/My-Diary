package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.RecycleViewHolder> {
    Context context;
    List<PlanData> list;
    onClickInterface onClickInterface;

    public PlanAdapter(Context context, List<PlanData> list, com.gigawattstechnology.mydiary.onClickInterface onClickInterface) {
        this.context = context;
        this.list = list;
        this.onClickInterface=onClickInterface;
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
        holder.id.setText(""+planData.getID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        TextView date,plan,id;
        ImageView delete;
        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.plan_container_date);
            plan=itemView.findViewById(R.id.plan_container_plan);
            id=itemView.findViewById(R.id.plan_container_ID);
            delete=itemView.findViewById(R.id.myPlan_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppDatabase appDatabase=AppDatabase.getDbInstance(context);
                    PlanData planData=new PlanData();
                    planData.ID= Integer.parseInt(id.getText().toString());
                    planData.Date=date.getText().toString();
                    planData.plan=plan.getText().toString();
                    appDatabase.planDao().delete(planData);
                    onClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
