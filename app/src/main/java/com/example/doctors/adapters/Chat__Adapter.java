package com.example.doctors.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doctors.ChatActivity;
import com.example.doctors.R;
import com.example.doctors.models.Doctor;
import com.example.doctors.models.Message;
import com.example.doctors.models.chat;

import java.util.List;


public class Chat__Adapter extends RecyclerView.Adapter<Chat__Adapter.HolderData>  {
    private List<chat> message_list;
    private Context context;
    private  int checkBoxClicked=0;


    public Chat__Adapter(Context context, List<chat> items)
    {
        this.message_list = items;
        this.context = context;

    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, final int position) {
       final chat message  = message_list.get(position);



       // display name in list


        if(message.getType().equals("user")){
            holder.text_message_name.setText(""+message.getName());

        }
        else {

            holder.text_message_name.setText(""+message.getDoctor_name());



        }

        holder.text_message_body.setText(""+message.getMessage());







    }



    @Override
    public int getItemCount() {
        return message_list.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView text_message_name, text_message_body;
        LinearLayout main;

        Doctor doctor;


        public  HolderData (View view)

        {
            super(view);

            text_message_name = (TextView) view.findViewById(R.id.text_message_name);
            text_message_body = (TextView) view.findViewById(R.id.text_message_body);

            //doctor_id_textview = (TextView) view.findViewById(R.id.Emp_id);





        }
    }
}
