package com.digitaldreamsapps.dierhanna;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.digitaldreamsapps.dierhanna.adapters.NewsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedFireBase;
import com.digitaldreamsapps.dierhanna.interfaces.OnNewsClicked;
import com.digitaldreamsapps.dierhanna.models.News;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity {
    private List<News> phones = new ArrayList<>();
    private NewsAdapter phonesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setToolbar(getResources().getString(R.string.news),true,true);

       setOnSupportNavigateUp(true);



        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        phonesAdapter=new NewsAdapter(phones);
        phonesAdapter.setOnNewsClicked(new OnNewsClicked() {
            @Override
            public void onClick(News news) {
                Intent intent = new Intent(NewsActivity.this,ArticleActivity.class);
                intent.putExtra("news",news);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(phonesAdapter);

        setViewModel("News", new OnDataChangedFireBase() {
            @Override
            public void onDataChanged(DataSnapshot dataSnapshot) {
                phones.clear();
                for(DataSnapshot readData: dataSnapshot.getChildren()){
                    News data = readData.getValue(News.class);
                    phones.add(data);

                }
                phonesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNoDataReceived() {

            }
        });





    }


}
