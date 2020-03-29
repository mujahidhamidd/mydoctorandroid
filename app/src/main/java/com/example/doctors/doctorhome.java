package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.doctors.Util.AppController;
import com.example.doctors.Util.SharedPrefManager;
import com.example.doctors.adapters.Message__Adapter;
import com.example.doctors.models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class doctorhome extends AppCompatActivity {



    RecyclerView doctoes_messages_Recyclerview;
    List<Message> Messages_list;
    ProgressDialog progress_dialog;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter messages__Adapter;
    String doctor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorhome);

        doctoes_messages_Recyclerview = (RecyclerView) findViewById(R.id.doctoes_messages_Recyclerview);
        progress_dialog = new ProgressDialog(doctorhome.this);
        Messages_list = new ArrayList<>();



        doctor_id= SharedPrefManager.getInstance(doctorhome.this).getdoctorid();

        Log.d("jojo", doctor_id);




        load_all_messages();
    }


    private void load_all_messages()
    {


        final  ProgressDialog pd;
        pd = new ProgressDialog(doctorhome.this);
        pd.setMessage("Plese Wait");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/doctor_message.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                        pd.cancel();



                        JSONArray res=new JSONArray() ;
                        try {
                            res = new JSONArray(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        for(int i = 0 ; i < res.length(); i++)
                        {
                            try {
                                JSONObject data = res.getJSONObject(i);
                                Message message = new Message();
                                message.setUser_id(data.getInt("user_id"));
                                message.setMessage(data.getString("name"));




                                Messages_list.add(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        layoutManager = new LinearLayoutManager(doctorhome.this,LinearLayoutManager.VERTICAL,false);
                        doctoes_messages_Recyclerview.setLayoutManager(layoutManager);
                        messages__Adapter = new Message__Adapter(doctorhome.this, Messages_list);
                        doctoes_messages_Recyclerview.setAdapter(messages__Adapter);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();

                        // Toast.makeText(InsertData.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("doctor_id",doctor_id);

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.doctorhome, menu);

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

            case R.id.addaticle:



                Intent intent1 = new Intent(this, addarticleActivity.class);
                startActivity(intent1);



                break;

        }
        return true;
    }
}
