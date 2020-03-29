package com.example.doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class forget_passord extends AppCompatActivity  {

    ProgressDialog progressDialog;
    EditText edittextemail;
    Button buttonforget;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passord);
        progressDialog = new ProgressDialog(forget_passord.this);
        buttonforget =  findViewById(R.id.buttonforget);
        edittextemail =  findViewById(R.id.edittextemail);

        buttonforget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendemail();

            }
        });


    }



    private void sendemail() {

        String email_ = edittextemail.getText().toString().trim();

        if (email_.matches("")) {
            edittextemail.setError("Email is required");
            edittextemail.requestFocus();
            return;
        }


        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/reset_password.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);


                        progressDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){





                                Intent intent = new Intent(forget_passord.this, entercode.class);
                                intent.putExtra("email", edittextemail.getText().toString());
                                startActivity(intent);

                                }
                            else  if(res.getString("result").equalsIgnoreCase("failed")){

                                Toast.makeText(forget_passord.this, "The email you entered dosenr exists", Toast.LENGTH_SHORT).show();
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
                map.put("email",edittextemail.getText().toString());


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
