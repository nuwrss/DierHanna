package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.BusinessCat;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BusCatAdapter extends RecyclerView.Adapter<BusCatAdapter.ViewHolder> {



    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public BusCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_cell, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BusinessCat businessCat = businessCats.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClicked(businessCat);
            }
        });

        Picasso.get().load(businessCat.getIcon()).into(holder.icon);

    }
    List<BusinessCat> businessCats;
    public BusCatAdapter(List<BusinessCat> businessCats){
        this.businessCats = businessCats;
    }



    @Override
    public int getItemCount() {
        return businessCats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {



        public ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);


            icon = itemView.findViewById(R.id.icon);
        }
    }
}
