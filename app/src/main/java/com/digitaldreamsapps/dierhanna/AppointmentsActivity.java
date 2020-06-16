package com.digitaldreamsapps.dierhanna;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.digitaldreamsapps.dierhanna.adapters.AppointmentsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnAppointmentClicked;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedFireBase;
import com.digitaldreamsapps.dierhanna.models.Appointment;
import com.google.firebase.database.DataSnapshot;


import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends BaseActivity {

    private AppointmentsAdapter appointmentsAdapter;
    private List<Appointment> appointments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        setToolbar(getResources().getString(R.string.appointment),true,true);
        setOnSupportNavigateUp(true);


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
        setViewModel("Appointments", new OnDataChangedFireBase() {
            @Override
            public void onDataChanged(DataSnapshot dataSnapshot) {
                appointments.clear();
                appointmentsAdapter.notifyDataSetChanged();
                for(DataSnapshot readData: dataSnapshot.getChildren()){
                    Appointment data = readData.getValue(Appointment.class);
                    appointments.add(data);

                }

                appointmentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNoDataReceived() {

            }
        });


    }


}
