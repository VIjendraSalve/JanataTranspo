package com.wht.janatatraspo.InitialActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.MainActivity;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Constants;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends BaseActivity {


    private Activity _act;
    private TextView tvTimer;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private OtpView otpView;
    private TextView btnSubmit, tvNote, tvResend;
    private String otpNo;
    private boolean flagOtpComplete = false;
    private String strMobile = null, stringUserToken = "NONE", user_profile_path = null, user_document_path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        strMobile = getIntent().getStringExtra("Mobile");

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
                        Shared_Preferences.setPrefs(OTPActivity.this, Constants.NOTIFICATION_TOKEN, stringUserToken);

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d(TAG, msg);
                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        _act = OTPActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);


        otpView = findViewById(R.id.otp_view);
        btnSubmit = findViewById(R.id.btnSubmit);
        otpView.requestFocus();

        tvNote = findViewById(R.id.tvNote);
        tvResend = findViewById(R.id.tvResend);
        tvNote.setVisibility(View.GONE);
        tvResend.setVisibility(View.GONE);

        tvTimer = findViewById(R.id.tvTimer);
        Timer();

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted", otp);
                otpNo = otp;
                flagOtpComplete = true;
                if (connectionDetector.checkConnection(_act)) {

                    if (flagOtpComplete) {
                        webcallOtp();
                    } else {
                        Helper_Method.toaster(_act, "Enter OTP");
                    }

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }
            }
        });

      /*  btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (connectionDetector.checkConnection(_act)) {

                    if (flagOtpComplete) {
                        webcallOtp();
                    } else {
                        Helper_Method.toaster(_act, "Enter OTP");
                    }

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }

            }
        });*/


        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (connectionDetector.checkConnection(_act)) {

                    webcallResendOtp();

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.activity_stay, R.anim.fade_out);
        //finish();
    }


    private void webcallResendOtp() {

        Helper_Method.showProgressBar(_act, "Resending...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTResendOtp(strMobile);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                //EraseLocalData();
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                            tvNote.setVisibility(View.GONE);
                            tvResend.setVisibility(View.GONE);
                            otpView.requestFocus();
                            Timer();


                        } else {
                            Helper_Method.logD("OTP", stringMsg);
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }


                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                        Helper_Method.logD("JSONException", "onResponse: " + e.getMessage());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.logD("IOException", "onResponse: " + e.getMessage());
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void webcallOtp() {

        Helper_Method.showProgressBar(_act, "Verifying...");

        Log.d("Input", "webcallOtp: " + strMobile);
        Log.d("Input", "webcallOtp: " + otpNo);
        Log.d("Input", "webcallOtp: " + stringUserToken);

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.getCheckOtp(strMobile, otpNo, stringUserToken);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                //EraseLocalData();
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            Helper_Method.removePhoneKeypad(_act);
                            Helper_Method.dismissProgessBar();


                            Helper_Method.dismissProgessBar();

                            user_profile_path = i.getString("user_profile_path");
                            user_document_path = i.getString("user_document_path");
                            JSONArray jsonArray = i.getJSONArray("user_detail");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);
                            String id = jsonObjectData.getString("id");
                            String first_name = jsonObjectData.getString("first_name");
                            String last_name = jsonObjectData.getString("last_name");
                            String email = jsonObjectData.getString("email");
                            String mobile_no = jsonObjectData.getString("mobile_no");
                            String image = user_profile_path + jsonObjectData.getString("image");
                            String role = jsonObjectData.getString("role");
                            String api_token = jsonObjectData.getString("api_token");
                            String is_driver = jsonObjectData.getString("is_driver");
                            String birthdate = jsonObjectData.getString("birthdate");
                            String address = jsonObjectData.getString("address");
                            String business_name = jsonObjectData.getString("business_name");
                            String bank_name = jsonObjectData.getString("bank_name");
                            String account_number = jsonObjectData.getString("account_number");
                            String ifsc_code = jsonObjectData.getString("ifsc_code");
                            String branch_name = jsonObjectData.getString("branch_name");
                            // String pan_number = jsonObjectData.getString("pan_number");
                            String gst_number = jsonObjectData.getString("gst_number");
                            // String aadhar_number = jsonObjectData.getString("aadhar_number");
                            String is_pan_verified = jsonObjectData.getString("is_pan_verified");
                            String is_gst_verified = jsonObjectData.getString("is_gst_verified");
                            // String is_aadhar_verified = jsonObjectData.getString("is_aadhar_verified");
                            // String addhar_front_image = user_document_path + jsonObjectData.getString("addhar_front_image");
                            // String aadhar_back_image = user_document_path + jsonObjectData.getString("aadhar_back_image");
                            //  String pan_image = user_document_path + jsonObjectData.getString("pan_image");
                            String gst_photo = user_document_path + jsonObjectData.getString("gst_photo");
                            String city = jsonObjectData.getString("city");
                            String police_verification_image = user_document_path + jsonObjectData.getString("police_verification_image");
                            String driving_licenses_image = user_document_path + jsonObjectData.getString("driving_licenses_image");


                            Shared_Preferences.setPrefs(_act, IConstant.USER_ID, id);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_FIRST_NAME, first_name);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_LAST_NAME, last_name);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_EMAIL, email);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_MOBILE, mobile_no);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_PHOTO, image);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_ROLE_ID, role);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_API_TOKEN, api_token);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_IS_DRIVER, is_driver);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_DOB, birthdate);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_ADDRESS, address);
                            Shared_Preferences.setPrefs(_act, IConstant.OWNER_BUSINESS_NAME, business_name);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_BANK_NAME, bank_name);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_ACCOUNT_NO, account_number);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_IFSC_CODE, ifsc_code);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_BANK_BRANCH_CITY, branch_name);
                            // Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD_NO, pan_number);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_GST_NO, gst_number);
                            // Shared_Preferences.setPrefs(_act, IConstant.USER_AADHAR_NO, aadhar_number);
                            Shared_Preferences.setPrefs(_act, IConstant.IS_PAN_VERIFIED, is_pan_verified);
                            Shared_Preferences.setPrefs(_act, IConstant.IS_GST_VERIFIED, is_gst_verified);
                            //  Shared_Preferences.setPrefs(_act, IConstant.IS_AADHAR_VERIFIED, is_aadhar_verified);
                            //  Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_ONE, addhar_front_image);
                            //  Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_TWO, aadhar_back_image);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_GST_PHOTO, gst_photo);
                            //  Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD, pan_image);
                            Shared_Preferences.setPrefs(_act, IConstant.CITY_NAME, city);
                            Shared_Preferences.setPrefs(_act, IConstant.POLICE_VERIFICATION_IMAGE, police_verification_image);
                            Shared_Preferences.setPrefs(_act, IConstant.DRIVING_LICENSES_IMAGE, driving_licenses_image);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");

                            Intent intent = new Intent(_act, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Helper_Method.toaster(_act, stringMsg);
                        } else {
                            Helper_Method.logD("OTP", stringMsg);
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();
                        }
                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                        Helper_Method.logD("JSONException", "onResponse: " + e.getMessage());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.logD("IOException", "onResponse: " + e.getMessage());
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    public void Timer() {
        tvTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("0:" + String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                tvTimer.setText("0:0");
               /* Animation shake = AnimationUtils.loadAnimation(_act, R.anim.shake);
                tvNote.startAnimation(shake);
                tvResend.startAnimation(shake);*/
                tvNote.setVisibility(View.VISIBLE);
                tvResend.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //overridePendingTransition(R.animator.left_right, R.animator.right_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            //overridePendingTransition(R.animator.left_right, R.animator.right_left);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}