package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.viewholders.BaseViewHolder;

import java.util.List;


public class  ItemsAdapter<T> extends RecyclerView.Adapter  {
    private static int TYPE_APPOINTMENT = 1;
    private static int TYPE_BUSCAT = 2;
    private static int TYPE_FORMS = 3;
    private static int TYPE_NEWS = 4;
    private static int TYPE_REPORTCATS = 5;
    private static int TYPE_WEDDING = 6;

    private List<T> itemList;

    public ItemsAdapter(List<T> itemList){
        this.itemList = itemList;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    private OnItemClickedListener onItemClickedListener;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaseViewHolder viewHolder = null;
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.base_cell, parent, false);
        viewHolder = new BaseViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        switch(getItemViewType(position)) {
            default:
                BaseViewHolder viewHolder = (BaseViewHolder)holder;
                viewHolder.setDetails(itemList.get(position));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickedListener.onItemClicked(itemList.get(position));
                    }
                });
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
