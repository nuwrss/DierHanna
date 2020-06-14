package com.digitaldreamsapps.dierhanna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitaldreamsapps.dierhanna.models.News;
import com.digitaldreamsapps.dierhanna.util.Config;
import com.digitaldreamsapps.dierhanna.viewmodels.BaseActivity;
import com.digitaldreamsapps.dierhanna.viewmodels.MainViewModel;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.Locale;


public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setLanguage();
        setContentView( R.layout.activity_main );


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);


        final ProgressBar progressBar = findViewById(R.id.progressBar);

        final TextView mainText = findViewById(R.id.maintext);
        final ImageView imageView = findViewById(R.id.mainimage);




        MainViewModel model = new ViewModelProvider(this).get(MainViewModel.class);

        model.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    for(DataSnapshot readData: dataSnapshot.getChildren()){
                        News data = readData.getValue(News.class);
                        mainText.setText(data.getTitle());

                        Picasso.get().load(data.getImage()).into(imageView);


                    }
                }

                progressBar.setVisibility(View.GONE);
            }
        });




    }


    public void appointmentclick(View view) {
        Intent intent = new Intent(this,AppointmentsActivity.class);
        startActivity(intent);
    }

    public void phonesActivity(View view) {
        Intent intent = new Intent(this,PhonesActivity.class);
        startActivity(intent);
    }

    public void formsClick(View view) {
        Intent intent = new Intent(this,FormsActivity.class);
        startActivity(intent);
    }

    public void weddingsClick(View view) {
        Intent intent = new Intent(this,WeddingsActivity.class);
        startActivity(intent);
    }

    public void newsClick(View view) {
        Intent intent = new Intent(this,NewsActivity.class);
        startActivity(intent);
    }

    public void historyClicked(View view) {




        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("type","memorials");
        startActivity(intent);


    }

    public void busClicked(View view) {
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void measurClicked(View view) {

        Intent intent = new Intent(this,WeatherActivity.class);
        startActivity(intent);

    }

    public void busniessClicked(View view) {
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("type","Business");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.action_arabic:{
                saveLanguage("ar");
                return  true;
            }


            case R.id.action_english:{
                saveLanguage("en");
                return  true;
            }


            case R.id.action_hebrew:{
                saveLanguage("iw");
                return  true;
            }


            default:
                return super.onOptionsItemSelected(item);


        }

    }


    public void sendToUsclicked(View view) {
        Intent intent = new Intent(this,SendToUsActivity.class);

        startActivity(intent);
    }
}
