package com.example.jamesforey.mobileappscw2;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.content.Context;
import android.content.ContextWrapper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

class NotificationHelper extends ContextWrapper {
    private NotificationManager notifyManager;
    public static final String CHANNEL_ONE_ID = "com.example.jamesforey.mobileappscw2.ONE";
    public static final String CHANNEL_ONE_NAME = "Channel One";


    //Create your notification channels//
    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, notifyManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(notificationChannel);

    }

    //Create the notification thatâ€™ll be posted to Channel One//
    public NotificationCompat.Builder getNotification1(String title, String body) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.meeting_notify)
                .setNumber(3)
                .setAutoCancel(true);
    }


    public void notify(int id, NotificationCompat.Builder notification) {
        getManager().notify(id, notification.build());
    }

    //Send your notifications to the NotificationManager system service//
    private NotificationManager getManager() {
        if (notifyManager == null) {
            notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifyManager;
    }
}
