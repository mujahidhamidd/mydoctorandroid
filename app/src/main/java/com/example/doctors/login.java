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

public class login extends AppCompatActivity  {

    ProgressDialog progress_dialog;
    EditText email,password;
    Button buttonlogin;
    TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progress_dialog = new ProgressDialog(login.this);

        email =  findViewById(R.id.editTextEmail);
        password =  findViewById(R.id.edittextpassword);
        buttonlogin =  findViewById(R.id.buttonlogin);
        forget =  findViewById(R.id.forget);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userlogin();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(login.this, forget_passord.class);
                 startActivity(intent);
            }
        });


    }




    private void userlogin() {

        String email_ = email.getText().toString().trim();
        String password_ = password.getText().toString().trim();


        if (email_.matches("")) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }


        if (password_.matches("")) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }


        progress_dialog.setMessage("please wait");
        progress_dialog.setCancelable(false);
        progress_dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progress_dialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){




                                Toast.makeText(login.this, ""+   res.getString("message") , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this,Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                 SharedPrefManager.getInstance(login.this).savelogin(res.getString("user_id"));
                            }
                            else{

                                Toast.makeText(login.this, "Wrong email and password", Toast.LENGTH_SHORT).show();

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
                map.put("email",email.getText().toString());
                map.put("password",password.getText().toString());


                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);


    }

}
