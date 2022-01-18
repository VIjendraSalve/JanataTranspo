package com.wht.janatatraspo.InitialActivities;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IErrors;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.ImagePickerActivity;
import com.wht.janatatraspo.Helpers.SharedPref;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Constants;
import com.wht.janatatraspo.my_library.Shared_Preferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AadharActivity extends BaseActivity {

    private Camera camera;
    private Activity _act;
    public static final int REQUEST_AADHAR_ONE = 200;
    public static final int REQUEST_AADHAR_TWO = 300;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView toolbar_title;

    //Image Upload
    //Personal Profile
    private ImageView ivProfilePic;
    private RequestBody requestFileProfile = null;
    private File profileFile = null;
    private MultipartBody.Part bodyProfile = null;
    private RelativeLayout rlProfilePic;
    private String profile_path = null;

    private String user_profile_path = null, user_document_path = null;
    private String frontAadharImagePath = "", backAadharImagePath = "";
    private Uri iurl;
    private File frontFile = null;
    private File backFile = null;
    private MultipartBody.Part bodyAadharFrontImg = null;
    private MultipartBody.Part bodyAadharBackImg = null;
    private RequestBody frontRequestFile = null;
    private RequestBody backRequestFile = null;

    //Private Aadhar image upload
    private ImageView ivAadharImgOne, ivEditAadharOne, ivAadharImgTwo, ivEditAadharTwo;
    private EditText etaadhar;
    private boolean aadharStatus = false;
    private String role = null;
    private String driver_id = null;
    private String verified_document_status = "1";
    private ExtendedFloatingActionButton efaSubmit;


    //Layout VIew
    private CardView cvOtpView, cvAadharImages;
    private TextView tvTimer, tvResend, tvNote;
    private TextView tvVerify, tvVerifiedUser;
    private String otpNo;
    private OtpView otpView;
    private Button btnOTPSubmit;
    private EditText etAadharLinkNumber;
    private boolean flagOtpComplete = false;

    private String client_id, full_name, gender, aadhaar_number, dob, country;
    private String dist, state, po, loc, vtc;
    private String subdist, street, house, landmark, zip;
    private String profile_image, care_of, reference_id;
    private String strFullJSON = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);

        role = getIntent().getStringExtra("role");
        if (role.equalsIgnoreCase(IConstant.DRIVER))
        {
            driver_id = getIntent().getStringExtra("driver_id");
        }
        else
        {
            driver_id="0";
        }

        _act = AadharActivity.this;
        connectionDetector = ConnectionDetector.getInstance(_act);
        validations = new Validations();
        Helper_Method.removePhoneKeypad(_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.black));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.black)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Aadhar Verification");
        toolbar_title.setTextColor(getResources().getColor(R.color.black));
        Helper_Method.setFontToolbard(_act, toolbar_title);

        //Profile Image Upload
        ivProfilePic = findViewById(R.id.ivProfilePic);
        rlProfilePic = findViewById(R.id.rlProfilePic);
        etaadhar = findViewById(R.id.etaadhar);
        ///disableEditText(etaadhar);

        //Adhar Photo Upload Image
        ivAadharImgOne = findViewById(R.id.ivAadharImgOne);
        ivEditAadharOne = findViewById(R.id.ivEditAadharOne);
        ivAadharImgTwo = findViewById(R.id.ivAadharImgTwo);
        ivEditAadharTwo = findViewById(R.id.ivEditAadharTwo);
        efaSubmit = findViewById(R.id.efaSubmit);

        //Otp Verification
        cvOtpView = findViewById(R.id.cvOtpView);
        cvAadharImages = findViewById(R.id.cvAadharImages);
        otpView = findViewById(R.id.otpView);
        tvTimer = findViewById(R.id.tvTimer);
        tvResend = findViewById(R.id.tvResend);
        tvNote = findViewById(R.id.tvNote);
        tvVerify = findViewById(R.id.tvVerify);
        tvVerifiedUser = findViewById(R.id.tvVerifiedUser);
        btnOTPSubmit = findViewById(R.id.btnOTPSubmit);
        etAadharLinkNumber = findViewById(R.id.etAadharLinkNumber);
        otpView.requestFocus();


        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted", otp);
                otpNo = otp;
                flagOtpComplete = true;

            }
        });

        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    String  result = etaadhar.getText().toString().replaceAll("[-]","");
                    //Toast.makeText(AadharActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                    webCallThirdPartyAadharGenrateOTPApi(result);
                }

            }
        });

        btnOTPSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOTPValid()) {
                    if (connectionDetector.checkConnection(_act)) {
                        if (flagOtpComplete) {
                            //webcallOtp();
                            webCallThirdPartyAadharSubmitOtpApi(otpNo);
                        } else {
                            Helper_Method.toaster(_act, "Enter OTP");
                            flagOtpComplete = false;
                        }

                    } else {
                        Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                    }
                }
            }
        });

       /* etaadhar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0 && s.length() == 12) {
                    tvVerify.setVisibility(View.VISIBLE);
                } else {
                    tvVerify.setVisibility(View.GONE);
                }
                // field2.setText("");
            }
        });*/




        etaadhar.addTextChangedListener(new TextWatcher() {
            private final String space = "-"; // you can change this to whatever you want
            private final Pattern pattern = Pattern.compile("^(\\d{4}"+space+"{1}){0,3}\\d{1,4}$"); // check whether we need to modify or not
            @Override
            public void onTextChanged(CharSequence s, int st, int be, int count) {

                String currentText = etaadhar.getText().toString();
                if (currentText.isEmpty() || pattern.matcher(currentText).matches())
                    return; // no need to modify
                String numbersOnly = currentText.trim().replaceAll("[^\\d.]", "");; // remove everything but numbers
                String formatted = "";
                for(int i = 0; i < numbersOnly.length(); i += 4)
                    if (i + 4 < numbersOnly.length())
                        formatted += numbersOnly.substring(i,i+4)+space;
                    else
                        formatted += numbersOnly.substring(i);
                etaadhar.setText(formatted);
                etaadhar.setSelection(etaadhar.getText().toString().length());
                //Toast.makeText(AadharActivity.this, ""+s.length(), Toast.LENGTH_SHORT).show();
                if (s.length() != 0 && s.length() == 14) {
                    tvVerify.setVisibility(View.VISIBLE);
                } else {
                    tvVerify.setVisibility(View.GONE);
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() != 0 && s.length() == 14) {
                    tvVerify.setVisibility(View.VISIBLE);
                } else {
                    tvVerify.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable e) {
                if (e.length() != 0 && e.length() == 14) {
                    tvVerify.setVisibility(View.VISIBLE);
                } else {
                    tvVerify.setVisibility(View.GONE);
                }
            }
        });


        ivEditAadharOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(_act)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImageLogoPickerOptions(REQUEST_AADHAR_ONE);
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        ivEditAadharTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(_act)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImageLogoPickerOptions(REQUEST_AADHAR_TWO);
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        efaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    if (aadharStatus) {
                        if (frontAadharImagePath != null && !frontAadharImagePath.isEmpty() && !frontAadharImagePath.equals("null")) {
                            if (backAadharImagePath != null && !backAadharImagePath.isEmpty() && !backAadharImagePath.equals("null")) {
                                webcallVerifiedAadhar();
                            } else {
                                Helper_Method.toaster(_act, "Select Aadhbar Back image");
                            }
                        } else {
                            Helper_Method.toaster(_act, "Select Aadhbar Front image");
                        }
                    } else {
                        Helper_Method.toaster(_act, "Verified Aadhar Card");
                    }
                }
            }
        });


    }

    private void webCallThirdPartyAadharGenrateOTPApi(String aadhar_no) {

        aadharStatus = false;
        tvVerifiedUser.setVisibility(View.GONE);
        cvOtpView.setVisibility(View.GONE);
        tvVerify.setVisibility(View.VISIBLE);
        cvAadharImages.setVisibility(View.GONE);
        efaSubmit.setVisibility(View.GONE);
        Helper_Method.showProgressBar(_act, "Authentication...");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id_number", aadhar_no);

        Interface api = IUrls.getApiThirdPartyService();
        Call<ResponseBody> result = api.POSTAadharGenrateOtp(params);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("my_tag_adhar", "onResponseSachinADD: " + response.code());
                String output = "";

                if (response.code() == 200) {
                    try {
                        output = response.body().string();
                        Log.d("my_tag_adhar", "onResponseSachinADD: " + output);
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("success");
                        String stringMsg = i.getString("message_code");

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {


                            JSONObject jsonObject = i.getJSONObject("data");
                            client_id = jsonObject.getString("client_id");
                            //  rec_otp = jsonObject.getString("otp_sent");
                            cvOtpView.setVisibility(View.VISIBLE);
                            tvVerify.setVisibility(View.GONE);
                            cvAadharImages.setVisibility(View.GONE);
                            tvVerifiedUser.setVisibility(View.VISIBLE);
                            efaSubmit.setVisibility(View.GONE);
                            Timer();

                           /* age_range = jsonObject.getString("age_range");
                            state = jsonObject.getString("state");
                            gender = jsonObject.getString("gender");
                            aadhaar_number = jsonObject.getString("aadhaar_number");
                            last_digits = jsonObject.getString("last_digits");
                            aadharStatus = true;
                            tvVerifiedUser.setVisibility(View.VISIBLE);*/
                            Helper_Method.dismissProgessBar();


                        } else {
                            Log.d("Login", "onResponse: " + stringMsg);
                            Helper_Method.dismissProgessBar();
                            aadharStatus = false;
                            tvVerifiedUser.setVisibility(View.GONE);
                            cvOtpView.setVisibility(View.GONE);
                            tvVerify.setVisibility(View.VISIBLE);
                            cvAadharImages.setVisibility(View.GONE);
                            efaSubmit.setVisibility(View.GONE);
                        }
                    } catch (JSONException | IOException e) {
                        Helper_Method.dismissProgessBar();
                        Log.d("JSONException", "onResponse: " + e.getMessage());
                        aadharStatus = false;
                        tvVerifiedUser.setVisibility(View.GONE);
                        cvOtpView.setVisibility(View.GONE);
                        tvVerify.setVisibility(View.VISIBLE);
                        cvAadharImages.setVisibility(View.GONE);
                        efaSubmit.setVisibility(View.GONE);
                    }
                }else if(response.code()==500)
                {
                    Helper_Method.dismissProgessBar();
                    aadharStatus = false;
                    tvVerifiedUser.setVisibility(View.GONE);
                    cvOtpView.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.VISIBLE);
                    cvAadharImages.setVisibility(View.GONE);
                    efaSubmit.setVisibility(View.GONE);
                    //Helper_Method.toaster(_act, "Invalid Number");

                    new AlertDialog.Builder(_act)
                            .setTitle("TIME OUT")
                            .setMessage("Server Time Out, Please Click verify again.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialogInterface, int arg1) {
                                    // ActivityCompat.finishAffinity(_act);
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
                else {
                    Helper_Method.dismissProgessBar();
                    aadharStatus = false;
                    tvVerifiedUser.setVisibility(View.GONE);
                    cvOtpView.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.VISIBLE);
                    cvAadharImages.setVisibility(View.GONE);
                    efaSubmit.setVisibility(View.GONE);
                    //Helper_Method.toaster(_act, "Invalid Number");

                    new AlertDialog.Builder(_act)
                            .setTitle("Alert")
                            .setMessage("You Enter Invalid Aadhar Number")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialogInterface, int arg1) {
                                   // ActivityCompat.finishAffinity(_act);
                                    dialogInterface.dismiss();
                                    etaadhar.setText("");
                                }
                            }).create().show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(_act, "Technical Error", Toast.LENGTH_SHORT).show();
                Helper_Method.dismissProgessBar();
                aadharStatus = false;
                tvVerifiedUser.setVisibility(View.GONE);
                cvOtpView.setVisibility(View.GONE);
                tvVerify.setVisibility(View.VISIBLE);
                cvAadharImages.setVisibility(View.GONE);
                efaSubmit.setVisibility(View.GONE);

            }
        });
    }

    private void webCallThirdPartyAadharSubmitOtpApi(String otp) {

        aadharStatus = false;
        cvOtpView.setVisibility(View.GONE);
        tvVerify.setVisibility(View.GONE);
        cvAadharImages.setVisibility(View.GONE);
        tvVerifiedUser.setVisibility(View.GONE);
        efaSubmit.setVisibility(View.GONE);

        Helper_Method.showProgressBar(_act, "Submiting...");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("client_id", client_id);
        params.put("otp", otp);
        params.put("mobile_number", etAadharLinkNumber.getText().toString().trim());

        Interface api = IUrls.getApiThirdPartyService();
        Call<ResponseBody> result = api.POSTAadharSubmitOtp(params);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("my_tag_adhar", "onResponseSachinADD: " + response.code());
                String output = "";

                if (response.code() == 200) {
                    try {
                        output = response.body().string();
                        Log.d("my_tag_adhar", "onResponseSachinADD: " + output);
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("success");
                        String stringMsg = i.getString("message_code");

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {


                            JSONObject jsonObject = i.getJSONObject("data");
                            client_id = jsonObject.getString("client_id");
                            full_name = jsonObject.getString("full_name");
                            gender = jsonObject.getString("gender");
                            aadhaar_number = jsonObject.getString("aadhaar_number");
                            dob = jsonObject.getString("dob");


                            JSONObject jsonObjectAddress = jsonObject.getJSONObject("address");
                            country = jsonObjectAddress.getString("country");
                            dist = jsonObjectAddress.getString("dist");
                            state = jsonObjectAddress.getString("state");
                            po = jsonObjectAddress.getString("po");
                            loc = jsonObjectAddress.getString("loc");
                            vtc = jsonObjectAddress.getString("vtc");
                            subdist = jsonObjectAddress.getString("subdist");
                            street = jsonObjectAddress.getString("street");
                            house = jsonObjectAddress.getString("house");
                            landmark = jsonObjectAddress.getString("landmark");

                            zip = jsonObject.getString("zip");
                            profile_image = jsonObject.getString("profile_image");
                            care_of = jsonObject.getString("care_of");
                            reference_id = jsonObject.getString("reference_id");
                            strFullJSON = jsonObject.toString();
                            aadharStatus = true;
                            cvOtpView.setVisibility(View.GONE);
                            tvVerify.setVisibility(View.GONE);
                            cvAadharImages.setVisibility(View.VISIBLE);
                            tvVerifiedUser.setVisibility(View.VISIBLE);
                            efaSubmit.setVisibility(View.VISIBLE);
                            Helper_Method.dismissProgessBar();


                        } else {
                            Log.d("Login", "onResponse: " + stringMsg);
                            Helper_Method.dismissProgessBar();
                            aadharStatus = false;
                            tvVerifiedUser.setVisibility(View.GONE);
                            cvOtpView.setVisibility(View.GONE);
                            tvVerify.setVisibility(View.GONE);
                            cvAadharImages.setVisibility(View.GONE);
                            tvVerifiedUser.setVisibility(View.GONE);
                            efaSubmit.setVisibility(View.GONE);


                        }
                    } catch (JSONException | IOException e) {
                        Helper_Method.dismissProgessBar();
                        Log.d("JSONException", "onResponse: " + e.getMessage());
                        aadharStatus = false;
                        tvVerifiedUser.setVisibility(View.GONE);
                        cvOtpView.setVisibility(View.GONE);
                        tvVerify.setVisibility(View.GONE);
                        cvAadharImages.setVisibility(View.GONE);
                        tvVerifiedUser.setVisibility(View.GONE);
                        efaSubmit.setVisibility(View.GONE);
                    }
                } else {
                    Helper_Method.dismissProgessBar();
                    aadharStatus = false;
                    tvVerifiedUser.setVisibility(View.GONE);
                    cvOtpView.setVisibility(View.GONE);
                    tvVerify.setVisibility(View.GONE);
                    cvAadharImages.setVisibility(View.GONE);
                    tvVerifiedUser.setVisibility(View.GONE);
                    efaSubmit.setVisibility(View.GONE);
                    Helper_Method.toaster(_act, "Invalid Number");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(_act, "Technical Error", Toast.LENGTH_SHORT).show();
                Helper_Method.dismissProgessBar();
                aadharStatus = false;
                tvVerifiedUser.setVisibility(View.GONE);
                cvOtpView.setVisibility(View.GONE);
                tvVerify.setVisibility(View.GONE);
                cvAadharImages.setVisibility(View.GONE);
                tvVerifiedUser.setVisibility(View.GONE);
                efaSubmit.setVisibility(View.GONE);

            }
        });
    }

    private boolean isValid() {

        if (validations.isBlank(etaadhar)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etaadhar.startAnimation(shake);
            etaadhar.setError(IErrors.EMPTY);
            return false;
        }

        return true;
    }

    private boolean isOTPValid() {

        if (!validations.isValidPhone(etAadharLinkNumber)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etAadharLinkNumber.startAnimation(shake);
            etAadharLinkNumber.setError(getResources().getString(R.string.error_invalid_mobile));
            return false;
        }

        if (validations.isBlank(etAadharLinkNumber)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etAadharLinkNumber.startAnimation(shake);
            etAadharLinkNumber.setError(getResources().getString(R.string.error_field_required));
            return false;
        }

        return true;
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(_act);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                AadharActivity.this.openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_AADHAR_ONE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadFirstAadhar(uri.toString());

                    String strpath = null;
                    strpath = uri.toString();
                    String[] parts = strpath.split("\\//");
                    String part1 = parts[0];
                    frontAadharImagePath = parts[1];

                    Log.d("mytag", "strcorectPath:Aadhar Image One " + frontAadharImagePath);

                   /* if (!frontAadharImagePath.equalsIgnoreCase(null) && !frontAadharImagePath.isEmpty() && !frontAadharImagePath.equals("null")) {
                        //webcallUpdateProfile(imagePath, REQUEST_AADHAR_ONE);
                    } else {

                    }*/

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_AADHAR_TWO) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadSecondAadhar(uri.toString());

                    String strpath = null;
                    strpath = uri.toString();
                    String[] parts = strpath.split("\\//");
                    String part1 = parts[0];
                    backAadharImagePath = parts[1];

                    Log.d("mytag", "strcorectPath:Aadhar Image two" + backAadharImagePath);

                   /* if (!backAadharImagePath.equalsIgnoreCase(null) && !backAadharImagePath.isEmpty() && !backAadharImagePath.equals("null")) {
                        //  webcallUpdateProfile(imagePath, REQUEST_AADHAR_TWO);
                    } else {

                    }*/

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void webcallVerifiedAadhar() {


        Helper_Method.showProgressBar(_act, getResources().getString(R.string.update_profile));

        //Create Upload Server Client
        Interface service = IUrls.getApiService();

        //Front side image
        frontFile = null;
        frontRequestFile = null;
        bodyAadharFrontImg = null;
        int compressionRatioFrontImg = 50; //1 == originalImage, 2 = 50% compression, 4=25% compress
        if (!frontAadharImagePath.equalsIgnoreCase("")) {

            frontFile = new File(String.valueOf(frontAadharImagePath));
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(frontFile.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatioFrontImg, new FileOutputStream(frontFile));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            frontRequestFile = RequestBody.create(MediaType.parse("image/*"), frontFile);
            bodyAadharFrontImg = MultipartBody.Part.createFormData("addhar_front_image", frontFile.getName(), frontRequestFile);
        }

        Log.d("mytag", "Front file: " + frontFile);

        //Back Side image
        backFile = null;
        backRequestFile = null;
        bodyAadharBackImg = null;
        int compressionRatioBackImg = 50; //1 == originalImage, 2 = 50% compression, 4=25% compress
        if (!backAadharImagePath.equalsIgnoreCase("")) {

            backFile = new File(String.valueOf(backAadharImagePath));
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(backFile.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatioBackImg, new FileOutputStream(backFile));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            backRequestFile = RequestBody.create(MediaType.parse("image/*"), backFile);
            bodyAadharBackImg = MultipartBody.Part.createFormData("aadhar_back_image", backFile.getName(), backRequestFile);

        }
        Log.d("mytag", "Back file: " + backFile);
        Log.d("mytag", "role vij: " + role);


        Call<ResponseBody> result = service.POSTAadharVerification(
                AadharActivity.this.createRequestBodyFromText(driver_id),
                AadharActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_ID)),
                AadharActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_API_TOKEN)),
                AadharActivity.this.createRequestBodyFromText(role),
                AadharActivity.this.createRequestBodyFromText(verified_document_status),
                AadharActivity.this.createRequestBodyFromText(client_id),
                AadharActivity.this.createRequestBodyFromText(full_name),
                AadharActivity.this.createRequestBodyFromText(gender),
                AadharActivity.this.createRequestBodyFromText(aadhaar_number),
                AadharActivity.this.createRequestBodyFromText(dob),
                AadharActivity.this.createRequestBodyFromText(country),
                AadharActivity.this.createRequestBodyFromText(dist),
                AadharActivity.this.createRequestBodyFromText(state),
                AadharActivity.this.createRequestBodyFromText(po),
                AadharActivity.this.createRequestBodyFromText(loc),
                AadharActivity.this.createRequestBodyFromText(vtc),
                AadharActivity.this.createRequestBodyFromText(subdist),
                AadharActivity.this.createRequestBodyFromText(street),
                AadharActivity.this.createRequestBodyFromText(house),
                AadharActivity.this.createRequestBodyFromText(landmark),
                AadharActivity.this.createRequestBodyFromText(zip),
                AadharActivity.this.createRequestBodyFromText(""),
                AadharActivity.this.createRequestBodyFromText(care_of),
                AadharActivity.this.createRequestBodyFromText(reference_id),
                AadharActivity.this.createRequestBodyFromText(strFullJSON),
                bodyAadharFrontImg,
                bodyAadharBackImg);

        /*AadharActivity.this.createRequestBodyFromText(profile_image),*/

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    logLargeString(response.body().string());
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            Helper_Method.dismissProgessBar();
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        } else {
                            Constants.AlertDailogue(stringMsg, _act);
                          //  Toast.makeText(_act, stringMsg, Toast.LENGTH_SHORT).show();
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
                Constants.AlertDailogue("Technical Error", _act);
                Toast.makeText(_act, "Technical Error", Toast.LENGTH_SHORT).show();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private RequestBody createRequestBodyFromText(String mText) {
        return RequestBody.create(MediaType.parse("text/plain"), mText);
    }


    //Image Picker Whole Code
    private void loadFirstAadhar(String url) {
        Log.d(TAG, "Image logo cache path IMG1: " + url);
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivAadharImgOne);
    }

    //Image Picker Whole Code
    private void loadSecondAadhar(String url) {
        Log.d(TAG, "Image logo cache path IMG2: " + url);
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivAadharImgTwo);
    }

    private void showImageLogoPickerOptions(final int callNo) {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchLogoCameraIntent(callNo);
            }

            @Override
            public void onChooseGallerySelected() {
                launchLogoGalleryIntent(callNo);
            }
        });
    }

    private void launchLogoCameraIntent(int numberCamera) {
        Intent intent = new Intent(_act, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 3); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 2);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, numberCamera);
    }

    private void launchLogoGalleryIntent(int numberGallery) {
        Intent intent = new Intent(_act, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 3); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 2);
        startActivityForResult(intent, numberGallery);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_stay, R.anim.fade_out);
        //finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        editText.setClickable(false);
        editText.setBackgroundColor(getResources().getColor(R.color.blue));
       /* final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackgroundDrawable(ContextCompat.getDrawable(_act, R.drawable.border_layout));
        } else {
            editText.setBackground(ContextCompat.getDrawable(_act, R.drawable.border_layout));
        }*/
        // editText.setTextColor(Color.BLACK);
    }

    public void Timer() {
        tvTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("0:" + String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                tvTimer.setText("00:00");
                Animation shake = AnimationUtils.loadAnimation(_act, R.anim.shake);
                tvNote.startAnimation(shake);
                tvResend.startAnimation(shake);
                tvNote.setVisibility(View.VISIBLE);
                tvResend.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void logLargeString(String str) {
        if(str.length() > 3000) {
            Log.d("LongData", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.d("LongData", str); // continuation
        }
    }
}