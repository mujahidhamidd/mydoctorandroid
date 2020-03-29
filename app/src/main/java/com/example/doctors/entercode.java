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

public class entercode extends AppCompatActivity {

    ProgressDialog pd;
    EditText edittextcode;
    Button buttonsubmit;
    String email ;

    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entercode);
        pd = new ProgressDialog(entercode.this);
        buttonsubmit =  findViewById(R.id.buttonsubmit);
        edittextcode =  findViewById(R.id.edittextcode);

        Intent intent = getIntent();
         email = intent.getExtras().getString("email");

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendemail();

            }
        });


    }



    private void sendemail() {

        String code = edittextcode.getText().toString().trim();

        if (code.matches("")) {
            edittextcode.setError("code is required");
            edittextcode.requestFocus();
            return;
        }


        pd.setMessage("please wait");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/get_code.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("response", response);


                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){


                                Intent intent = new Intent(entercode.this, change_password.class);
                                intent.putExtra("email", email);
                                startActivity(intent);





                            }
                            else if(res.getString("result").equalsIgnoreCase("failed")){


                                Toast.makeText(entercode.this, "WRONG CODE", Toast.LENGTH_SHORT).show();





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
                map.put("code",edittextcode.getText().toString());


                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);


    }
}
