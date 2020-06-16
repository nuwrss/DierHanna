package com.digitaldreamsapps.dierhanna;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedFireBase;
import com.digitaldreamsapps.dierhanna.viewmodels.BaseViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.Locale;
import static com.digitaldreamsapps.dierhanna.util.Config.setLanguageConfig;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private BaseViewModel baseViewModel;
    private boolean navigateUp = false;

    public void setLanguage(){
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);


         String l = sharedPreferences.getString("language", "");
         if (l.equals(Locale.getDefault().getLanguage())){
             return;
         }


        saveLanguage(setLanguageConfig(l));
    }

    public void setToolbar(String title,boolean displayHomeAsUpEnabled,boolean displayShowHomeEnabled){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        getSupportActionBar().setDisplayShowHomeEnabled(displayShowHomeEnabled);
    }

    public void showProgressBar(){
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    public void setViewModel(String childFireBase, final OnDataChangedFireBase onDataChangedFireBase){

        if (baseViewModel==null) {
            baseViewModel = new ViewModelProvider(this).get(BaseViewModel.class);
            baseViewModel.setChildFirebase(childFireBase);
        }
        registerViewModel(onDataChangedFireBase);


    }

    public void unRegisterViewModel(){
        if (baseViewModel!=null)
            baseViewModel.getdataSnapshotLiveData().removeObservers(this);
    }

    private void registerViewModel(final OnDataChangedFireBase onDataChangedFireBase){
        showProgressBar();
        if (baseViewModel.getdataSnapshotLiveData().hasActiveObservers()){
            hideProgressBar();
            return;
        }
        baseViewModel.getdataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    onDataChangedFireBase.onDataChanged(dataSnapshot);
                }else{
                    onDataChangedFireBase.onNoDataReceived();
                }
                hideProgressBar();
            }
        });
    }

    public void saveLanguage(String language){
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putString("language",language);
        myEdit.commit();
        setLanguageConfig(language);
        reConfig(language);


    }

    public void reConfig(String language){

        Locale locale;

        locale = new Locale(language);
        Configuration config = new Configuration(getResources().getConfiguration());
        Locale.setDefault(locale);
        config.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        recreate();

    }

    public void setOnSupportNavigateUp(boolean navigateUp){
        this.navigateUp = navigateUp;
    }
    @Override
    public boolean onSupportNavigateUp() {
        if (navigateUp) {
            onBackPressed();
            return true;
        }
            return super.onSupportNavigateUp();

    }

}
