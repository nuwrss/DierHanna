package com.digitaldreamsapps.dierhanna.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.models.BaseItem;

public class BaseViewHolder <T> extends RecyclerView.ViewHolder {
    private TextView title;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
    }
    public void setDetails(T item){
        title.setText(((BaseItem)item).getTitle());
    }
}
