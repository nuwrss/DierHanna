package com.digitaldreamsapps.dierhanna;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.digitaldreamsapps.dierhanna.adapters.ItemsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseItemActivity <T> extends BaseActivity {
    public ItemsAdapter itemsAdapter;
    private List<T> items = new ArrayList<>();
    private ShimmerFrameLayout shimmerFrameLayout;
    private String itemName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitybase_layout);
        itemName = getIntent().getStringExtra("itemName");


        setOnSupportNavigateUp(true);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        itemsAdapter= new ItemsAdapter(items);
        recyclerView.setAdapter(itemsAdapter);

        setViewModel(itemName, new OnDataChangedRepository() {

            @Override
            public void onDataChangedDataBase(Object o) {

                items.clear();
                items.addAll((List<T>)o);
                itemsAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNoDataReceived() {

            }
        });
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
