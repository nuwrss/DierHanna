package com.digitaldreamsapps.dierhanna.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.digitaldreamsapps.dierhanna.R;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.News;
import com.squareup.picasso.Picasso;

public class NewsViewHolder extends BaseViewHolder {
    ImageView newsImg ;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        newsImg = itemView.findViewById(R.id.imgnews);
    }

    @Override
    public void setDetails(Object item, OnItemClickedListener onItemClickedListener) {
        super.setDetails(item, onItemClickedListener);
        News news = (News)item;
        Picasso.get().load(news.getImage()).placeholder(R.mipmap.ic_launcher).into(newsImg);

    }


}
