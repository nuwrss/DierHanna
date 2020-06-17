package com.digitaldreamsapps.dierhanna.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileWorkManager extends Worker {
    public DownloadFileWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    private String downloadFileName;
    private URL downloadUrl ;


    @NonNull
    @Override
    public Result doWork() {
        downloadFileName= getInputData().getString("fileName");


        try {
            downloadUrl = new URL(getInputData().getString("url"));
            File directory = new File(Environment.getExternalStorageDirectory() + "/" + "DierHanna");
            if (!directory.exists()) {
                directory.mkdir();

            }
            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/DierHanna/" + downloadFileName);
            URLConnection urlConnection = null;

            urlConnection = downloadUrl.openConnection();

            urlConnection.connect();
            int fileLength = urlConnection.getContentLength();
            FileOutputStream fos = new FileOutputStream(pdfFile);
            InputStream inputStream = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int len1;
            long total = 0;
            while ((len1 = inputStream.read(buffer)) > 0) {
                total += len1;
                int percentage = (int) ((total * 100) / fileLength);
                LiveDataHelperWorkManager.getInstance().updateDownloadPer(percentage);
                fos.write(buffer, 0, len1);
            }
            fos.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }


        return Result.success();
    }
}
