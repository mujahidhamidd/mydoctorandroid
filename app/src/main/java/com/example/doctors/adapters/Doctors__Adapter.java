package com.example.doctors.adapters;

import android.content.Context;

import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doctors.R;
import com.example.doctors.details;
import com.example.doctors.models.Doctor;

import java.io.Serializable;
import java.util.List;


public class Doctors__Adapter extends RecyclerView.Adapter<Doctors__Adapter.HolderData>  {
    private List<Doctor> doctors_list;
    private Context context;
    private  int checkBoxClicked=0;


    public Doctors__Adapter(Context context, List<Doctor> items)
    {
        this.doctors_list = items;
        this.context = context;

    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_layout_row,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, final int position) {
       final Doctor doctor  = doctors_list.get(position);



       // display name in list
        holder.name_textview.setText(""+doctor.getName());

        holder.rating.setText(""+doctor.getRate());

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                //  new category_offers(request.getId());


                context.startActivity( new Intent( context.getApplicationContext(),
                        details.class).putExtra("doctor",doctor));


            }
        });




    }



    @Override
    public int getItemCount() {
        return doctors_list.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView name_textview, rating;
        LinearLayout main;

        Doctor doctor;


        public  HolderData (View view)

        {
            super(view);

            name_textview = (TextView) view.findViewById(R.id.name);
            main = itemView.findViewById(R.id.main);
            rating = (TextView) view.findViewById(R.id.rating);





        }
    }
}
