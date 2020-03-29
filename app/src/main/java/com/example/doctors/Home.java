package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.doctors.Util.AppController;
import com.example.doctors.Util.SharedPrefManager;
import com.example.doctors.adapters.Doctors__Adapter;
import com.example.doctors.models.Doctor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    RecyclerView doctoes_Recyclerview;
    List<Doctor> Doctors_list;
    ProgressDialog progress_dialog;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter doctors__Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        doctoes_Recyclerview = (RecyclerView) findViewById(R.id.recyclerviewalldoctors);
        progress_dialog = new ProgressDialog(Home.this);
        Doctors_list = new ArrayList<>();

        load_all_doctors();
    }


    private void load_all_doctors()
    {
        progress_dialog.setMessage("plesae wait");
        progress_dialog.setCancelable(false);
        progress_dialog.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/alldoctors.php",null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progress_dialog.cancel();

                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                Doctor doctor = new Doctor();
                                doctor.setDoctor_id(data.getInt("doctor_id"));
                                doctor.setName(data.getString("name"));
                                doctor.setQualification(data.getString("qualification"));
                                doctor.setRate(data.getString("rate"));



                                Doctors_list.add(doctor);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        layoutManager = new LinearLayoutManager(Home.this,LinearLayoutManager.VERTICAL,false);
                        doctoes_Recyclerview.setLayoutManager(layoutManager);
                        doctors__Adapter = new Doctors__Adapter(Home.this, Doctors_list);
                        doctoes_Recyclerview.setAdapter(doctors__Adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_dialog.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(reqData);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logout:
                SharedPrefManager.getInstance(this).logout();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return true;
    }
}
