package com.example.doctors.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doctors.R;
import com.example.doctors.details;
import com.example.doctors.models.Article;
import com.example.doctors.models.Doctor;

import java.util.List;


public class Article_Adapter extends RecyclerView.Adapter<Article_Adapter.HolderData>  {
    private List<Article> articles_list;
    private Context context;
    private  int checkBoxClicked=0;


    public Article_Adapter(Context context, List<Article> items)
    {
        this.articles_list = items;
        this.context = context;

    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_layout_row,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, final int position) {
       final Article article  = articles_list.get(position);



       // display name in list
        holder.subject.setText(""+article.getSubject());

        holder.body.setText(""+article.getBody());

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                //  new category_offers(request.getId());


                context.startActivity( new Intent( context.getApplicationContext(),
                        details.class).putExtra("doctor",article));


            }
        });




    }



    @Override
    public int getItemCount() {
        return articles_list.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView subject, body;
        LinearLayout main;



        public  HolderData (View view)

        {
            super(view);

            subject = (TextView) view.findViewById(R.id.subject);
            main = itemView.findViewById(R.id.main);
            body = (TextView) view.findViewById(R.id.body);





        }
    }
}
