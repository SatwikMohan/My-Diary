package com.gigawattstechnology.mydiary.ui.Memorial;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.gigawattstechnology.mydiary.ImageMemorialAdapter;
import com.gigawattstechnology.mydiary.ImageMemorialModal;
import com.gigawattstechnology.mydiary.R;
import com.gigawattstechnology.mydiary.databinding.FragmentMyImageMemorialBinding;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyImageMemorial extends Fragment {
FragmentMyImageMemorialBinding binding;
DatabaseReference databaseReference;
ActivityResultLauncher<String> photo;
ImageMemorialAdapter imageMemorialAdapter;
    Uri selectedImageUri;
    ImageView imageView;
    RecyclerView recyclerView;
    ArrayList<ImageMemorialModal> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentMyImageMemorialBinding.inflate(inflater,container,false);
        databaseReference= FirebaseDatabase.getInstance().getReference("My Image Memorial");
        recyclerView=binding.ImageMemorialRecyclerView;
        View root=binding.getRoot();

        Map config = new HashMap();
        config.put("cloud_name", "dbgmkskmk");
        config.put("api_key","757922249274476");
        config.put("api_secret","vONW2OphHLSaY-eDlZpe74cbuZU");
        config.put("secure", true);
        MediaManager.init(root.getContext(),config);


        photo=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                selectedImageUri=result;
                Glide.with(root.getContext()).load(result).into(imageView);
            }
        });
        binding.setImageDateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView=LayoutInflater.from(root.getContext()).inflate(R.layout.imagememorial_dialog,null);
                imageView=dialogView.findViewById(R.id.imageMemorial_chooseImage);
                TextView date=dialogView.findViewById(R.id.imageMemorial_Date);
                EditText status=dialogView.findViewById(R.id.imageMemorial_SetStatus);
                Date today=new Date(System.currentTimeMillis());
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                date.setText(format.format(today));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photo.launch("image/*");
                    }
                });
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(requireContext());
                alertDialog.setView(dialogView).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String[] url = new String[1];
                        String todayDate=date.getText().toString();
                        String text=status.getText().toString();



                        MediaManager.get().upload(selectedImageUri).callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                Toast.makeText(root.getContext(), "Upload Started", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {
                                Toast.makeText(root.getContext(), "Wait Uploading", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                url[0] =resultData.get("url").toString();
                                String key=""+System.currentTimeMillis();
                                databaseReference.child(key).child("Date").setValue(todayDate);
                                databaseReference.child(key).child("Status").setValue(text);
                                databaseReference.child(key).child("url").setValue(url[0]);
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Toast.makeText(root.getContext(), ""+error, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {
                                Toast.makeText(root.getContext(), ""+error, Toast.LENGTH_SHORT).show();
                            }
                        }).dispatch();

                    }
                }).setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    //Toast.makeText(root.getContext(), postSnapshot.child("Date").getValue(String.class), Toast.LENGTH_SHORT).show();
                    list.add(new ImageMemorialModal(postSnapshot.child("Date").getValue(String.class),postSnapshot.child("Status").getValue(String.class),postSnapshot.child("url").getValue(String.class)));
                }
                imageMemorialAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imageMemorialAdapter=new ImageMemorialAdapter(root.getContext(),list);
        recyclerView.setAdapter(imageMemorialAdapter);

        return root;
    }
}