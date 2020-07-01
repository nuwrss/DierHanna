package com.digitaldreamsapps.dierhanna;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import com.digitaldreamsapps.dierhanna.adapters.ItemsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class WeddingsActivity extends BaseActivity {

    private List<Wedding> weddings = new ArrayList<>();
    private ItemsAdapter weddingsAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_weddings);
        setToolbar(getResources().getString(R.string.Weddings),true,true);
        setOnSupportNavigateUp(true);





        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();
        weddingsAdapter = new ItemsAdapter(weddings);
        recyclerView.setAdapter(weddingsAdapter);

        weddingsAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public <T> void onItemClicked(T item) {

            }
        });

        setViewModel("Wedding", new OnDataChangedRepository() {
            @Override
            public void onDataChangedDataBase(Object o) {
                weddings.clear();
                weddings.addAll((List<Wedding>)o);
                weddingsAdapter.notifyDataSetChanged();
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
