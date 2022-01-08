package com.wht.janatatraspo.InitialActivities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.Helper_Variable;
import com.wht.janatatraspo.Helpers.LocaleHelper;
import com.wht.janatatraspo.Helpers.SharedPref;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Constants;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private boolean doubleBackToExitPressedOnce = false;
    private EditText etMobile;
    private Button btnSubmit;
    private String stringUserToken = "None", user_profile_path;

    private String setLang, deviceInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _act = LoginActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.removePhoneKeypad(_act);
        setLang = SharedPref.getPrefs(_act, IConstant.LANGUAGE);
        deviceInformation = Helper_Method.getHardwareAndSoftwareInfo();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        stringUserToken = task.getResult().getToken();
                        Log.d("Notification_Token1", "onComplete: " + stringUserToken);
                        Shared_Preferences.setPrefs(LoginActivity.this, Constants.NOTIFICATION_TOKEN, stringUserToken);

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d(TAG, msg);
                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        if (setLang == null || setLang.equalsIgnoreCase("null") || setLang.equalsIgnoreCase("") || setLang.isEmpty()) {
            SharedPref.setPrefs(_act, IConstant.LANGUAGE, "en");
            setLang = "en";
        } else {
        }

        if (setLang.equalsIgnoreCase("en")) {
            SharedPref.setPrefs(_act, IConstant.LANGUAGE, "en");
        } else if (setLang.equalsIgnoreCase("mr")) {
            SharedPref.setPrefs(_act, IConstant.LANGUAGE, "mr");
        } else if (setLang.equalsIgnoreCase("hi")) {
            SharedPref.setPrefs(_act, IConstant.LANGUAGE, "hi");
        }

        etMobile = findViewById(R.id.etMobile);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.checkConnection(_act)) {

                    if (isValid()) {
                        Helper_Method.hideSoftInput(_act);
                        webcallLogin();
                    }

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }
            }
        });
    }

    private boolean isValid() {


        if (!validations.isValidPhone(etMobile)) {
           /* Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);*/
            etMobile.setError(getResources().getString(R.string.error_invalid_mobile));
            return false;
        }

        if (validations.isBlank(etMobile)) {
          /*  Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);*/
            etMobile.setError(getResources().getString(R.string.error_field_required));
            return false;
        }

        return true;
    }


    private void webcallLogin() {

        Helper_Method.showProgressBar(_act, "Sign In...");

        Log.d("Input", "webcallLogin: "+etMobile.getText().toString().trim());
        Log.d("Input", "webcallLogin: "+stringUserToken);
        Log.d("Input", "webcallLogin: "+deviceInformation);


        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTLogin(
                etMobile.getText().toString().trim(),
                stringUserToken,
                deviceInformation);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);
                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            //String is_verified = jsonObject.getString(IConstant.RESPONSE_OTP_VERIFIED);
                            //if (is_verified.equalsIgnoreCase(IConstant.RESPONSE_VERIFIED)) {

                            Helper_Method.hideSoftInput(_act);
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                            Intent intent = new Intent(_act, OTPActivity.class);
                            intent.putExtra("Mobile", etMobile.getText().toString().trim());
                            startActivity(intent);


                        } else {
                            Helper_Method.hideSoftInput(_act);
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }

                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Issue", "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            //overridePendingTransition(R.anim.activity_stay, R.anim.fade_out);
            return;
        }

        doubleBackToExitPressedOnce = true;
        Helper_Method.toaster(_act, "Press BACK again to close the app.");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }*/

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Helper_Variable.user_reg_lang = languageCode;
        SharedPref.setPrefs(_act, IConstant.LANGUAGE, languageCode);
        finish();
        startActivity(getIntent());
        /*Resources resources = context.getResources();
        edt_login_username.setHint(resources.getString(R.string.provide_contact_no));
        edt_login_password.setHint(resources.getString(R.string.provide_password));
        tvWcTitle.setText(resources.getString(R.string.lbl_welcome));
        tvUserType.setText(resources.getString(R.string.lbl_operator));
        btnLogin.setText(resources.getString(R.string.btn_login));*/
        //setTitle(resources.getString(R.string.main_activity_toolbar_title));
    }
}