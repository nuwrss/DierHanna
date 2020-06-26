package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaldreamsapps.dierhanna.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesViewPagerAdapter extends RecyclerView.Adapter<ImagesViewPagerAdapter.ViewHolder> {
    private ArrayList<String>imagesList;

    public ImagesViewPagerAdapter(ArrayList<String> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImagesViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_cell_viewpager, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewPagerAdapter.ViewHolder holder, int position) {

        Picasso.get().load(imagesList.get(position)).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);


        }
    }
}
