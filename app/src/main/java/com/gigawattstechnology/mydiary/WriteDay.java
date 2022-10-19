package com.gigawattstechnology.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gigawattstechnology.mydiary.ui.gallery.GalleryFragment;

import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class WriteDay extends AppCompatActivity {
TextView date;
EditText text;
Button save;
    String AppID="application-0-cmcng";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_day);
        Realm.init(this);
        App app =new App(new AppConfiguration.Builder(AppID).build());
        date=findViewById(R.id.MyDay_Date_time);
        text=findViewById(R.id.MyDay_EditText);
        save=findViewById(R.id.MyDay_Button);
        Date today=new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date.setText(simpleDateFormat.format(today));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myDay_text=text.getText().toString();
                if (myDay_text.isEmpty()){
                    text.setError("Go on express your day");
                    return ;
                }
                user=app.currentUser();
                mongoClient=user.getMongoClient("mongodb-atlas");
                mongoDatabase=mongoClient.getDatabase("MyDiaryDatabase");
                mongoCollection= mongoDatabase.getCollection("MyDiaryCollection");
                mongoCollection.insertOne(new Document("userid",user.getId()).append("Date",date.getText().toString()).append("Text",myDay_text))
                        .getAsync(result->{
                            if (result.isSuccess()){
                                //Log.v("user","Data inserted ");
                                startActivity(new Intent(WriteDay.this, GalleryFragment.class));
                            }else{
                               // Log.v("user","Data not inserted ");
                                Toast.makeText(WriteDay.this, "Error! Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            });
    }
}