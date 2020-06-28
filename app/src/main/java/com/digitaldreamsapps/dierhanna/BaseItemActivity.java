package com.digitaldreamsapps.dierhanna;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.digitaldreamsapps.dierhanna.adapters.ItemsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.digitaldreamsapps.dierhanna.models.News;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import java.util.List;

public  class BaseItemActivity <T> extends BaseActivity {
    public ItemsAdapter itemsAdapter;
    private List<T> items = new ArrayList<>();
    private ShimmerFrameLayout shimmerFrameLayout;
    private String itemName;
    private final String appointmentItem = "Appointment";
    private final String anewsItem = "News";


    public static void startBaseItemActivity(Context context, String itemName , String title){
        Intent intent = new Intent(context,BaseItemActivity.class);
        intent.putExtra("itemName", itemName);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitybase_layout);
        itemName = getIntent().getStringExtra("itemName");

        String title = getIntent().getStringExtra("title");

        setToolbar(title,true,true);

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
        if (itemName.equals(appointmentItem)){
            setItemClicked();

        }

        if (itemName.equals(anewsItem)){
            setItemClicked();
        }

    }

    public void setItemClicked(){
        itemsAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public <T> void onItemClicked(T item) {

                if (itemName.equals(appointmentItem)){
                    Appointment appointment =(Appointment)item;
                    String url = appointment.getLink();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    return;
                }

                if (itemName.equals(anewsItem)){
                    Intent intent = new Intent(BaseItemActivity.this, ArticleActivity.class);
                    intent.putExtra("news", (News) item);
                    startActivity(intent);
                    return;
                }


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
