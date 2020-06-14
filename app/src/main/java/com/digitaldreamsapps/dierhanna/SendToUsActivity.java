package com.digitaldreamsapps.dierhanna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.digitaldreamsapps.dierhanna.models.SendToUs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendToUsActivity extends AppCompatActivity {

    EditText nameEdit , phoneEdit , titleEdit , messageEdit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.send_to_us);
        nameEdit = findViewById(R.id.nameedit);
        phoneEdit = findViewById(R.id.phoneedit);
        titleEdit = findViewById(R.id.titleedit);
        messageEdit = findViewById(R.id.messageedit);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void send(View view) {
        String name = nameEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String title = titleEdit.getText().toString();
        String message = messageEdit.getText().toString();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("sendToUs");
        DatabaseReference newPostRef = mDatabase.push();
        newPostRef.setValue(new SendToUs(name,phone,title,message)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                nameEdit.setText("");
                phoneEdit.setText("");
                titleEdit.setText("");
                messageEdit.setText("");
                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.send_success);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.send_failed);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}