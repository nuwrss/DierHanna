package com.digitaldreamsapps.dierhanna;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.digitaldreamsapps.dierhanna.adapters.PhonesAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.interfaces.OnPhoneClickListener;
import com.digitaldreamsapps.dierhanna.models.Phones;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import java.util.List;

public class PhonesActivity extends BaseActivity {

    private List<Phones> phones = new ArrayList<>();
    private PhonesAdapter phonesAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phones);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();
        setToolbar(getResources().getString(R.string.phone_numbers),true,true);
        setOnSupportNavigateUp(true);



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

        setViewModel("Important phones", new OnDataChangedRepository() {



            @Override
            public void onDataChangedDataBase(Object o) {
                phones.clear();
                phones.addAll((List<Phones>)o);
                phonesAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNoDataReceived() {

            }
        },new Phones());


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
