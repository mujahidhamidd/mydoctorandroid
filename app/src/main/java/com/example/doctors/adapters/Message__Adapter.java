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
import com.example.doctors.details;
import com.example.doctors.models.Doctor;
import com.example.doctors.models.Message;

import java.util.List;


public class Message__Adapter extends RecyclerView.Adapter<Message__Adapter.HolderData>  {
    private List<Message> message_list;
    private Context context;
    private  int checkBoxClicked=0;


    public Message__Adapter(Context context, List<Message> items)
    {
        this.message_list = items;
        this.context = context;

    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, final int position) {
       final Message message  = message_list.get(position);



       // display name in list
        holder.message_textview.setText(""+message.getMessage());


        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                //  new category_offers(request.getId());


                context.startActivity( new Intent( context.getApplicationContext(),
                        ChatActivity.class).putExtra("message",message));


            }
        });




    }



    @Override
    public int getItemCount() {
        return message_list.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView user_textview, message_textview;
        LinearLayout main;

        Doctor doctor;


        public  HolderData (View view)

        {
            super(view);

            message_textview = (TextView) view.findViewById(R.id.body_message);
            user_textview = (TextView) view.findViewById(R.id.user_id);
            main = itemView.findViewById(R.id.main);
            //doctor_id_textview = (TextView) view.findViewById(R.id.Emp_id);





        }
    }
}
