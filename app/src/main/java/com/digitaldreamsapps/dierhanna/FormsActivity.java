package com.digitaldreamsapps.dierhanna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.digitaldreamsapps.dierhanna.adapters.FormsAdapter;
import com.digitaldreamsapps.dierhanna.adapters.PhonesAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnFormClickListener;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.digitaldreamsapps.dierhanna.util.DownloadTask;
import com.digitaldreamsapps.dierhanna.viewmodels.FormsViewModel;
import com.digitaldreamsapps.dierhanna.viewmodels.PhonesViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FormsActivity extends AppCompatActivity {
    private List<Form> forms = new ArrayList<>();
    private FormsAdapter formsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.forms);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        formsAdapter=new FormsAdapter(forms);
        recyclerView.setAdapter(formsAdapter);


        formsAdapter.setOnAppointmentClicked(new OnFormClickListener() {
            @Override
            public void onClick(Form form) {

                new DownloadTask(FormsActivity.this, form);


            }
        });

        FormsViewModel model = new ViewModelProvider(this).get(FormsViewModel.class);

        model.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    forms.clear();
                    for(DataSnapshot readData: dataSnapshot.getChildren()){
                        Form data = readData.getValue(Form.class);
                        forms.add(data);

                    }
                }

                formsAdapter.notifyDataSetChanged();

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
