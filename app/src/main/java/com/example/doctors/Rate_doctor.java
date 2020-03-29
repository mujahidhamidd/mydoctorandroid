package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.doctors.Util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Rate_doctor extends AppCompatActivity  {

    ProgressDialog progressDialog;
    EditText edittextrate;
    Button rate;

     String doctor_rating;
     int doctor_id;

    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_doctor);
        progressDialog = new ProgressDialog(Rate_doctor.this);
        //edittextrate =  findViewById(R.id.edittextrate);
        rate =  findViewById(R.id.rate);
        doctor_rating="";

        Intent intent = getIntent();
        doctor_id= intent.getExtras().getInt("doctor_id");


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
       //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {


                doctor_rating=String.valueOf(rating);



            }
        });





        rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rate();

            }
        });


    }



    private void rate() {

        Log.d("jojo", doctor_rating);


        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/rate_doctor.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);


                        progressDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){


                                Toast.makeText(Rate_doctor.this, res.getString("message"), Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(Rate_doctor.this,Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                            else  if(res.getString("result").equalsIgnoreCase("failed")){

                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.getMessage());

                        progressDialog.cancel();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("doctor_id",doctor_id+"");
                map.put("rate",doctor_rating);


                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request);


    }

}
