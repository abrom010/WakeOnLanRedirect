package com.example.wakeonlanredirect;

import android.content.*;
import android.os.*;
import android.app.*;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class PacketRedirectService extends Service {
    PacketRedirect pr= new PacketRedirect(MainActivity.port);

    @Override
    public void onDestroy(){
        pr.stop();
        System.out.println("STOPPED");
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    private static final int ID_SERVICE = 101;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Thread redirect = new Thread(pr);
        redirect.start();
        System.out.println("Started on port "+MainActivity.port+" with address "+MainActivity.address);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_MIN)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentTitle("Running on port "+MainActivity.port+" with address "+MainActivity.address)
                .build();

        startForeground(ID_SERVICE, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(NotificationManager notificationManager){
        String channelId = "my_service_channelid";
        String channelName = "My Foreground Service";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }
}