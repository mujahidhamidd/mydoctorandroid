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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.doctors.Util.AppController;
import com.example.doctors.Util.SharedPrefManager;
import com.example.doctors.adapters.Chat__Adapter;
import com.example.doctors.adapters.Message__Adapter;
import com.example.doctors.models.Message;
import com.example.doctors.models.chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {



    RecyclerView chat_messages_Recyclerview;
    List<chat> Messages_list;
    ProgressDialog progress_dialog;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter chat__Adapter;
    String doctor_id;
    String user_id;
    EditText edittext_chatbox;
    Button button_chatbox_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat_messages_Recyclerview =  findViewById(R.id.recyclerviewchat);
        button_chatbox_send =  findViewById(R.id.button_chatbox_send);
        edittext_chatbox =  findViewById(R.id.edittext_chatbox);
        progress_dialog = new ProgressDialog(ChatActivity.this);
        Messages_list = new ArrayList<>();



        final Message message= (Message) getIntent().getSerializableExtra("message");
        this.setTitle(message.getMessage());


        user_id=message.getUser_id()+"";
        doctor_id= SharedPrefManager.getInstance(ChatActivity.this).getdoctorid();

        Log.d("jojo", doctor_id);



        button_chatbox_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendmessage();
            }
        });
        load_all_messages();
    }


    private void load_all_messages()
    {

        Messages_list.clear();
        final  ProgressDialog pd;
        pd = new ProgressDialog(ChatActivity.this);
        pd.setMessage("Plese Wait");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, "http://10.0.3.2/doctors_backend/api/allmessages.php",
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
                                chat chatmessage = new chat();
                                chatmessage.setMessage(data.getString("message"));
                                chatmessage.setUser_id(data.getString("user_id"));
                                chatmessage.setName(data.getString("name"));
                                chatmessage.setDoctor_name(data.getString("doctor_name"));
                                chatmessage.setType(data.getString("type"));




                                Messages_list.add(chatmessage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        layoutManager = new LinearLayoutManager(ChatActivity.this,LinearLayoutManager.VERTICAL,false);
                        chat_messages_Recyclerview.setLayoutManager(layoutManager);
                        chat__Adapter = new Chat__Adapter(ChatActivity.this, Messages_list);
                        chat_messages_Recyclerview.setAdapter(chat__Adapter);


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
                map.put("user_id",user_id);

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);







    }

    private void sendmessage() {


        final String message_ = edittext_chatbox.getText().toString().trim();


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


                                Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ChatActivity.this, doctorhome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                load_all_messages();
                               // startActivity(intent);
                                Toast.makeText(ChatActivity.this, ""+   res.getString("message") , Toast.LENGTH_SHORT).show();
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
                map.put("doctor_id",""+doctor_id);
                map.put("type","doctor");




                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

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

        }
        return true;
    }
}
