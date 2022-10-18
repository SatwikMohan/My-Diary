package com.gigawattstechnology.mydiary.ui.Opinions;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gigawattstechnology.mydiary.OpinionAdapter;
import com.gigawattstechnology.mydiary.OpinionModal;
import com.gigawattstechnology.mydiary.R;
import com.gigawattstechnology.mydiary.databinding.FragmentMyOpinionsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyOpinionsFragment extends Fragment {
FragmentMyOpinionsBinding binding;
RecyclerView recyclerView;
DatabaseReference databaseReference;
ArrayList<OpinionModal> list;
OpinionAdapter opinionAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentMyOpinionsBinding.inflate(inflater,container,false);
        recyclerView=binding.opinionRecyclerView;
        databaseReference= FirebaseDatabase.getInstance().getReference("My Opinions");
        View root=binding.getRoot();

        binding.writeOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView=LayoutInflater.from(getContext()).inflate(R.layout.opinion_dialog,null);
                EditText Topic=dialogView.findViewById(R.id.topic_opinion);
                EditText Solution=dialogView.findViewById(R.id.solution_opinion);
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(requireContext());
                alertDialog.setView(dialogView).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String topic=Topic.getText().toString();
                        String solution=Solution.getText().toString();
                        if(topic.isEmpty()){
                            Topic.setError("Topic Required");
                            return;
                        }
                        if(solution.isEmpty()){
                            Solution.setError("Solution Required");
                            return;
                        }
                        databaseReference.child(topic.replace("/","")).setValue(solution);

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
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    list.add(new OpinionModal(postSnapshot.getKey(), postSnapshot.getValue(String.class)));
                }
                opinionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        opinionAdapter=new OpinionAdapter(root.getContext(),list);
        recyclerView.setAdapter(opinionAdapter);

        return root;
    }
}