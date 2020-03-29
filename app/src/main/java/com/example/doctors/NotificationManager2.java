package com.example.doctors;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;


@SuppressWarnings("deprecation")
public class NotificationManager2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String Title = "My Doctor App";
        String content ="your apoint with doctor is now ";

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_stat)
                        .setContentTitle(Title)
                        .setContentText(content).setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + context.getPackageName() + "/raw/notify"));

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}