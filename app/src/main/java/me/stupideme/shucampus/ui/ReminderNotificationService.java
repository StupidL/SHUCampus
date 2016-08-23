package me.stupideme.shucampus.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import me.stupideme.shucampus.R;
import me.stupideme.shucampus.ui.ReminderDetailActivity;


public class ReminderNotificationService extends Service {
    public ReminderNotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle("SHUCampus提醒")
                .setOnlyAlertOnce(true)
                ;
        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;

        Intent notifyIntent = new Intent(this, ReminderDetailActivity.class);
        notifyIntent.putExtras(intent);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        builder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notification);
        return START_STICKY;
    }

}
