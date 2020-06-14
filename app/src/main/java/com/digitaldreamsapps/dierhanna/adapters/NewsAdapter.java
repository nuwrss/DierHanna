package com.digitaldreamsapps.dierhanna.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnFormClickListener;
import com.digitaldreamsapps.dierhanna.interfaces.OnNewsClicked;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

private OnNewsClicked onNewsClicked;

    public OnNewsClicked getOnNewsClicked() {
        return onNewsClicked;
    }

    public void setOnNewsClicked(OnNewsClicked onNewsClicked) {
        this.onNewsClicked = onNewsClicked;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_cell, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final News appointment = appointments.get(position);
        holder.itemDetail.setText(appointment.getTitle());
        Picasso.get()
                .load(appointment.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);
        Log.e("image",appointment.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewsClicked.onClick(appointment);
            }
        });

    }
    List<News> appointments;
    public NewsAdapter(List<News> appointments){
        this.appointments = appointments;
    }



    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView itemDetail;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            itemDetail =
                    (TextView)itemView.findViewById(R.id.appontmenttitle);

            imageView=itemView.findViewById(R.id.img);
        }
    }
}
