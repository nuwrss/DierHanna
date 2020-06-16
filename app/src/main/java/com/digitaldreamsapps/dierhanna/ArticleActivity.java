package com.digitaldreamsapps.dierhanna;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldreamsapps.dierhanna.models.News;
import com.squareup.picasso.Picasso;

public class ArticleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        setToolbar(getResources().getString(R.string.news),true,true);
        setOnSupportNavigateUp(true);

        ImageView imageView = findViewById(R.id.mainimage);
        TextView title = findViewById(R.id.maintext);
        TextView textView = findViewById(R.id.text);

       News news = (News) getIntent().getSerializableExtra("news");



        Picasso.get()
                .load(news.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);

        title.setText(news.getTitle());
        textView.setText(news.getArticle());


    }


}
