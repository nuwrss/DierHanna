package com.digitaldreamsapps.dierhanna;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.digitaldreamsapps.dierhanna.interfaces.OnItemClickedListener;
import com.digitaldreamsapps.dierhanna.models.Appointment;


public class AppointmentsActivity extends BaseItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setToolbar(getResources().getString(R.string.appointment),true,true);

        itemsAdapter.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public <T> void onItemClicked(T item) {
                Appointment appointment =(Appointment)item;
                String url = appointment.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

        });


    }

}
