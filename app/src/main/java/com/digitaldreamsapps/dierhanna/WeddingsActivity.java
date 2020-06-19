package com.digitaldreamsapps.dierhanna;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import com.digitaldreamsapps.dierhanna.adapters.WeddingsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WeddingsActivity extends BaseActivity {
    private List<Wedding> phones = new ArrayList<>();
    private WeddingsAdapter phonesAdapter;
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
        phonesAdapter = new WeddingsAdapter(phones);
        recyclerView.setAdapter(phonesAdapter);

        setViewModel("Weddings", new OnDataChangedRepository() {
            @Override
            public void onDataChangedFirebase(DataSnapshot dataSnapshot) {
                phones.clear();
                for(DataSnapshot readData: dataSnapshot.getChildren()){
                    Wedding data = readData.getValue(Wedding.class);
                    phones.add(data);

                }
                phonesAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onDataChangedDataBase(Object o) {

            }

            @Override
            public void onNoDataReceived() {

            }

        },new Wedding());


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
