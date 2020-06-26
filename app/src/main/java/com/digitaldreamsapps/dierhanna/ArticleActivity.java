package com.digitaldreamsapps.dierhanna;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import com.digitaldreamsapps.dierhanna.adapters.ImagesViewPagerAdapter;
import com.digitaldreamsapps.dierhanna.models.News;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ArticleActivity extends BaseActivity {
    private ViewPager2 imagesViewPager;

    private ImagesViewPagerAdapter imagesViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        News news = (News) getIntent().getSerializableExtra("news");

        setToolbar(news.getTitle(),true,true);
        setOnSupportNavigateUp(true);

        TextView title = findViewById(R.id.maintext);
        TextView textView = findViewById(R.id.text);

        imagesViewPager = findViewById(R.id.viewPager2);


       TabLayout tabLayout = findViewById(R.id.tbalayout);







        imagesViewPagerAdapter= new ImagesViewPagerAdapter(news.getImages());
        imagesViewPager.setAdapter(imagesViewPagerAdapter);

        new TabLayoutMediator(tabLayout, imagesViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                    }
                }).attach();







        title.setText(news.getTitle());
        textView.setText(news.getArticle());


    }


}
