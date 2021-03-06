package com.digitaldreamsapps.dierhanna;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.util.ConnectionStatus;
import com.digitaldreamsapps.dierhanna.viewmodels.BaseViewModel;
import java.util.Locale;


import static com.digitaldreamsapps.dierhanna.util.Config.setLanguageConfig;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private BaseViewModel baseViewModel;
    private boolean navigateUp = false;

    public boolean isInternetConnection() {
        return internetConnection;
    }

    public void setInternetConnection(boolean internetConnection) {
        this.internetConnection = internetConnection;
    }

    private boolean internetConnection = true;

    public void setLanguage(){
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);


         String l = sharedPreferences.getString("language", "");

         if (l.equals(Locale.getDefault().getLanguage())){

             return;
         }
        Locale locals[] =Locale.getAvailableLocales();

         if (locals[0].getLanguage().equals(l)){

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

    public void setViewModel(String childFireBase, final OnDataChangedRepository onDataChangedRepository){

        if (baseViewModel==null) {
            baseViewModel = new ViewModelProvider(this).get(BaseViewModel.class);
            baseViewModel.setChildFirebase(childFireBase,this);
        }
        registerViewModel(onDataChangedRepository);


    }

    public void unRegisterViewModel(){
        if (baseViewModel!=null)
            baseViewModel.getData().removeObservers(this);
    }

    private<T> void  registerViewModel(final OnDataChangedRepository onDataChangedRepository){
        showProgressBar();

        if (baseViewModel.getData().hasActiveObservers()){
            hideProgressBar();
            return;
        }
        baseViewModel.getConnectionStatusLiveData().observe(this, new Observer<ConnectionStatus>() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                hideProgressBar();
                if (!internetConnection) return;
                setInternetConnection(connectionStatus.isConnected());
                if (!connectionStatus.isConnected()){


                    showMessage(getResources().getString(R.string.internet_problem));


                }else{

                }
            }
        });
        baseViewModel.getData().observe(this, new Observer<T>() {
            @Override
            public void onChanged(T t) {

                    onDataChangedRepository.onDataChangedDataBase(t);


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



        Locale locale = new Locale(language);

        Locale.setDefault(locale);

        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());

        config.setLocale(locale);
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        createConfigurationContext(config);


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

    public void showMessage(String msg){
        final Dialog dialogView = new Dialog(this);
        dialogView.setContentView(R.layout.message_dialog);
        dialogView.setTitle("");

        TextView messageTxt = dialogView.findViewById(R.id.message);
        messageTxt.setText(msg);
        Button submitButton = dialogView.findViewById(R.id.buttonOk);
        submitButton.setText(getResources().getString(R.string.close));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView.cancel();
            }
        });
        dialogView.show();


    }



}
