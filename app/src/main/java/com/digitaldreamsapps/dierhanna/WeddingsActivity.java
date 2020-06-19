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
    private List<Wedding> weddings = new ArrayList<>();
    private WeddingsAdapter weddingsAdapter;
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
        weddingsAdapter = new WeddingsAdapter(weddings);
        recyclerView.setAdapter(weddingsAdapter);

        setViewModel("Weddings", new OnDataChangedRepository() {


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
