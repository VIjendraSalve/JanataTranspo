package com.wht.janatatraspo.my_library;

/*import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;*/

import androidx.annotation.NonNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

//import okhttp3.Dispatcher;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;

/**
 * Created by PC-2 on 07-Apr-17.
 */
public class MyConfig {

    /*public static final String JSON_BASE_URL = "http://roomser.in";
    public static final String JSON_SUB_URL = "";


    public static final String JSON_SUB_URL2 = "/app";
    public static final String JSON_PROFILE_PATH = "http://roomser.in/assets/uploads/profile/";
    public static final String JSON_DOC_PATH = "http://roomser.in/assets/uploads/user_documents/";*/

    public static final String JSON_BASE_URL = "http://wetap.in";
    public static final String JSON_SUB_URL = "/grit_hotel";


    public static final String JSON_SUB_URL2 = "/app";
    public static final String JSON_PROFILE_PATH = "http://wetap.in/grit_hotel/assets/uploads/profile/";
    public static final String JSON_DOC_PATH = "http://wetap.in/grit_hotel/assets/uploads/user_documents/";

    public static final String IMG_URL_CAR = "http://hrcabs.com/files/carDocuments/";

    public static Dispatcher dispatcher;
    public static Retrofit retrofit;

    public static Retrofit getRetrofit(String BASE_URL) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.cache(null);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);
        //.addConverterFactory(GsonConverterFactory.create());
        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        httpClient.dispatcher(dispatcher);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit;
    }


    /*public static void cancelRetrofitAllRequests()
    {

        Log.d("my_tag", "cancelRetrofitAllRequests1: "+dispatcher.queuedCallsCount());
        Log.d("my_tag", "cancelRetrofitAllRequests2: "+dispatcher.runningCallsCount());
        for(Call call : dispatcher.queuedCalls()) {
            //if(call.request().tag().equals("sss"))

            if (dispatcher.queuedCallsCount()>0)
                call.cancel();
        }
        for(Call call : dispatcher.runningCalls()) {
            //if(call.request().tag().equals("sss"))
            if (dispatcher.runningCallsCount()>0)
                call.cancel();
        }

        if (dispatcher!=null)
        {
            Log.d("my_tag", "cancelRetrofitAllRequests: ");
            dispatcher.cancelAll();
        }
    }*/

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse("text/plain"), s);
    }

    public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

}
