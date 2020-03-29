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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.doctors.Util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    EditText  firstname,lastname,email,password,confirmpassword;
    Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(register.this);
        firstname =  findViewById(R.id.FirstName);
        lastname =  findViewById(R.id.LastName);
        email =  findViewById(R.id.email);
        password =  findViewById(R.id.password);
        confirmpassword= findViewById(R.id.confirmpassword);
        buttonSignUp= findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        userregister();




    }
    private void userregister() {

        String firstname_ = firstname.getText().toString().trim();
        final String lastname_ = lastname.getText().toString().trim();
        String email_ = email.getText().toString().trim();
        String password_ = password.getText().toString().trim();
        String _confirm = confirmpassword.getText().toString().trim();

        if (firstname_.matches("")) {
            firstname.setError("Email is required");
            firstname.requestFocus();
            return;
        }
        if (lastname_.matches("")) {
            lastname.setError("Email is required");
            lastname.requestFocus();
            return;
        }

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

        if (!password_.equals(_confirm)) {
            password.setError("Password dosent match");
            password.requestFocus();
            return;
        }

        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        progressDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){




                                  Toast.makeText(register.this, ""+   res.getString("message") , Toast.LENGTH_SHORT).show();


                                startActivity( new Intent(register.this,login.class));


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
                map.put("first_name",firstname.getText().toString());
                map.put("last_name",lastname.getText().toString());
                map.put("email",email.getText().toString());
                map.put("password",password.getText().toString());


                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);


    }


}
