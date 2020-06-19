package com.digitaldreamsapps.dierhanna;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.digitaldreamsapps.dierhanna.adapters.NewsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.interfaces.OnNewsClicked;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity {
    private List<News> news = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setToolbar(getResources().getString(R.string.news),true,true);

       setOnSupportNavigateUp(true);



        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        newsAdapter=new NewsAdapter(news);
        newsAdapter.setOnNewsClicked(new OnNewsClicked() {
            @Override
            public void onClick(News news) {
                Intent intent = new Intent(NewsActivity.this,ArticleActivity.class);
                intent.putExtra("news",news);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(newsAdapter);

        setViewModel("News", new OnDataChangedRepository() {
            @Override
            public void onDataChangedFirebase(DataSnapshot dataSnapshot) {
                news.clear();
                for(DataSnapshot readData: dataSnapshot.getChildren()){
                    News data = readData.getValue(News.class);
                    news.add(data);

                }
                insertDataToDataBase(news);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDataChangedDataBase(Object o) {
                news.clear();
                news.addAll((List<News>)o);
                newsAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNoDataReceived() {

            }

        }, new News());





    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }



}
