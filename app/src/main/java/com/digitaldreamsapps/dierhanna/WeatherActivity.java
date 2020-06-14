package com.digitaldreamsapps.dierhanna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digitaldreamsapps.dierhanna.models.Measurements;
import com.digitaldreamsapps.dierhanna.viewmodels.MeasurementsViewModel;

import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    private MeasurementsViewModel measurementsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.mdadem);
        final TextView temp = findViewById(R.id.temp);
        final TextView lastupdated = findViewById(R.id.lastupdate);
        final TextView humidity = findViewById(R.id.humdity);
        final TextView wind = findViewById(R.id.wind);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        measurementsViewModel = new ViewModelProvider(this).get(MeasurementsViewModel.class);
        measurementsViewModel.getPostsList().observe(this, new Observer<List<Measurements>>() {
            @Override
            public void onChanged(List<Measurements> measurements) {
                progressBar.setVisibility(View.GONE);
                Log.e("tempr ", measurements.get(0).getTemp()+"");
                Log.e("last update ",measurements.get(0).getObsTimeLocal());
                temp.setText(measurements.get(0).getTemp()+"");
                lastupdated.setText(measurements.get(0).getObsTimeLocal());
                wind.setText(measurements.get(0).getWindSpeed()+"");
                humidity.setText(measurements.get(0).getHumidity()+"");
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
