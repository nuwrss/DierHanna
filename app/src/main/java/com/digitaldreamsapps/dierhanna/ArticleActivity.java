package com.digitaldreamsapps.dierhanna;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;

import com.digitaldreamsapps.dierhanna.adapters.ImagesViewPagerAdapter;
import com.digitaldreamsapps.dierhanna.models.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleActivity extends BaseActivity {
    private ViewPager2 imagesViewPager;
    private ArrayList<String> images = new ArrayList<>();
    private ImagesViewPagerAdapter imagesViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        News news = (News) getIntent().getSerializableExtra("news");
        Log.e("title",news.getTitle());
        setToolbar(news.getTitle(),true,true);
        setOnSupportNavigateUp(true);

        TextView title = findViewById(R.id.maintext);
        TextView textView = findViewById(R.id.text);

        imagesViewPager = findViewById(R.id.viewPager2);



        images.add(news.getImage());

        imagesViewPagerAdapter= new ImagesViewPagerAdapter(images);
        imagesViewPager.setAdapter(imagesViewPagerAdapter);







        title.setText(news.getTitle());
        textView.setText(news.getArticle());


    }


}
