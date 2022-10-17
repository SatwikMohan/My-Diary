package com.gigawattstechnology.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class LogInActivity extends AppCompatActivity {
    String AppID="application-0-cmcng";
    EditText Email;
    EditText Password;
    Button LogIn;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    User user;
    MongoCollection<Document> mongoCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Realm.init(this);
        App app =new App(new AppConfiguration.Builder(AppID).build());
        Email=findViewById(R.id.email_in);
        Password=findViewById(R.id.password_in);
        LogIn=findViewById(R.id.login_button);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                String password=Password.getText().toString();
                if(email.isEmpty()){
                    Email.setError("Email is required");
                    return;
                }
                if(password.isEmpty()){
                    Password.setError("Password is Required");
                    return;
                }
                if (password.length()<8){
                    Password.setError("Invalid Password");
                    return;
                }
                app.loginAsync(Credentials.emailPassword(email, password), new App.Callback<User>() {
                    @Override
                    public void onResult(App.Result<User> result) {
                        if(result.isSuccess()){
                            Log.v("user","Logged in ");
                            user=app.currentUser();
                            mongoClient=user.getMongoClient("mongodb-atlas");
                            mongoDatabase=mongoClient.getDatabase("MyDiaryDatabase");
                            mongoCollection= mongoDatabase.getCollection("MyDiaryCollection");
                            startActivity(new Intent(LogInActivity.this,Diary.class));

                        }else{
                            Log.v("user","Failure to log");
                        }
                    }
                });
            }
        });
    }
}