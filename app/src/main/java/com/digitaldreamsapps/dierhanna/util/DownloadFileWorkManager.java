package com.digitaldreamsapps.dierhanna.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.digitaldreamsapps.dierhanna.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileWorkManager extends Worker {
    private static final String CHANNEL_ID = "Download File";


    public DownloadFileWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    private String downloadFileName;
    private URL downloadUrl ;


    @NonNull
    @Override
    public Result doWork() {

        NotificationCompat.Builder builder = null;
        NotificationManagerCompat notificationManager;
        downloadFileName= getInputData().getString("fileName");

        File pdfFile;
        try {
            downloadUrl = new URL(getInputData().getString("url"));
            File directory = new File(Environment.getExternalStorageDirectory() + "/" + "DierHanna");
            if (!directory.exists()) {
                directory.mkdir();

            }
             pdfFile = new File(Environment.getExternalStorageDirectory() + "/DierHanna/" + downloadFileName);
             notificationManager = NotificationManagerCompat.from(getApplicationContext());
            createNotificationChannel();
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(downloadFileName)
                    //.setContentText(downloadFileName)

                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);





            URLConnection urlConnection = downloadUrl.openConnection();

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


                builder.setProgress(100,percentage,false);
                notificationManager.notify(1, builder.build());


                fos.write(buffer, 0, len1);
            }
            fos.close();
            inputStream.close();
        } catch (IOException e) {
            builder.setContentTitle(getApplicationContext().getResources().getString(R.string.failed_to_download_file));
            e.printStackTrace();
            return Result.failure();
        }

        //sendNotification(pdfFile);

        builder.setContentTitle(getApplicationContext().getResources().getString(R.string.file_downloaded))
                .setContentTitle(downloadFileName)
                .setProgress(0,0,false);

        notificationManager.notify(1, builder.build());

        return Result.success();
    }



    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Dier hanna download file notification";
            String description = "download file";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
