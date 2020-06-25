package com.digitaldreamsapps.dierhanna;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.models.News;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setLanguage();
        setContentView( R.layout.activity_main );


        setToolbar(getResources().getString(R.string.app_name),false,false);






        final TextView mainText = findViewById(R.id.maintext);
        final ImageView imageView = findViewById(R.id.mainimage);




        setViewModel("News", new OnDataChangedRepository() {


            @Override
            public void onDataChangedDataBase(Object o) {
                List<News>newsList = (List<News>)o;
                if (newsList.isEmpty()) return;
                News news = newsList.get(0);
                mainText.setText(news.getTitle());

                Picasso.get().load(news.getImage()).into(imageView);

            }

            @Override
            public void onNoDataReceived() {

            }



        });





    }




    public void appointmentclick(View view) {
        Intent intent = new Intent(this,AppointmentsActivity.class);
        intent.putExtra("itemName", "Appointment");
        startActivity(intent);
    }

    public void phonesActivity(View view) {
        Intent intent = new Intent(this,PhonesActivity.class);
        intent.putExtra("itemName", "Phones");
        startActivity(intent);
    }

    public void formsClick(View view) {
        Intent intent = new Intent(this,FormsActivity.class);
        intent.putExtra("itemName", "Form");
        startActivity(intent);
    }

    public void weddingsClick(View view) {
        Intent intent = new Intent(this,WeddingsActivity.class);
        intent.putExtra("itemName", "Wedding");
        startActivity(intent);
    }

    public void newsClick(View view) {
        Intent intent = new Intent(this,NewsActivity.class);
        intent.putExtra("itemName", "News");
        startActivity(intent);
    }

    public void reportClicked(View view) {
        Intent intent = new Intent(this,ReportActivity.class);

        startActivity(intent);
    }

    public void historyClicked(View view) {




        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("type","memorials");
        startActivity(intent);


    }



    public void busClicked(View view) {

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
