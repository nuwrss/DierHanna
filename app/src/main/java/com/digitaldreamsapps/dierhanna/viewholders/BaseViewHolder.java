package com.digitaldreamsapps.dierhanna.viewholders;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.BaseItem;

public class BaseViewHolder <T> extends RecyclerView.ViewHolder {
    private TextView title;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
    }
    public void setDetails(final T item , final OnItemClickedListener onItemClickedListener){
        title.setText(((BaseItem)item).getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickedListener.onItemClicked(item);
            }
        });
    }
}
