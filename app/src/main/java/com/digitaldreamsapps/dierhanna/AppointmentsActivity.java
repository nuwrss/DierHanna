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

import com.digitaldreamsapps.dierhanna.adapters.AppointmentsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnAppointmentClicked;
import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.digitaldreamsapps.dierhanna.viewmodels.AppointmentsViewModel;
import com.google.firebase.database.DataSnapshot;


import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    private AppointmentsAdapter appointmentsAdapter;
    private List<Appointment> appointments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.appointment);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        appointmentsAdapter= new AppointmentsAdapter(appointments);
        recyclerView.setAdapter(appointmentsAdapter);

        appointmentsAdapter.setOnAppointmentClicked(new OnAppointmentClicked() {
            @Override
            public void onClick(Appointment appointment) {
                String url = appointment.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        AppointmentsViewModel model = new ViewModelProvider(this).get(AppointmentsViewModel.class);

        model.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    appointments.clear();
                    appointmentsAdapter.notifyDataSetChanged();
                    for(DataSnapshot readData: dataSnapshot.getChildren()){
                        Appointment data = readData.getValue(Appointment.class);
                        appointments.add(data);

                    }

                    appointmentsAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
