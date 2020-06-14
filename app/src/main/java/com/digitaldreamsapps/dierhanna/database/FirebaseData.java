package com.digitaldreamsapps.dierhanna.database;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.digitaldreamsapps.dierhanna.models.Department;
import com.digitaldreamsapps.dierhanna.models.DepartmentCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class FirebaseData {
    private static FirebaseData firebaseData;
    private static DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
    public static FirebaseData getInstance(){
        if (firebaseData==null){
            firebaseData=new FirebaseData();

        }
        return firebaseData;
    }

    public  void addCategoryDepartments(final DepartmentCategory departmentCategory, Bitmap bmp){
        final String key=  firebaseDatabase.child( "departments" ).push().getKey();
        departmentCategory.setUid( key );
        if (bmp==null){
            firebaseDatabase.child( "departments" ).child( key ).setValue( departmentCategory );
            return;
        }
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference storageReference = firebaseStorage.getReference().child("images/departmentcategory/" + key+ "/catimage/" + UUID.randomUUID().toString());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        storageReference.putBytes(data).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        departmentCategory.setPicUrl( uri.toString() );

                        firebaseDatabase.child( "departments" ).child( key ).setValue( departmentCategory );


                    }
                });


            }
        });





    }

    public  void addDepartment(final DepartmentCategory departmentCategory,final Department department,Bitmap bmp){
        final String key=  firebaseDatabase.child( departmentCategory.getUid() ).push().getKey();
        department.setUid( key );
        if (bmp==null){
            firebaseDatabase.child( "departments" ).child( departmentCategory.getUid() ).child( key ).setValue( department );
            return;
        }
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference storageReference = firebaseStorage.getReference().child("images/department/" + key+ "/mainimage/" + UUID.randomUUID().toString());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        storageReference.putBytes(data).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        department.setPicUrl( uri.toString() );

                        firebaseDatabase.child( "departments" ).child( departmentCategory.getUid() ).child( key ).setValue( department );


                    }
                });


            }
        });




    }
}
