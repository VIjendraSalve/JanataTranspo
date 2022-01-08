package com.wht.janatatraspo.my_library;/*
package com.wht.community.my_library;

import android.content.Context;

import androidx.annotation.NonNull;



public class AppController {
    private static AppController mInstance;



    private static Context mCtx;

    private AppController(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AppController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppController(context);
        }
        return mInstance;
    }



    public <T> void addToRequestQueue(@NonNull final Request<T> request) {
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueueWithTag(@NonNull final Request<T> request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }
}
*/
