package com.digitaldreamsapps.dierhanna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.digitaldreamsapps.dierhanna.adapters.NewsAdapter;
import com.digitaldreamsapps.dierhanna.adapters.PhonesAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnNewsClicked;
import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.digitaldreamsapps.dierhanna.viewmodels.NewsViewModel;
import com.digitaldreamsapps.dierhanna.viewmodels.PhonesViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    private List<News> phones = new ArrayList<>();
    private NewsAdapter phonesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.news);


        final ProgressBar progressBar = findViewById(R.id.progressBar);
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


        NewsViewModel model = new ViewModelProvider(this).get(NewsViewModel.class);

        model.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    phones.clear();
                    for(DataSnapshot readData: dataSnapshot.getChildren()){
                        News data = readData.getValue(News.class);
                        phones.add(data);

                    }
                }

                phonesAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);


            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
