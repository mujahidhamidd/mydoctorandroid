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
import com.example.doctors.adapters.Article_Adapter;
import com.example.doctors.adapters.Message__Adapter;
import com.example.doctors.models.Article;
import com.example.doctors.models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class allarticlesActivity extends AppCompatActivity {



    RecyclerView articles_Recyclerview;
    List<Article> articles_list;
    ProgressDialog progress_dialog;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter article__Adapter;
    String doctor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allarticles);

        articles_Recyclerview = (RecyclerView) findViewById(R.id.articles_Recyclerview);
        progress_dialog = new ProgressDialog(allarticlesActivity.this);
        articles_list = new ArrayList<>();



        Intent intent = getIntent();
        doctor_id= intent.getExtras().getInt("doctor_id")+"";





        load_all_messages();
    }


    private void load_all_messages()
    {


        final  ProgressDialog pd;
        pd = new ProgressDialog(allarticlesActivity.this);
        pd.setMessage("Plese Wait");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/allartcles.php",
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
                                Article article = new Article();
                                article.setId(data.getInt("id"));
                                article.setBody(data.getString("body"));
                                article.setSubject(data.getString("subject"));




                                articles_list.add(article);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        layoutManager = new LinearLayoutManager(allarticlesActivity.this,LinearLayoutManager.VERTICAL,false);
                        articles_Recyclerview.setLayoutManager(layoutManager);
                        article__Adapter = new Article_Adapter(allarticlesActivity.this, articles_list);
                        articles_Recyclerview.setAdapter(article__Adapter);


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


}
