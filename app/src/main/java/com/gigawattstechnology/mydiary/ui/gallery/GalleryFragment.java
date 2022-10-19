package com.gigawattstechnology.mydiary.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gigawattstechnology.mydiary.MyDayAdapter;
import com.gigawattstechnology.mydiary.MyDayModal;
import com.gigawattstechnology.mydiary.WriteDay;
import com.gigawattstechnology.mydiary.databinding.FragmentGalleryBinding;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class GalleryFragment extends Fragment {
//My Day
    private FragmentGalleryBinding binding;
    RecyclerView recyclerView;
    String AppID="application-0-cmcng";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    User user;
    ArrayList<MyDayModal> list;
    MyDayAdapter myDayAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        recyclerView=binding.myDayRecyclerView;
        View root = binding.getRoot();
            binding.startToWriteMyDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(root.getContext(), WriteDay.class));
                }
            });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Realm.init(root.getContext());
        App app =new App(new AppConfiguration.Builder(AppID).build());
        user=app.currentUser();
        mongoClient=user.getMongoClient("mongodb-atlas");
        mongoDatabase=mongoClient.getDatabase("MyDiaryDatabase");
        mongoCollection= mongoDatabase.getCollection("MyDiaryCollection");
        list=new ArrayList<>();

        Document document=new Document().append("userid",user.getId());
        RealmResultTask<MongoCursor<Document>> findTask=mongoCollection.find(document).iterator();
        findTask.getAsync(task->{
            if (task.isSuccess()){
                MongoCursor<Document> results=task.get();
                while(results.hasNext()){
                    Document currentDoc=results.next();
                    if(currentDoc.getString("Date")!=null){

                        list.add(new MyDayModal(currentDoc.getString("Date"),currentDoc.getString("Text")));

                    }
                }

                if (list.size()>0){
                    myDayAdapter=new MyDayAdapter(root.getContext(),list);
                    recyclerView.setAdapter(myDayAdapter);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(myDayAdapter.getItemCount());
                        }
                    });
                }

            }else{
                Toast.makeText(root.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}