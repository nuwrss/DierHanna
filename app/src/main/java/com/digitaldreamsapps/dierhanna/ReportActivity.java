package com.digitaldreamsapps.dierhanna;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.digitaldreamsapps.dierhanna.adapters.ReportCatAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.interfaces.OnReportCatClicked;
import com.digitaldreamsapps.dierhanna.models.ReportCat;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends BaseActivity {
    private ShimmerFrameLayout shimmerFrameLayout;
    private ArrayList<ReportCat>reportCats=new ArrayList<>();
    private ReportCatAdapter reportCatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setToolbar(getResources().getString(R.string.report),true,true);
        setOnSupportNavigateUp(true);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        reportCatAdapter=new ReportCatAdapter(reportCats);
        reportCatAdapter.setOnReportCatClicked(new OnReportCatClicked() {
            @Override
            public void onClick(ReportCat reportCat) {

            }
        });
        recyclerView.setAdapter(reportCatAdapter);
        setViewModel("Report Categories", new OnDataChangedRepository() {



            @Override
            public void onDataChangedDataBase(Object o) {
                reportCats.clear();
                reportCats.addAll((List<ReportCat>)o);
                reportCatAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNoDataReceived() {

            }
        },new ReportCat());
    }
}