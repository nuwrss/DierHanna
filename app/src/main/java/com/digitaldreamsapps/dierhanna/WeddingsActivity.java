package com.digitaldreamsapps.dierhanna;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.digitaldreamsapps.dierhanna.adapters.WeddingsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedFireBase;
import com.digitaldreamsapps.dierhanna.models.Wedding;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WeddingsActivity extends BaseActivity {
    private List<Wedding> phones = new ArrayList<>();
    private WeddingsAdapter phonesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weddings);
        setToolbar(getResources().getString(R.string.Weddings),true,true);
        setOnSupportNavigateUp(true);





        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        phonesAdapter = new WeddingsAdapter(phones);
        recyclerView.setAdapter(phonesAdapter);

        setViewModel("Weddings", new OnDataChangedFireBase() {
            @Override
            public void onDataChanged(DataSnapshot dataSnapshot) {
                phones.clear();
                for(DataSnapshot readData: dataSnapshot.getChildren()){
                    Wedding data = readData.getValue(Wedding.class);
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
