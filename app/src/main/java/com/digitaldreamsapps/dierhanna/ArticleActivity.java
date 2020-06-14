package com.digitaldreamsapps.dierhanna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitaldreamsapps.dierhanna.models.News;
import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.news);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
