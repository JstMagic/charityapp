package com.kodemakers.charity.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.kodemakers.charity.R;


/**
 * Created by nkdroid on 24/9/18.
 */

public class NotificationHelper extends ContextWrapper {
    private NotificationManager notifManager;
    public static final String CHANNEL_ONE_ID = "com.kodemakers.charity.user.ONE";
    public static final String CHANNEL_ONE_NAME = "Channel One";

//Create your notification channels//

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, notifManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(notificationChannel);
        }

//        NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_TWO_ID,
//                CHANNEL_TWO_NAME, notifManager.IMPORTANCE_DEFAULT);
//        notificationChannel2.enableLights(false);
//        notificationChannel2.enableVibration(true);
//        notificationChannel2.setLightColor(Color.RED);
//        notificationChannel2.setShowBadge(false);
//        getManager().createNotificationChannel(notificationChannel2);

    }

//Create the notification that’ll be posted to Channel One//

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification1(String title, String body) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
//        }
    }

//Create the notification that’ll be posted to Channel Two//

//    public Notification.Builder getNotification2(String title, String body) {
//        return new Notification.Builder(getApplicationContext(), CHANNEL_TWO_ID)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setSmallIcon(R.drawable.alert)
//                .setAutoCancel(true);
//    }


    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

//Send your notifications to the NotificationManager system service//

    private NotificationManager getManager() {
        if (notifManager == null) {
            notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifManager;
    }
}