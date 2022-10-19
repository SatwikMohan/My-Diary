package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageMemorialAdapter extends RecyclerView.Adapter<ImageMemorialAdapter.RecycleViewHolder> {
    Context context;
    ArrayList<ImageMemorialModal> list;

    public ImageMemorialAdapter(Context context, ArrayList<ImageMemorialModal> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.imagememorial_container;
    }
    @NonNull
    @Override
    public ImageMemorialAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new ImageMemorialAdapter.RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageMemorialAdapter.RecycleViewHolder holder, int position) {

        ImageMemorialModal imageMemorialModal=list.get(position);
        holder.date.setText(imageMemorialModal.getDate());
        holder.status.setText(imageMemorialModal.getStatus());
        Picasso.get().load(imageMemorialModal.getUrl()).into(holder.imageView);
        //Glide.with(context).load(imageMemorialModal.getUrl()).into(holder.imageView);
        //holder.imageView.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        ImageView imageView;
        TextView status;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.memorial_container_date);
            imageView=itemView.findViewById(R.id.memorial_container_image);
            status=itemView.findViewById(R.id.memorial_container_status);

        }
    }
}
