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

public class sendmessage extends AppCompatActivity  {

    ProgressDialog progress_dialog;
    EditText edittextmessage;
    Button buttonsend;
    int  docor_id;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmessage);
        progress_dialog = new ProgressDialog(sendmessage.this);

        Intent intent = getIntent();

         docor_id= intent.getExtras().getInt("doctor_id");
        user_id=SharedPrefManager.getInstance(sendmessage.this).getserid();






        buttonsend =  findViewById(R.id.buttonsend);
        edittextmessage=findViewById(R.id.edittextmessage);





        buttonsend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendmessage();
            }
        });



    }




    private void sendmessage() {


        final String message_ = edittextmessage.getText().toString().trim();


        progress_dialog.setMessage("please wait");
        progress_dialog.setCancelable(false);
        progress_dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/sendmessage.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progress_dialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if(res.getString("result").equalsIgnoreCase("success")){


                                Toast.makeText(sendmessage.this, "Message sent", Toast.LENGTH_SHORT).show();



                                Toast.makeText(sendmessage.this, ""+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                map.put("message",message_);
                map.put("user_id",user_id);
                map.put("doctor_id",""+docor_id);


                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);


    }

}
