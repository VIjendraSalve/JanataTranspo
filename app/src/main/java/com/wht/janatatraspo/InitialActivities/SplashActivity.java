package com.wht.janatatraspo.InitialActivities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.MainActivity;
import com.wht.janatatraspo.Notification.DatabaseSqliteHandler;
import com.wht.janatatraspo.Notification.Notification;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class SplashActivity extends BaseActivity {

    public static boolean versionFlag;
    String TAG = "SplashScreen";
    private Activity _act;
    private ImageView ivImage;
    private Dialog dialog;
    private Button buttonUpdate, buttonExit;
    private String versionCode, versionName;
    private TextView tvVersion;
    private ProgressBar prog;
    private ConnectionDetector connectionDetector;

    private DatabaseSqliteHandler db;
    private ArrayList<String> stringArrayListKeys = new ArrayList<>();
    private ArrayList<String> stringArrayListValues = new ArrayList<>();
    private ArrayList<Notification> notificationInsertObjectArrayList = new ArrayList<>();

    private String image = null, title = null, message1 = null;

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        _act = SplashActivity.this;
        connectionDetector = ConnectionDetector.getInstance(this);
        prog = findViewById(R.id.prog);
        ivImage = findViewById(R.id.ivImage);

        /*try {
            InAppUpdateManager updateManager = new InAppUpdateManager(this,
                    AppUpdateConstant.APP_UPDATE_TYPE_IMMEDIATE);
            updateManager.checkAppUpdate(AppUpdateConstant.APP_UPDATE_TYPE_IMMEDIATE);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        if (getIntent().getExtras() != null) {
            JSONObject obj = null;
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("Splash: ", "Key: " + key + " Value: " + value);
                Log.d("Splash: ", "Values :" + getIntent().getExtras().get(key));

                if (key.equalsIgnoreCase("title")) {

                    title = (String) value;
                }
                if (key.equalsIgnoreCase("image")) {

                    image = (String) value;

                }
                if (key.equalsIgnoreCase("body")) {

                    message1 = (String) value;

                } else {

                }

                stringArrayListKeys.add(key);
                stringArrayListValues.add(key);
            }
            Log.d("Data", "Key: Size" + stringArrayListKeys.size());
            Log.d("Data", "Values: Size" + stringArrayListValues.size());

            db = DatabaseSqliteHandler.getInstance(this);
            Log.d("Splash", "onCreate: " + title);
            if (title != null && !title.isEmpty() && !title.equals("null") && !title.equals("")) {
                if (image != null && !image.isEmpty() && !image.equals("null") && !image.equals("")) {
                    db.insert_notification(title, message1, image, "");

                } else {
                    db.insert_notification(title, message1, null,"");
                }
            } else {
            }

        }

        final Thread timer = new Thread() {
            public void run() {
                try {

                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                    ivImage.startAnimation(animation2);
                    animation2.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // Toast.makeText(SplashScreenActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            deleteCache(_act);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Log.d(TAG, "onAnimationEnd: " + Shared_Preferences.getPrefs(_act, IConstant.USER_ID));
                            if (Shared_Preferences.getPrefs(_act, IConstant.USER_ID) != null
                                    && !Shared_Preferences.getPrefs(_act, IConstant.USER_ID).isEmpty()
                                    && !Shared_Preferences.getPrefs(_act, IConstant.USER_ID).equals("null")
                                    && !Shared_Preferences.getPrefs(_act, IConstant.USER_ID).equals("")) {

                                Intent intent = new Intent(_act, MainActivity.class);
                                startActivity(intent);
                                // _act.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();

                            } else {

                                Intent intent = new Intent(_act, LoginActivity.class);
                                startActivity(intent);
                                //_act.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            }

                            //webServiceVersion();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                } finally {
                }
            }
        };
        timer.start();


    }
}