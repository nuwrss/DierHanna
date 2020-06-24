package com.digitaldreamsapps.dierhanna;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;
import com.digitaldreamsapps.dierhanna.adapters.FormsAdapter;
import com.digitaldreamsapps.dierhanna.interfaces.OnDataChangedRepository;
import com.digitaldreamsapps.dierhanna.interfaces.OnFormClickListener;
import com.digitaldreamsapps.dierhanna.models.Form;
import com.digitaldreamsapps.dierhanna.util.CheckForSDCard;
import com.digitaldreamsapps.dierhanna.util.DownloadFileWorkManager;
import com.digitaldreamsapps.dierhanna.util.LiveDataHelperWorkManager;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FormsActivity extends BaseActivity {
    private  final int PERMISSION_REQUEST_CODE_READ_STORAGE = 101 ;
    private List<Form> forms = new ArrayList<>();
    private FormsAdapter formsAdapter;
    private  final int PERMISSION_REQUEST_CODE_WRITE_STORAGE = 100;
    private ShimmerFrameLayout shimmerFrameLayout;
    private Form form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
       setToolbar(getResources().getString(R.string.forms),true,true);
       setOnSupportNavigateUp(true);
        File pdf = (File) getIntent().getSerializableExtra("file");
        if (pdf!=null){
            startRead(pdf);
        }



        RecyclerView recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        formsAdapter=new FormsAdapter(forms);
        recyclerView.setAdapter(formsAdapter);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();
        formsAdapter.setOnAppointmentClicked(new OnFormClickListener() {
            @Override
            public void onClick(Form form1) {
                form=form1;
                if (!CheckForSDCard.isSDCardPresent()){

                    showMessage(getResources().getString(R.string.sdCardNotAvailable));

                    return;
                }
                fileAlreadyDownloaded(form1);





            }
        });

        setViewModel("Forms", new OnDataChangedRepository() {




            @Override
            public void onDataChangedDataBase(Object o) {
                forms.clear();
                forms.addAll((List<Form>)o);
                formsAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);


            }

            @Override
            public void onNoDataReceived() {

            }

        },new Form());

    }

    private void fileAlreadyDownloaded(Form form1) {
        form =form1;
        checkReadExternalPermission();
    }

    private void checkReadExternalPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionReadExternal();
            return;
        }
        startRead();
    }

    private void startRead(File file) {

        Intent intent=new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText( this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }


    }
    private void startRead() {
        File directory = new File(Environment.getExternalStorageDirectory() + "/" + "DierHanna");
        if (!directory.exists()) {
            checkPermissionWriteExternal();
            return;
        }
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/DierHanna/" + form.getTitle()+".pdf");
        if (!pdfFile.exists()){
            checkPermissionWriteExternal();
            return;
        }
        Intent intent=new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(pdfFile);
        intent.setDataAndType(uri, "application/pdf");
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText( this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }


    }

    private void requestPermissionReadExternal() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
        }
        else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE_READ_STORAGE);
        }
    }

    private void checkPermissionWriteExternal() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionWriteExternal();
            return;
        }
        startDownload();


    }
    private void requestPermissionWriteExternal(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
        }
        else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE_WRITE_STORAGE);
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                }
                break;
            }
            case PERMISSION_REQUEST_CODE_READ_STORAGE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startRead();
                }
                break;
            }
        }
    }

    private void startDownload() {
        final ProgressDialog progressDialog= new ProgressDialog(FormsActivity.this);
        progressDialog.setIndeterminate(false);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.setCancelable(false);

        progressDialog.setMax(100);
        progressDialog.show();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        WorkRequest downloadFile =
                new OneTimeWorkRequest.Builder(DownloadFileWorkManager.class).setConstraints(constraints).setInputData(new Data.Builder()
                        .putString("fileName", form.getTitle()+".pdf")
                        .putString("url",form.getLink())
                        .build())
                        .build();
        if (!isInternetConnection()){
            progressDialog.dismiss();
            showMessage(getResources().getString(R.string.internet_problem));
        }
        workManager.enqueue(downloadFile);
        workManager.getWorkInfoByIdLiveData(downloadFile.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null) {
                    WorkInfo.State state = workInfo.getState();
                    if (state == WorkInfo.State.SUCCEEDED){
                        startRead();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }else{
                        if (state == WorkInfo.State.FAILED) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            showMessage(getResources().getString(R.string.problem_withDownload));
                        }
                    }
                    formsAdapter.notifyDataSetChanged();
                }
            }
        });
        LiveDataHelperWorkManager.getInstance().observePercentage().observe(FormsActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (progressDialog.isShowing()) {
                    progressDialog.setProgress(integer);
                    if (integer >= 100) {

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }


}
