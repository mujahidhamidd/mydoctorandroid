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

public class change_password extends AppCompatActivity {

    ProgressDialog pd;
    EditText edittexnewpassword,edittextconfirm;
    Button buttonsubmit;
    String email ;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        pd = new ProgressDialog(change_password.this);
        buttonsubmit =  findViewById(R.id.buttonsubmit);
        edittexnewpassword =  findViewById(R.id.edittexnewpassword);
        edittextconfirm =  findViewById(R.id.edittextconfirm);
        Intent intent = getIntent();
         email = intent.getExtras().getString("email");

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changepassword();

            }
        });


    }



    private void changepassword() {

        String newpassword = edittexnewpassword.getText().toString().trim();
        String confirmpassword = edittextconfirm.getText().toString().trim();


        if (newpassword.matches("")) {
            edittexnewpassword.setError("password is required");
            edittexnewpassword.requestFocus();
            return;
        }
        if (confirmpassword.matches("")) {
            edittextconfirm.setError("please confirm password");
            edittextconfirm.requestFocus();
            return;
        }
        if (!confirmpassword.matches(newpassword)) {
            edittextconfirm.setError("password dosent match");
            edittextconfirm.requestFocus();
            return;
        }


        pd.setMessage("please wait");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/change_password.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);


                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){




                                Intent intent = new Intent(change_password.this, login.class);
                                startActivity(intent);



                                // SharedPrefManager.getInstance(register.this).userLogin(res.getString("Emp_id"));
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

                        pd.cancel();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("password",edittexnewpassword.getText().toString());
                map.put("email",email);



                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);


    }
}
