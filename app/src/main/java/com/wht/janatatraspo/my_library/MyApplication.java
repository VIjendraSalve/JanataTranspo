package com.wht.janatatraspo.my_library;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.wht.janatatraspo.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

//import android.support.multidex.MultiDex;


public class MyApplication extends Application {
    private static MyApplication sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        // MultiDex.install(this);
        //FirebaseInstanceId.getInstance().getToken();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        //Log.d("Token_id-->", ""+FirebaseInstanceId.getInstance().getToken());
        //TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        //Fabric.with(this, new Twitter(authConfig));
        /*FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/
        printHashKey();

        //AnalyticsTrackers.initialize(this);
        //AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        // printHashKey();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/latoregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/
    }

    public void printHashKey() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.wht.grit", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
