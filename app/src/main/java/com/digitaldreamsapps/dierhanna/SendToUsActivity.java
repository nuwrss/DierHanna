package com.digitaldreamsapps.dierhanna;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.digitaldreamsapps.dierhanna.models.ReportCat;
import com.digitaldreamsapps.dierhanna.models.SendToUs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendToUsActivity extends BaseActivity {

    private EditText nameEdit , phoneEdit , titleEdit , messageEdit ;
    private TextView explainText;
    private ReportCat reportCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_us);
        setToolbar(getResources().getString(R.string.send_to_us),true,true);
        setOnSupportNavigateUp(true);
        nameEdit = findViewById(R.id.nameedit);
        phoneEdit = findViewById(R.id.phoneedit);
        titleEdit = findViewById(R.id.titleedit);
        messageEdit = findViewById(R.id.messageedit);
        explainText = findViewById(R.id.explain);

         reportCat =(ReportCat) getIntent().getSerializableExtra("reportCat");
        if (reportCat!=null){
            explainText.setText(reportCat.getName());
        }

    }


    public void send(View view) {
        String name = nameEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String title = titleEdit.getText().toString();
        String message = messageEdit.getText().toString();
        DatabaseReference mDatabase ;

         if (reportCat!=null){
             mDatabase = FirebaseDatabase.getInstance().getReference().child("Reports").child(reportCat.getNameAr());

         }else {
             mDatabase = FirebaseDatabase.getInstance().getReference().child("sendToUs");

         }
        DatabaseReference newPostRef = mDatabase.push();
        newPostRef.setValue(new SendToUs(name,phone,title,message)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                nameEdit.setText("");
                phoneEdit.setText("");
                titleEdit.setText("");
                messageEdit.setText("");

                showMessage(getResources().getString(R.string.send_success));


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                showMessage(getResources().getString(R.string.send_failed));


            }
        });

    }


}