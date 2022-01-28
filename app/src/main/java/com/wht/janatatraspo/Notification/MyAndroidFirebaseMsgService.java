package com.wht.janatatraspo.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wht.janatatraspo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;


public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {

    public static final String INTENT_FILTER = "INTENT_FILTER";
    //private RemindersDatabase db;
    private String image_path;
    private DatabaseSqliteHandler db;
    private JSONObject jsonObject;
    private String title = "", message, image;
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //MainActivity mainActivity = MainActivity.getInstance();
            Log.d("count", "count");
        }
    };

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        Log.d("TOken", "Refreshed token: " + s);
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        registerReceiver(myReceiver, new IntentFilter(MyAndroidFirebaseMsgService.INTENT_FILTER));
        Log.d("Notification1234", "onMessageReceived: " + remoteMessage.getData());
//        Log.d("Notification1234", "onMessageReceived: "+remoteMessage.getNotification().getChannelId());
        //Log.d("Notification1234", "From: " + remoteMessage.getFrom());

        try {

            jsonObject = new JSONObject(remoteMessage.getData());

            if (jsonObject.has("image")) {
                image = jsonObject.getString("image");
                //image = "http://wetap.in/community/assets/uploads/notifications/img_1594655188.png";
            }
            Log.d("Notification", "onMessageReceived: " + jsonObject.getString("title"));
            Log.d("Notification", "onMessageReceived: " + jsonObject.getString("body"));
            Log.d("Notification", "onMessageReceived: " + jsonObject.getString("link"));
            showNotification(
                    jsonObject.getString("title"),
                    jsonObject.getString("body"),
                    jsonObject.getString("link")
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(INTENT_FILTER);
        sendBroadcast(intent);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //change
        if (myReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }

    private void showNotification(String title, String message, String link) {
        Log.d("Notific", "showNotification: " + message + "," + title + "," + link);

        Intent intent = new Intent(this, ActivityNotification.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        db = DatabaseSqliteHandler.getInstance(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        db.insert_notification(title, message, image, link);
        int requestCode = ("someString" + System.currentTimeMillis()).hashCode();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_NO_CREATE);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + getPackageName() + "/" + R.raw.samsung_s);

       /* Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.user_background);
        String contentText = "A classic image processing test image.";
        String summaryText = "This mandrill is often used as a test image.";
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                .setSummaryText(summaryText)
                .bigPicture(bigPicture);*/

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.app_logo)
                .setSound(alarmSound)
                .setChannelId("vijendra")
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        MediaPlayer mp = MediaPlayer.create(MyAndroidFirebaseMsgService.this, R.raw.samsung_s);
        mp.start();
        if (jsonObject.has("image")) {
            if (image != null && !image.isEmpty() && !image.equals("null")) {
                Bitmap image = getBitmapFromURL(this.image);
                builder.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image).setSummaryText(message));
            }

        }
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.samsung_s);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("vijendra", "Your Notifications",
                    NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();


            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(sound, attributes);
            manager.createNotificationChannel(notificationChannel);

            Log.d("abcd", "IFshowNotification: ");
        } else {
          /*  MediaPlayer mp= MediaPlayer.create(MyAndroidFirebaseMsgService.this, R.raw.sms_popcorn);
            mp.start();*/
        }


        // to diaplay notification in DND Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel("vijendra");
            channel.canBypassDnd();
            Log.d("abcd", "IFshowNotificationDND: ");


        }


        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        manager.notify(m, builder.build());


    }

    public void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Libas/Notification Images/");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        image_path = file.toString();
        Log.d("path-->", image_path);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
