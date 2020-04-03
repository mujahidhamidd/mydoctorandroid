package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctors.models.Doctor;

public class details extends AppCompatActivity {



    TextView name,qulification;

    Button buttonmessage, buttonrate,buttonapointment, buttonarticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       final   Doctor doctor= (Doctor) getIntent().getSerializableExtra("doctor");
        this.setTitle(doctor.getName());


        name =findViewById(R.id.buttonmessage);


        name =findViewById(R.id.name);
        qulification =findViewById(R.id.qulification);
        qulification.setMovementMethod(new ScrollingMovementMethod());

        buttonmessage =findViewById(R.id.buttonmessage);

        buttonrate =findViewById(R.id.buttonrate);

        buttonapointment =findViewById(R.id.buttonapointment);


        buttonarticle =findViewById(R.id.buttonarticle);



        name.setText(doctor.getName());
        qulification.setText(doctor.getQualification());

        buttonmessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(details.this, userchatActivity.class);

                intent.putExtra("doctor_id", doctor.getDoctor_id());

                startActivity(intent);



            }
        });

        buttonapointment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(details.this, apointment.class);
                intent.putExtra("doctor_id", doctor.getDoctor_id());



                startActivity(intent);



            }
        });



        buttonrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(details.this, Rate_doctor.class);

                intent.putExtra("doctor_id", doctor.getDoctor_id());

                Log.d("jojo", ""+ doctor.getDoctor_id());

                startActivity(intent);



            }
        });


        buttonarticle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(details.this, allarticlesActivity.class);

                intent.putExtra("doctor_id", doctor.getDoctor_id());

                startActivity(intent);



            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
