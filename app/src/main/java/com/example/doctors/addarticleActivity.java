package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.doctors.Util.AppController;
import com.example.doctors.Util.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addarticleActivity extends AppCompatActivity  {

    ProgressDialog progress_dialog;
    EditText edittextarticle,edittextarticlesubj;
    Button buttonsend;
    String  docor_id;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addarticle);
        progress_dialog = new ProgressDialog(addarticleActivity.this);

        Intent intent = getIntent();


        docor_id=SharedPrefManager.getInstance(addarticleActivity.this).getdoctorid();






        buttonsend =  findViewById(R.id.buttonsend);
        edittextarticle=findViewById(R.id.edittextarticle);

        edittextarticlesubj=findViewById(R.id.edittextarticlesubj);



        buttonsend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendarticle();
            }
        });



    }




    private void sendarticle() {



        progress_dialog.setMessage("please wait");
        progress_dialog.setCancelable(false);
        progress_dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/sendarticle.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progress_dialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){


                                Toast.makeText(addarticleActivity.this, "Article saved", Toast.LENGTH_SHORT).show();



                                Toast.makeText(addarticleActivity.this, ""+   res.getString("message") , Toast.LENGTH_SHORT).show();
                            }
                            else{


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progress_dialog.cancel();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("body",edittextarticle.getText().toString().trim());
                map.put("subject",edittextarticlesubj.getText().toString().trim());

                map.put("doctor_id",""+docor_id);


                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);


    }

}
