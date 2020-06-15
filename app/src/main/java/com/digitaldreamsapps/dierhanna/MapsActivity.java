package com.digitaldreamsapps.dierhanna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.digitaldreamsapps.dierhanna.adapters.BusCatAdapter;
import com.digitaldreamsapps.dierhanna.adapters.DierInfoWindowAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnBusnissCatClicked;
import com.digitaldreamsapps.dierhanna.models.BusinessCat;
import com.digitaldreamsapps.dierhanna.models.Place;
import com.digitaldreamsapps.dierhanna.models.Business;
import com.digitaldreamsapps.dierhanna.models.Memorial;
import com.digitaldreamsapps.dierhanna.viewmodels.MapMemorialsViewModel;
import com.digitaldreamsapps.dierhanna.viewmodels.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private MapViewModel model;
    private MapMemorialsViewModel mapMemorialsViewModel;
    private ProgressBar progressBar;
    private ArrayList<Marker>markers=new ArrayList<>();
    private ArrayList<BusinessCat>businessCats=new ArrayList<>();
    private ArrayList<Place>searchPlaces=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private LinearLayoutManager HorizontalLayout;
    private BusCatAdapter busCatAdapter;
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        progressBar = findViewById(R.id.progress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.business);

        busCatAdapter= new BusCatAdapter(businessCats);
        busCatAdapter.setOnBusnissCatClicked(new OnBusnissCatClicked() {
            @Override
            public void onClick(BusinessCat businessCat) {
                categorySelected(businessCat);
            }
        });

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recycler);
        RecyclerViewLayoutManager
                = new LinearLayoutManager(
                getApplicationContext());

        
        recyclerView.setLayoutManager(
                RecyclerViewLayoutManager);

        HorizontalLayout
                = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(busCatAdapter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);


            searchQuery(query);

        }

    }
    private void showAll(){
        Busness();
    }
    private void categorySelected(BusinessCat businessCat){
        model.getdataSnapshotLiveData().removeObservers(this);

        searchPlaces.clear();
        for (Marker marker : markers){
            marker.remove();

        }
        markers.clear();
        for (final Place place : businessCat.getPlaces()) {

            try {
                final LatLng sydney = new LatLng((place.getLat()), (place.getLongt()));

                if (place.getMarker() == null) {


                    Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(place.getName()));

                    marker.setTag(place);
                   
                    markers.add(marker);


                } else {

                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                    .position(sydney).title(place.getName()));
                            marker.setTag(place);
                           
                            markers.add(marker);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(place.getName()));

                            marker.setTag(place);
                           
                            markers.add(marker);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    };


                    Picasso.get().load(place.getMarker()).into(target);
                }
            } catch (NullPointerException e) {

            }

        }

    }
    private void searchQuery(String query) {
        model.getdataSnapshotLiveData().removeObservers(this);
        searchPlaces.clear();
        for (Marker marker : markers){
            marker.remove();

        }
        
        markers.clear();

        for (BusinessCat businessCat : businessCats) {
            for (Place place : businessCat.getPlaces()) {
                if (place.getName().contains(query) || place.getDescription().contains(query) || place.getName().toUpperCase().contains(query.toUpperCase()) || place.getDescription().toUpperCase().contains(query.toUpperCase())) {
                    searchPlaces.add(place);

                }

            }
        }

        for (final Place place : searchPlaces){







                try {
                    final LatLng sydney = new LatLng((place.getLat()), (place.getLongt()));

                    if (place.getMarker() == null) {


                        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(place.getName()));

                        marker.setTag(place);
                       
                        markers.add(marker);


                    } else {

                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                        .position(sydney).title(place.getName()));
                                marker.setTag(place);
                               
                                markers.add(marker);
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(place.getName()));

                                marker.setTag(place);
                               
                                markers.add(marker);
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };


                        Picasso.get().load(place.getMarker()).into(target);
                    }
                } catch (NullPointerException e) {

                }

        }
    }

    private void Busness() {
        if (model==null)
        model = new ViewModelProvider(this).get(MapViewModel.class);
        DierInfoWindowAdapter customInfoWindow = new DierInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindow);

        model.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (Marker marker : markers){
                        marker.remove();

                    }
                    markers.clear();
                    businessCats.clear();


                    

                    for (DataSnapshot readData : dataSnapshot.getChildren()) {
                        BusinessCat businessCat = new BusinessCat();
                        businessCat.setDetails(readData);
                        
                        for (DataSnapshot snapshot : readData.getChildren()){
                            if (snapshot.getKey().equals("nameAr")||snapshot.getKey().equals("nameEn")||snapshot.getKey().equals("nameHe")||snapshot.getKey().equals("icon")){

                                continue;
                            }
                            for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                final Business data = new Business();
                                data.setDetails(snapshot1);
                                
                                businessCat.addPlace(data);



                                try {
                                    final LatLng sydney = new LatLng((data.getLat()), (data.getLongt()));

                                    if (data.getMarker() == null) {


                                        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(data.getName()));

                                        marker.setTag(data);
                                       
                                        markers.add(marker);


                                    } else {

                                        Target target = new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                Marker marker = mMap.addMarker(new MarkerOptions()
                                                        .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                                                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                                        .position(sydney).title(data.getName()));
                                                marker.setTag(data);
                                               
                                                markers.add(marker);
                                            }

                                            @Override
                                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                                Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(data.getName()));

                                                marker.setTag(data);
                                               
                                                markers.add(marker);
                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                                            }
                                        };


                                        Picasso.get().load(data.getMarker()).into(target);
                                    }
                                } catch (NullPointerException e) {

                                }
                            }
                            businessCats.add(businessCat);

                        }






                    }
                }

                busCatAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);


            }
        });
    }

    

  

    private void Memorials() {

        mapMemorialsViewModel = new ViewModelProvider(this).get(MapMemorialsViewModel.class);
        DierInfoWindowAdapter customInfoWindow = new DierInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(customInfoWindow);

        mapMemorialsViewModel.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {

                    for (DataSnapshot readData : dataSnapshot.getChildren()) {
                        Memorial data = new Memorial();
                        data.setDetails(readData);
                        
                        try {
                            LatLng sydney = new LatLng((data.getLat()), (data.getLongt()));
                            Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(data.getName()));
                            marker.setTag(data);
                           
                        } catch (NullPointerException e) {

                        }
                    }
                }

                progressBar.setVisibility(View.GONE);


            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_map)));

        if (!success) {
            Log.e("error", "Style parsing failed.");
        }

        String type = getIntent().getStringExtra("type");
        if (type.equals("Business")) {
            Busness();
        } else {
            Memorials();
        }


       


        LatLng sydney = new LatLng((32.862483), (35.366343));


        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        showDialog(marker);

        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        showDialog(marker);


    }

    private void showDialog(Marker marker) {
        final Place place = (Place) marker.getTag();


        final Dialog dialogView = new Dialog(this);
        dialogView.setContentView(R.layout.busniss_dialog);
        dialogView.setTitle("Title...");

        TextView name = dialogView.findViewById(R.id.name);
        name.setText(place.getName());

        TextView desc = dialogView.findViewById(R.id.desc);
        desc.setText(place.getDescription());

        ImageView img = dialogView.findViewById(R.id.img);

        ImageView phone = dialogView.findViewById(R.id.phone);

        ImageView loc = dialogView.findViewById(R.id.loc);

        ImageView read = dialogView.findViewById(R.id.read);
        img.setVisibility(View.GONE);
        if (place.isShowimageinsidedialogwindow() && place.getPic() != null) {
            img.setVisibility(View.VISIBLE);
            Picasso.get().load(place.getPic()).into(img);
        }

        if (place instanceof Memorial) {
            phone.setVisibility(View.GONE);
            read.setVisibility(View.VISIBLE);
            read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
            read.setVisibility(View.GONE);
            phone.setVisibility(View.VISIBLE);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    phonNumber=place.getPhones().get(0);
                    makeCall();
                }
            });
        }


        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// Or map point based on latitude/longitude
                Uri location = Uri.parse("geo:" + place.getLat() + "," + place.getLongt()); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                Intent chooser = Intent.createChooser(mapIntent, "Open with :");
                startActivity(chooser);
            }
        });


        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        buttonOk.setText(getResources().getString(R.string.close));

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.dismiss();
            }
        });


        dialogView.show();
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

    private void requestForCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE))
        {
        }
        else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }
private final int PERMISSION_REQUEST_CODE =909;
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
}
