package com.digitaldreamsapps.dierhanna.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.viewholders.BaseViewHolder;
import com.digitaldreamsapps.dierhanna.viewholders.FormsViewHolder;
import java.util.List;


public class  ItemsAdapter<T> extends RecyclerView.Adapter  {

    private final int BASE_TYPE =1 ;
    private final int FORM_TYPE =2 ;


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
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BaseViewHolder viewHolder = null;
        View v;

        if (viewType == BASE_TYPE) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.base_cell, parent, false);
            viewHolder = new BaseViewHolder(v);

        }else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.formcell, parent, false);
            viewHolder = new FormsViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

       T item = itemList.get(position);
        ((BaseViewHolder) holder).setDetails(item,onItemClickedListener);



    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof Form){
            return FORM_TYPE;
        }else{
            return BASE_TYPE;
        }

    }
}
