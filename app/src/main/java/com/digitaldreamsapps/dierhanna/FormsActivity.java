package com.digitaldreamsapps.dierhanna;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.digitaldreamsapps.dierhanna.adapters.FormsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedFireBase;
import com.digitaldreamsapps.dierhanna.interfaces.OnFormClickListener;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.util.DownloadTask;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FormsActivity extends BaseActivity {
    private List<Form> forms = new ArrayList<>();
    private FormsAdapter formsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
       setToolbar(getResources().getString(R.string.forms),true,true);
       setOnSupportNavigateUp(true);


        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        formsAdapter=new FormsAdapter(forms);
        recyclerView.setAdapter(formsAdapter);


        formsAdapter.setOnAppointmentClicked(new OnFormClickListener() {
            @Override
            public void onClick(Form form) {

                new DownloadTask(FormsActivity.this, form);


            }
        });

        setViewModel("Forms", new OnDataChangedFireBase() {
            @Override
            public void onDataChanged(DataSnapshot dataSnapshot) {
                forms.clear();
                for(DataSnapshot readData: dataSnapshot.getChildren()){
                    Form data = readData.getValue(Form.class);
                    forms.add(data);
                }
                formsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNoDataReceived() {

            }
        });

    }


}
