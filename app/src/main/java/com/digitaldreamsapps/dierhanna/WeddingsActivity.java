package com.digitaldreamsapps.dierhanna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import com.digitaldreamsapps.dierhanna.adapters.WeddingsAdapter;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import com.digitaldreamsapps.dierhanna.viewmodels.WeddingsViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WeddingsActivity extends AppCompatActivity {
    private List<Wedding> phones = new ArrayList<>();
    private WeddingsAdapter phonesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weddings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.Weddings);



        final ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        phonesAdapter = new WeddingsAdapter(phones);
        recyclerView.setAdapter(phonesAdapter);

        WeddingsViewModel model = new ViewModelProvider(this).get(WeddingsViewModel.class);

        model.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    phones.clear();
                    for(DataSnapshot readData: dataSnapshot.getChildren()){
                        Wedding data = readData.getValue(Wedding.class);
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
