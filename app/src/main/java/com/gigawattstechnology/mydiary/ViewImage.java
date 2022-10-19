package com.gigawattstechnology.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import io.realm.mongodb.sync.Progress;

public class ViewImage extends AppCompatActivity {
ImageView imageView;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        String url=getIntent().getStringExtra("url");
        imageView=findViewById(R.id.CloudinaryImage);
        progressBar=findViewById(R.id.progressBar);
        Picasso.get().load(url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(ViewImage.this, "Load Success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(ViewImage.this, "Cannot Load", Toast.LENGTH_SHORT).show();
            }
        });
    }
}