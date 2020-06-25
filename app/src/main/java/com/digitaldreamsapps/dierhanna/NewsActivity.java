package com.digitaldreamsapps.dierhanna;


import android.content.Intent;
import android.os.Bundle;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.News;

public class NewsActivity extends BaseItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(getResources().getString(R.string.news), true, true);


        itemsAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public <T> void onItemClicked(T item) {
                Intent intent = new Intent(NewsActivity.this, ArticleActivity.class);
                intent.putExtra("news", (News) item);
                startActivity(intent);
            }
        });

    }
}
