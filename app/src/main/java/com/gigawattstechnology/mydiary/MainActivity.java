package com.gigawattstechnology.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class MainActivity extends AppCompatActivity {
    String AppID="application-0-cmcng";
    EditText Email;
    EditText Password;
    Button SignUp;
    TextView GotoLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        App app =new App(new AppConfiguration.Builder(AppID).build());
        Email=findViewById(R.id.email_up);
        Password=findViewById(R.id.password_up);
        SignUp=findViewById(R.id.signup_button);
        GotoLogIn=findViewById(R.id.send_to_logIN);
        GotoLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LogInActivity.class));
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
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
                    Password.setError("Password must contain 8 characters");
                    return;
                }
                app.getEmailPassword().registerUserAsync(email,password,it->{
                    if(it.isSuccess()){
                        //Log.v("user", "Registered with email successfully");
                        Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Diary.class));
                    }else{
                       // Log.v("user","Registration Failed ");
                        Toast.makeText(MainActivity.this, "Error, Unable to Register", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}