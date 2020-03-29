package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doctors.Util.SharedPrefManager;

public class MainActivity extends AppCompatActivity  {


    Button  buttonopenlogin,buttonopenreg ,buttondoctorlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonopenlogin=findViewById(R.id.buttonopenlogin);
        buttonopenreg= findViewById(R.id.buttonopenreg);
        buttondoctorlogin= findViewById(R.id.buttondoctorlogin);


        buttonopenlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, login.class));


            }
        });

        buttonopenreg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, register.class));


            }
        });

        buttondoctorlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DoctorLogin.class));


            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();



        // check if user already logged  in


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        if(SharedPrefManager.getInstance(this).isdoctorLoggedIn()){
            Intent intent = new Intent(this, doctorhome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}
