package com.digitaldreamsapps.dierhanna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.digitaldreamsapps.dierhanna.adapters.PhonesAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnPhoneClickListener;
import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.digitaldreamsapps.dierhanna.viewmodels.MainViewModel;
import com.digitaldreamsapps.dierhanna.viewmodels.PhonesViewModel;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhonesActivity extends AppCompatActivity {

    private List<Phones> phones = new ArrayList<>();
    private PhonesAdapter phonesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.phone_numbers);


        final ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        phonesAdapter = new PhonesAdapter(phones);
        recyclerView.setAdapter(phonesAdapter);
        phonesAdapter.setOnPhoneClickListener(new OnPhoneClickListener() {
            @Override
            public void onPhoneNumberClick(String number) {

                phonNumber=number;
                makeCall();
            }

            @Override
            public void onEmailClick(String email) {
                Intent emailintent = new Intent(Intent.ACTION_SEND);
                emailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{ email});

                emailintent.setType("message/rfc822");

                startActivity(Intent.createChooser(emailintent, "Choose an Email client :"));
            }
        });

        PhonesViewModel model = new ViewModelProvider(this).get(PhonesViewModel.class);

        model.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    phones.clear();
                    for(DataSnapshot readData: dataSnapshot.getChildren()){
                        Phones data = readData.getValue(Phones.class);
                        phones.add(data);

                    }
                }

                phonesAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);


            }
        });
    }
    String phonNumber="";
    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phonNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestForCallPermission();
            return;
        }
        startActivity(callIntent);
    }
    private final int PERMISSION_REQUEST_CODE =909;
    private void requestForCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
        {
        }
        else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                }
                break;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
