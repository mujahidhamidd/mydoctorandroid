package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class apointment extends AppCompatActivity {



    EditText prayer_name;



    Button buttonsave;

    Spinner type_spinner;

    DatePicker pickerDate;


    TimePicker pickerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apointment);

//


        buttonsave=findViewById(R.id.buttonsave);

        pickerDate = (DatePicker) findViewById(R.id.datePicker);

        pickerTime = (TimePicker) findViewById(R.id.timePicker);





        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Calendar calender = Calendar.getInstance();
                calender.clear();

                calender.set(Calendar.MONTH, pickerDate.getMonth());
                calender.set(Calendar.DAY_OF_MONTH, pickerDate.getDayOfMonth());
                calender.set(Calendar.YEAR, pickerDate.getYear());
                calender.set(Calendar.HOUR, pickerTime.getCurrentHour());
                calender.set(Calendar.MINUTE, pickerTime.getCurrentMinute());
                calender.set(Calendar.SECOND, 00);


                SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.hour_minutes));
                String timeString = formatter.format(new Date(calender.getTimeInMillis()));

                AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(apointment.this, NotificationManager2.class);




                PendingIntent pendingIntent = PendingIntent.getBroadcast(apointment.this, 0, intent, 0);

                alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);


                Log.d("jojo", timeString);
                // insert into database





            }
        });




    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }



}