package com.gigawattstechnology.mydiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
        holder.url.setText(imageMemorialModal.getUrl());
        //Picasso.get().load(imageMemorialModal.getUrl()).into(holder.imageView);
        /*Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(imageMemorialModal.getUrl()).error(R.drawable.ic_launcher_foreground).into(holder.imageView);*/
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
        TextView url;
        Button viewImage;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.memorial_container_date);
            status=itemView.findViewById(R.id.memorial_container_status);
            url=itemView.findViewById(R.id.imageMemorial_container_url);
            viewImage=itemView.findViewById(R.id.imageMemorial_container_button);
            viewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,ViewImage.class);
                    i.putExtra("url",url.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }
}
