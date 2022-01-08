package com.wht.janatatraspo.InitialActivities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.ImagePickerActivity;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProfileActivity extends BaseActivity {

    public static final int REQUEST_LOGO_IMAGE = 100;
    public static final int REQUEST_AADHAR_ONE = 200;
    public static final int REQUEST_AADHAR_TWO = 300;
    public static final int REQUEST_PANCARD = 400;
    public static final int REQUEST_GST = 500;
    private Camera camera;
    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView toolbar_title;
    //Image Upload
    //Personal Profile
    private ImageView ivProfilePic, ivAddImage;
    private RequestBody requestFileProfile = null;
    private File profileFile = null;
    private MultipartBody.Part bodyProfile = null;
    private RelativeLayout rlProfilePic, rlEditGSTView;
    private String profile_path = null;

    private String imagePath = "", user_profile_path = null, user_document_path = null;
    private Uri iurl;
    private File file = null;
    private MultipartBody.Part bodyImg = null;
    private MultipartBody.Part bodyAadharFrontImg = null;
    private MultipartBody.Part bodyAadharBackImg = null;
    private MultipartBody.Part bodyPanCardImg = null;
    private MultipartBody.Part bodyGstImg = null;
    private RequestBody requestFile = null;

    //Set Text Details
    private TextView tvCompanyName, tvRole, tvOwnerName, /*tvAboutUs,*/
            tv_phone, tv_email, tvCity, tvAddress, tvBloodGroup;
    private TextView tvBankName, tvAccNo, tvIfscCode, tvBranchCity, tvPanNo, tvGSTNo;

    //Private Aadhar image upload
    private ImageView ivPanCard, ivEditPancard, ivGST, ivEditGST;
    private ImageView ivAadharImgOne, ivEditAadharOne, ivAadharImgTwo, ivEditAadharTwo;
    private ExtendedFloatingActionButton efaUpdate;


    //Verification Owner and Owner_as_Driver
    private TextView tvMainVerified, tvMainUnVerified;
    private TextView tvAadharVerified, tvAadharPending, tvAadharNote;
    private TextView tvAadharNo;
    private RelativeLayout rlAddhar;
    private RelativeLayout rlGSTUnVerifiedView;
    private TextView tvGstVerified;
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        // Intent data = result.getData();
                        webCallThirdGetUserDetails();

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _act = ProfileActivity.this;
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);
        validations = new Validations();
        removePhoneKeypad();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.apptheme)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Profile");
        toolbar_title.setTextColor(getResources().getColor(R.color.white));
        Helper_Method.setFontToolbard(_act, toolbar_title);

        //Profile Image Upload
        ivProfilePic = findViewById(R.id.ivProfilePic);
        rlProfilePic = findViewById(R.id.rlProfilePic);
        ivAddImage = findViewById(R.id.ivAddImage);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvRole = findViewById(R.id.tvRole);
        tvOwnerName = findViewById(R.id.tvOwnerName);
        /*       tvAboutUs = findViewById(R.id.tvAboutUs);*/
        tv_phone = findViewById(R.id.tv_phone);
        tv_email = findViewById(R.id.tv_email);
        tvCity = findViewById(R.id.tvCity);
        tvAddress = findViewById(R.id.tvAddress);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvAadharNo = findViewById(R.id.tvAadharNo);

        tvBankName = findViewById(R.id.tvBankName);
        tvAccNo = findViewById(R.id.tvAccNo);
        tvIfscCode = findViewById(R.id.tvIfscCode);
        tvBranchCity = findViewById(R.id.tvBranchCity);
        tvPanNo = findViewById(R.id.tvPanNo);
        tvGSTNo = findViewById(R.id.tvGSTNo);

        //Image OF Photo Upload Image
        ivGST = findViewById(R.id.ivGST);
        ivEditGST = findViewById(R.id.ivEditGST);
        ivPanCard = findViewById(R.id.ivPanCard);
        ivEditPancard = findViewById(R.id.ivEditPancard);
        ivAadharImgOne = findViewById(R.id.ivAadharImgOne);
        ivEditAadharOne = findViewById(R.id.ivEditAadharOne);
        ivAadharImgTwo = findViewById(R.id.ivAadharImgTwo);
        ivEditAadharTwo = findViewById(R.id.ivEditAadharTwo);
        efaUpdate = findViewById(R.id.efaUpdate);
        rlAddhar = findViewById(R.id.rlAddhar);

        //Top Checking KYC
        tvMainVerified = findViewById(R.id.tvMainVerified);
        tvMainUnVerified = findViewById(R.id.tvMainUnVerified);

        //Aadhar VIew
        tvAadharVerified = findViewById(R.id.tvAadharVerified);
        tvAadharPending = findViewById(R.id.tvAadharPending);
        tvAadharNote = findViewById(R.id.tvAadharNote);


        //GST VIEW
        rlEditGSTView = findViewById(R.id.rlEditGSTView);
        tvGstVerified = findViewById(R.id.tvGstVerified);
        rlGSTUnVerifiedView = findViewById(R.id.rlGSTUnVerifiedView);


        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(_act)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImageLogoPickerOptions(REQUEST_LOGO_IMAGE);
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

   /*     ivEditAadharOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(_act)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

        ivEditPancard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(_act)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImageLogoPickerOptions(REQUEST_PANCARD);
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
        ivEditGST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(_act)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImageLogoPickerOptions(REQUEST_GST);
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
        });*/

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_act, ZoomScreenActivity.class);
                intent.putExtra("image_path", Shared_Preferences.getPrefs(_act, IConstant.USER_PHOTO));
                startActivity(intent);
            }
        });

        ivAadharImgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_act, ZoomScreenActivity.class);
                intent.putExtra("image_path", Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_ONE));
                startActivity(intent);
            }
        });
        ivAadharImgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_act, ZoomScreenActivity.class);
                intent.putExtra("image_path", Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_TWO));
                startActivity(intent);
            }
        });
     /*   ivPanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_act, ZoomScreenActivity.class);
                intent.putExtra("image_path", Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD));
                startActivity(intent);
            }
        });
        ivGST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_act, ZoomScreenActivity.class);
                intent.putExtra("image_path", Shared_Preferences.getPrefs(_act, IConstant.USER_GST_PHOTO));
                startActivity(intent);
            }
        });*/

        efaUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_act, UpdateProfileActivity.class);
                startActivityForResult(intent, 600);
            }
        });

        rlAddhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AadharActivity.class);
               /* if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equalsIgnoreCase(IConstant.OWNER)) {
                    intent.putExtra("role", IConstant.OWNER);
                } else if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equalsIgnoreCase(IConstant.DRIVER)) {
                    intent.putExtra("role", IConstant.DRIVER);
                } else {
                    intent.putExtra("role", IConstant.OWNER_DRIVER);
                }*/
                intent.putExtra("role", "5");
                someActivityResultLauncher.launch(intent);
            }
        });

        rlGSTUnVerifiedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, GSTDocumentActivity.class);
                /*if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equalsIgnoreCase(IConstant.OWNER)) {
                    intent.putExtra("role", IConstant.OWNER);
                } else if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equalsIgnoreCase(IConstant.DRIVER)) {
                    intent.putExtra("role", IConstant.DRIVER);
                } else {
                    intent.putExtra("role", IConstant.OWNER_DRIVER);
                }*/
                intent.putExtra("role", "5");
                someActivityResultLauncher.launch(intent);
            }
        });
        webCallThirdGetUserDetails();
    }

    private void showSettingsDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(_act);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ProfileActivity.this.openSettings();
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
        if (requestCode == REQUEST_LOGO_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString(), ivProfilePic);

                    String strpath = null;
                    strpath = uri.toString();
                    String[] parts = strpath.split("\\//");
                    String part1 = parts[0];
                    imagePath = parts[1];

                    Log.d("mytag", "strcorectPath" + imagePath);

                    if (!imagePath.equalsIgnoreCase(null) && !imagePath.isEmpty() && !imagePath.equals("null")) {
                        webcallUpdateProfile(imagePath, REQUEST_LOGO_IMAGE);
                    } else {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_AADHAR_ONE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString(), ivAadharImgOne);

                    String strpath = null;
                    strpath = uri.toString();
                    String[] parts = strpath.split("\\//");
                    String part1 = parts[0];
                    imagePath = parts[1];

                    Log.d("mytag", "strcorectPath:Aadhar Image One " + imagePath);

                    if (!imagePath.equalsIgnoreCase(null) && !imagePath.isEmpty() && !imagePath.equals("null")) {
                        webcallUpdateProfile(imagePath, REQUEST_AADHAR_ONE);
                    } else {

                    }

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
                    loadProfile(uri.toString(), ivAadharImgTwo);

                    String strpath = null;
                    strpath = uri.toString();
                    String[] parts = strpath.split("\\//");
                    String part1 = parts[0];
                    imagePath = parts[1];

                    Log.d("mytag", "strcorectPath" + imagePath);

                    if (!imagePath.equalsIgnoreCase(null) && !imagePath.isEmpty() && !imagePath.equals("null")) {
                        webcallUpdateProfile(imagePath, REQUEST_AADHAR_TWO);
                    } else {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_PANCARD) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString(), ivPanCard);

                    String strpath = null;
                    strpath = uri.toString();
                    String[] parts = strpath.split("\\//");
                    String part1 = parts[0];
                    imagePath = parts[1];

                    Log.d("mytag", "strcorectPath" + imagePath);

                    if (!imagePath.equalsIgnoreCase(null) && !imagePath.isEmpty() && !imagePath.equals("null")) {
                        webcallUpdateProfile(imagePath, REQUEST_PANCARD);
                    } else {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_GST) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString(), ivGST);

                    String strpath = null;
                    strpath = uri.toString();
                    String[] parts = strpath.split("\\//");
                    String part1 = parts[0];
                    imagePath = parts[1];

                    Log.d("mytag", "strcorectPath" + imagePath);

                    if (!imagePath.equalsIgnoreCase(null) && !imagePath.isEmpty() && !imagePath.equals("null")) {
                        webcallUpdateProfile(imagePath, REQUEST_GST);
                    } else {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 600) {
            if (resultCode == Activity.RESULT_OK) {
                updateProfile();
            }
        }

    }

    //Uploading Profile Image
    private void webCallUpdateProfilePic(String user_profile_path) {

        Helper_Method.showProgressBar(_act, "Upload Profile Image...");
        //Create Upload Server Client
        Interface service = IUrls.getApiService();
        profileFile = null;
        bodyProfile = null;
        requestFileProfile = null;
        int compressionRatioLogoImg = 50; //1 == originalImage, 2 = 50% compression, 4=25% compress
        if (!user_profile_path.equalsIgnoreCase("")) {

            profileFile = new File(String.valueOf(user_profile_path));
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(profileFile.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatioLogoImg, new FileOutputStream(profileFile));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            requestFileProfile = RequestBody.create(MediaType.parse("image/*"), profileFile);
            bodyProfile = MultipartBody.Part.createFormData("image", profileFile.getName(), requestFileProfile);
        }
        Log.d("mytag", "profileFile: " + profileFile);

        RequestBody userIdMul = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(_act, IConstant.USER_ID));
        RequestBody apiTokenMul = RequestBody.create(MediaType.parse("text/plain"), Shared_Preferences.getPrefs(_act, IConstant.USER_API_TOKEN));

        Call<ResponseBody> result = service.POSTUpdateProfileImage(userIdMul, apiTokenMul, bodyProfile);

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
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                            profile_path = jsonObject.getString("user_profile_path");
                            String image = jsonObject.getString("image");
                            Shared_Preferences.setPrefs(_act, IConstant.USER_PHOTO, profile_path + image);
                            //loadProfile(profile_path + image);
                            updateProfile();
                        } else {
                            Helper_Method.dismissProgessBar();
                            //Helper_Method.dismissProgessBar();
                            if (jsonObject.has("isSessionExpired")) {
                                Helper_Method.autoLogout(_act, true, stringMsg);
                            } else {
                                Helper_Method.toaster(_act, stringMsg);
                            }

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
                Log.d(TAG, "onFailure: " + getResources().getString(R.string.lbl_technical_error));
                Helper_Method.dismissProgessBar();
            }
        });
    }


    private void webcallUpdateProfile(String recImgPath, int imageNo) {

        Helper_Method.showProgressBar(_act, getResources().getString(R.string.update_profile));

        //Create Upload Server Client
        Interface service = IUrls.getApiService();

        // Logo Image
        file = null;
        bodyImg = null;
        bodyAadharFrontImg = null;
        bodyAadharBackImg = null;
        bodyPanCardImg = null;
        bodyGstImg = null;
        requestFile = null;
        int compressionRatioLogoImg = 50; //1 == originalImage, 2 = 50% compression, 4=25% compress
        if (!recImgPath.equalsIgnoreCase("")) {

            file = new File(String.valueOf(recImgPath));
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatioLogoImg, new FileOutputStream(file));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            requestFile = RequestBody.create(MediaType.parse("image/*"), file);

            if (imageNo == REQUEST_AADHAR_ONE) {
                bodyImg = MultipartBody.Part.createFormData("image", "", requestFile);
                bodyAadharFrontImg = MultipartBody.Part.createFormData("addhar_front_image", file.getName(), requestFile);
                bodyAadharBackImg = MultipartBody.Part.createFormData("aadhar_back_image", "", requestFile);
                bodyPanCardImg = MultipartBody.Part.createFormData("pan_photo", "", requestFile);
                bodyGstImg = MultipartBody.Part.createFormData("gst_photo", "", requestFile);
            } else if (imageNo == REQUEST_AADHAR_TWO) {
                bodyImg = MultipartBody.Part.createFormData("image", "", requestFile);
                bodyAadharFrontImg = MultipartBody.Part.createFormData("addhar_front_image", "", requestFile);
                bodyAadharBackImg = MultipartBody.Part.createFormData("aadhar_back_image", file.getName(), requestFile);
                bodyPanCardImg = MultipartBody.Part.createFormData("pan_photo", "", requestFile);
                bodyGstImg = MultipartBody.Part.createFormData("gst_photo", "", requestFile);

            } else if (imageNo == REQUEST_PANCARD) {
                bodyImg = MultipartBody.Part.createFormData("image", "", requestFile);
                bodyAadharFrontImg = MultipartBody.Part.createFormData("addhar_front_image", "", requestFile);
                bodyAadharBackImg = MultipartBody.Part.createFormData("aadhar_back_image", "", requestFile);
                bodyPanCardImg = MultipartBody.Part.createFormData("pan_photo", file.getName(), requestFile);
                bodyGstImg = MultipartBody.Part.createFormData("gst_photo", "", requestFile);
            } else if (imageNo == REQUEST_GST) {
                bodyImg = MultipartBody.Part.createFormData("image", "", requestFile);
                bodyAadharFrontImg = MultipartBody.Part.createFormData("addhar_front_image", "", requestFile);
                bodyAadharBackImg = MultipartBody.Part.createFormData("aadhar_back_image", "", requestFile);
                bodyPanCardImg = MultipartBody.Part.createFormData("pan_photo", "", requestFile);
                bodyGstImg = MultipartBody.Part.createFormData("gst_photo", file.getName(), requestFile);
            } else if (imageNo == REQUEST_LOGO_IMAGE) {
                bodyImg = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                bodyAadharFrontImg = MultipartBody.Part.createFormData("addhar_front_image", "", requestFile);
                bodyAadharBackImg = MultipartBody.Part.createFormData("aadhar_back_image", "", requestFile);
                bodyPanCardImg = MultipartBody.Part.createFormData("pan_photo", "", requestFile);
                bodyGstImg = MultipartBody.Part.createFormData("gst_photo", "", requestFile);
            }

        }
        Log.d("mytag", "Logo file: " + file);


        Call<ResponseBody> result = service.POSTUpdateProfile(
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_ID)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_API_TOKEN)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_LAST_NAME)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_DOB)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO)),
                ProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO)),
                bodyImg,
                bodyAadharFrontImg,
                bodyAadharBackImg,
                bodyPanCardImg,
                bodyGstImg);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";

                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            Helper_Method.dismissProgessBar();

                            user_profile_path = i.getString("user_profile_path");
                            user_document_path = i.getString("user_document_path");
                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);
                            String id = jsonObjectData.getString("id");
                            Log.d("Vijendra", "onResponse: " + jsonObjectData.getString("customer_type"));
                            String customer_type = jsonObjectData.getString("customer_type");
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
                            String pan_number = jsonObjectData.getString("pan_number");
                            String gst_number = jsonObjectData.getString("gst_number");
                            String aadhar_number = jsonObjectData.getString("aadhar_number");
                            String is_pan_verified = jsonObjectData.getString("is_pan_verified");
                            String is_gst_verified = jsonObjectData.getString("is_gst_verified");
                            String is_aadhar_verified = jsonObjectData.getString("is_aadhar_verified");
                            String addhar_front_image = user_document_path + jsonObjectData.getString("addhar_front_image");
                            String aadhar_back_image = user_document_path + jsonObjectData.getString("aadhar_back_image");
                            // String pan_image = user_document_path + jsonObjectData.getString("pan_image");
                            // String gst_photo = user_document_path + jsonObjectData.getString("gst_photo");
                            String city = jsonObjectData.getString("city");
                            //  String police_verification_image = user_document_path + jsonObjectData.getString("police_verification_image");
                            // String driving_licenses_image = user_document_path + jsonObjectData.getString("driving_licenses_image");


                            Shared_Preferences.setPrefs(_act, IConstant.USER_ID, id);
                            Shared_Preferences.setPrefs(_act, IConstant.CUSTOMER_TYPE, customer_type);
                            Log.d("Vijendra", "onResponse shared: " + Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE));
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
                            Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD_NO, pan_number);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_GST_NO, gst_number);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_AADHAR_NO, aadhar_number);
                            Shared_Preferences.setPrefs(_act, IConstant.IS_PAN_VERIFIED, is_pan_verified);
                            Shared_Preferences.setPrefs(_act, IConstant.IS_GST_VERIFIED, is_gst_verified);
                            Shared_Preferences.setPrefs(_act, IConstant.IS_AADHAR_VERIFIED, is_aadhar_verified);
                            Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_ONE, addhar_front_image);
                            Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_TWO, aadhar_back_image);
                            //  Shared_Preferences.setPrefs(_act, IConstant.USER_GST_PHOTO, gst_photo);
                            //  Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD, pan_image);
                            Shared_Preferences.setPrefs(_act, IConstant.CITY_NAME, city);
                            //   Shared_Preferences.setPrefs(_act, IConstant.POLICE_VERIFICATION_IMAGE, police_verification_image);
                            //   Shared_Preferences.setPrefs(_act, IConstant.DRIVING_LICENSES_IMAGE, driving_licenses_image);
                            //  Shared_Preferences.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");
                            //Intent intent = new Intent(_act, DashboardActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            // startActivity(intent);
                            //finish();
                            updateProfile();


                        } else {
                            Toast.makeText(_act, stringMsg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(_act, "Technical Error", Toast.LENGTH_SHORT).show();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private RequestBody createRequestBodyFromText(String mText) {
        return RequestBody.create(MediaType.parse("text/plain"), mText);
    }


    //Image Picker Whole Code
    private void loadProfile(String url, ImageView ivImage) {
        Log.d(TAG, "Image logo cache path: " + url);
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivImage);
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
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 0); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

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
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, numberGallery);
    }

    public void removePhoneKeypad() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            System.out.println("getCurrentFocus() in frag");
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            IBinder binder = getCurrentFocus().getWindowToken();
            inputManager.hideSoftInputFromWindow(binder,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    public void updateProfile() {

        Log.d(TAG, "updateProfile: ISAadhar " + Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED));
        Log.d(TAG, "updateProfile: Aadhar No " + Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO));
        Log.d(TAG, "updateProfile: ISGST " + Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED));
        Log.d(TAG, "updateProfile: Profile Pic " + Shared_Preferences.getPrefs(_act, IConstant.USER_PHOTO));
        Log.d(TAG, "updateProfile: AADHAR_IMAGE_ONE Pic " + Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_ONE));
        Log.d(TAG, "updateProfile: AADHAR_IMAGE_TWO Pic " + Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_TWO));
        Log.d(TAG, "updateProfile: CUSTOMER_TYPE " + Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE));
        Log.d(TAG, "updateProfile: IS_AADHAR_VERIFIED " + Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED));
        Log.d(TAG, "updateProfile: IS_GST_VERIFIED " + Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED));

        if (Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE) != null &&
                !Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE).isEmpty() &&
                !Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE).equals("null") &&
                !Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE).equals("")) {
            if (Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE).equals("1")) {
                rlEditGSTView.setVisibility(View.GONE);
                tvCompanyName.setVisibility(View.GONE);

                if (Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equals("1")) {
                    tvMainUnVerified.setVisibility(View.GONE);
                    tvMainVerified.setVisibility(View.VISIBLE);
                } else {
                    tvMainUnVerified.setVisibility(View.VISIBLE);
                    tvMainVerified.setVisibility(View.GONE);
                }

            } else {

                if (Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equals("1") &&
                        Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).equals("1")) {
                    tvMainUnVerified.setVisibility(View.GONE);
                    tvMainVerified.setVisibility(View.VISIBLE);
                } else {
                    tvMainUnVerified.setVisibility(View.VISIBLE);
                    tvMainVerified.setVisibility(View.GONE);
                }

                rlEditGSTView.setVisibility(View.VISIBLE);
                tvCompanyName.setVisibility(View.VISIBLE);
            }

        } else {

            /*rlEditGSTView.setVisibility(View.GONE);
            tvCompanyName.setVisibility(View.GONE);*/
        }



        //User Name
        if (Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME) != null && !Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME).equals("")) {
            tvCompanyName.setText(Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME));
        } else {
            tvCompanyName.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equals("null")) {
            tvOwnerName.setText(Helper_Method.toTitleCase(Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME) + " " + Shared_Preferences.getPrefs(_act, IConstant.USER_LAST_NAME)));
        } else {
            tvOwnerName.setText("");
        }

        Log.d(TAG, "Is_Aadhar_Verified: " + Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED));

        /*if (Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED) != null && !Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equals("")) {
            if (Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED) != null && !Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).equals("")) {

                if (Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equalsIgnoreCase("1") && Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).equalsIgnoreCase("1")) {
                    tvMainVerified.setVisibility(View.VISIBLE);
                    tvMainUnVerified.setVisibility(View.GONE);
                } else {
                    tvMainVerified.setVisibility(View.GONE);
                    tvMainUnVerified.setVisibility(View.VISIBLE);
                }
            } else {
                tvMainVerified.setVisibility(View.GONE);
                tvMainUnVerified.setVisibility(View.VISIBLE);
            }
        } else {
            tvMainVerified.setVisibility(View.GONE);
            tvMainUnVerified.setVisibility(View.VISIBLE);
        }*/


        //Single Gst Verification
        if (Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED) != null && !Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).equals("")) {
            if (Shared_Preferences.getPrefs(_act, IConstant.IS_GST_VERIFIED).equalsIgnoreCase("1")) {
                tvGstVerified.setVisibility(View.VISIBLE);
                rlGSTUnVerifiedView.setVisibility(View.GONE);
            } else {
                tvGstVerified.setVisibility(View.GONE);
                rlGSTUnVerifiedView.setVisibility(View.VISIBLE);
            }
        } else {
            tvGstVerified.setVisibility(View.GONE);
            rlGSTUnVerifiedView.setVisibility(View.VISIBLE);
        }

        //Role Checking
      /*  if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equals("null")) {
            if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equalsIgnoreCase(IConstant.OWNER)) {
                tvRole.setText("Owner");
            } else {
                tvRole.setText("Owner / Driver");
            }

        } else {
            tvRole.setText("");
            tvRole.setVisibility(View.GONE);
        }*/
        tvRole.setText("Customer");


        if (Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED) != null && !Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equals("")) {
            if (Shared_Preferences.getPrefs(_act, IConstant.IS_AADHAR_VERIFIED).equalsIgnoreCase("1")) {
                tvAadharVerified.setVisibility(View.VISIBLE);
                tvAadharPending.setVisibility(View.GONE);
                tvAadharNote.setVisibility(View.GONE);
                rlAddhar.setVisibility(View.GONE);
            } else {
                tvAadharVerified.setVisibility(View.GONE);
                tvAadharPending.setVisibility(View.GONE);
                tvAadharNote.setVisibility(View.GONE);
                rlAddhar.setVisibility(View.VISIBLE);
            }
        } else {
            tvAadharVerified.setVisibility(View.GONE);
            tvAadharPending.setVisibility(View.GONE);
            tvAadharNote.setVisibility(View.GONE);
            rlAddhar.setVisibility(View.VISIBLE);
        }


        if (Shared_Preferences.getPrefs(_act, IConstant.USER_PHOTO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_PHOTO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_PHOTO).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.USER_PHOTO).equals("")) {
            Glide.with(_act)
                    .load(Shared_Preferences.getPrefs(_act, IConstant.USER_PHOTO))
                    .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                    .into(ivProfilePic);
        } else {
            Glide.with(_act)
                    .load(R.drawable.default_user)
                    .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                    .into(ivProfilePic);
        }


        if (Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO).equals("null")) {
            tvAadharNo.setText("Aadhar No : " + Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO));
        } else {
            tvAadharNo.setText("");
            tvAadharNo.setVisibility(View.VISIBLE);
        }


        if (Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE).equals("null")) {
            tv_phone.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE));
        } else {
            tv_phone.setText("");
            tv_phone.setVisibility(View.GONE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL).equals("null")) {
            tv_email.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL));
        } else {
            tv_email.setText("");
            tv_email.setVisibility(View.GONE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS).equals("null")) {
            tvAddress.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS));
        } else {
            tvAddress.setText("");
            tvAddress.setVisibility(View.GONE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP) != null &&
                !Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP).isEmpty() &&
                !Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP).equals("null")) {
            tvBloodGroup.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP));
        } else {
            tvBloodGroup.setText("");
            tvBloodGroup.setVisibility(View.GONE);
        }

       /* if (Shared_Preferences.getPrefs(_act, IConstant.USER_ABOUT_US) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ABOUT_US).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ABOUT_US).equals("null")) {
            tvAboutUs.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_ABOUT_US));
        } else {
            tvAboutUs.setText("");
            tvAboutUs.setVisibility(View.GONE);
        }*/


        if (Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME).equals("null")) {
            tvBankName.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME));
        } else {
            tvBankName.setText("");
            tvBankName.setVisibility(View.VISIBLE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO).equals("null")) {
            tvAccNo.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO));
        } else {
            tvAccNo.setText("");
            tvAccNo.setVisibility(View.VISIBLE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE).equals("null")) {
            tvIfscCode.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE));
        } else {
            tvIfscCode.setText("");
            tvIfscCode.setVisibility(View.VISIBLE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY).equals("null")) {
            tvBranchCity.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY));
        } else {
            tvBranchCity.setText("");
            tvBranchCity.setVisibility(View.VISIBLE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO).equals("null")) {
            tvPanNo.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO));
        } else {
            tvPanNo.setText("");
            tvPanNo.setVisibility(View.VISIBLE);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO).equals("null")) {
            tvGSTNo.setText("GST NO :" + Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO));
        } else {
            tvGSTNo.setText("GST NO");
            tvGSTNo.setVisibility(View.VISIBLE);
        }


        if (Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_ONE) != null && !Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_ONE).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_ONE).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_ONE).equals("")) {
            Glide.with(_act)
                    .load(Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_ONE))
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivAadharImgOne);
        } else {
            Glide.with(_act)
                    .load(R.drawable.default_square)
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivAadharImgOne);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_TWO) != null && !Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_TWO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_TWO).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_TWO).equals("")) {
            Glide.with(_act)
                    .load(Shared_Preferences.getPrefs(_act, IConstant.AADHAR_IMAGE_TWO))
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivAadharImgTwo);
        } else {
            Glide.with(_act)
                    .load(R.drawable.default_square)
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivAadharImgTwo);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD).equals("")) {
            Glide.with(_act)
                    .load(Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD))
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivPanCard);
        } else {
            Glide.with(_act)
                    .load(R.drawable.default_square)
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivPanCard);
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_GST_PHOTO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_GST_PHOTO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_GST_PHOTO).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.USER_GST_PHOTO).equals("")) {
            Glide.with(_act)
                    .load(Shared_Preferences.getPrefs(_act, IConstant.USER_GST_PHOTO))
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivGST);
        } else {
            Glide.with(_act)
                    .load(R.drawable.default_square)
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square))
                    .into(ivGST);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfile();
    }


    private void webCallThirdGetUserDetails() {

        Helper_Method.showProgressBar(_act, "Loading...");

        Log.d("VijendraS", "webCallThirdGetUserDetails: ");

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTGetProfile(
                Shared_Preferences.getPrefs(_act, IConstant.USER_ID),
                Shared_Preferences.getPrefs(_act, IConstant.USER_API_TOKEN)
        );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("my_tag_adhar", "onResponseSachinADD: " + response.code());
                String output = "";


                try {
                    output = response.body().string();
                    Log.d("my_tag_adhar", "onResponseSachinADD: " + output);
                    JSONObject i = new JSONObject(output);
                    String stringCode = i.getString(IConstant.RESPONSE_CODE);
                    String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);
                    if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {


                        user_profile_path = i.getString("user_profile_path");
                        user_document_path = i.getString("user_document_path");
                        JSONArray jsonArray = i.getJSONArray("user_data");
                        JSONObject jsonObjectData = jsonArray.getJSONObject(0);
                        String id = jsonObjectData.getString("id");
                        String first_name = jsonObjectData.getString("first_name");
                        String last_name = jsonObjectData.getString("last_name");
                        String email = jsonObjectData.getString("email");
                        String mobile_no = jsonObjectData.getString("mobile_no");
                        String image = user_profile_path + jsonObjectData.getString("image");
                        String role = jsonObjectData.getString("role");
                        String api_token = jsonObjectData.getString("api_token");
                        String referral_code = jsonObjectData.getString("referral_code");
                        String is_driver = jsonObjectData.getString("is_driver");
                        String birthdate = jsonObjectData.getString("birthdate");
                        //String address = jsonObjectData.getString("address");
                        String business_name = jsonObjectData.getString("business_name");
                        String bank_name = jsonObjectData.getString("bank_name");
                        String account_number = jsonObjectData.getString("account_number");
                        String ifsc_code = jsonObjectData.getString("ifsc_code");
                        String branch_name = jsonObjectData.getString("branch_name");
                        String pan_number = jsonObjectData.getString("pan_number");
                        String gst_number = jsonObjectData.getString("gst_number");
                        String aadhar_number = jsonObjectData.getString("aadhar_number");
                        //String is_pan_verified = jsonObjectData.getString("is_pan_verified");
                        String is_gst_verified = jsonObjectData.getString("is_gst_verified");
                        String is_aadhar_verified = jsonObjectData.getString("is_aadhar_verified");
                        String addhar_front_image = user_document_path + jsonObjectData.getString("addhar_front_image");
                        String aadhar_back_image = user_document_path + jsonObjectData.getString("addhar_back_image");
                        //     String pan_image = user_document_path + jsonObjectData.getString("pan_image");
                        // String gst_photo = user_document_path + jsonObjectData.getString("gst_photo");
                        //  String city = jsonObjectData.getString("city");
                        //     String police_verification_image = user_document_path + jsonObjectData.getString("police_verification_image");
                        //     String driving_licenses_image = user_document_path + jsonObjectData.getString("driving_licenses_image");
                        /*String driving_license_number = jsonObjectData.getString("driving_license_number");
                        String driving_license_verified = jsonObjectData.getString("driving_license_verified");*/


                        Log.d("AADHAR", "aadhar_number:" + aadhar_number);

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
                        //Shared_Preferences.setPrefs(_act, IConstant.USER_ADDRESS, address);
                        Shared_Preferences.setPrefs(_act, IConstant.OWNER_BUSINESS_NAME, business_name);
                        Shared_Preferences.setPrefs(_act, IConstant.USER_BANK_NAME, bank_name);
                        Shared_Preferences.setPrefs(_act, IConstant.USER_ACCOUNT_NO, account_number);
                        Shared_Preferences.setPrefs(_act, IConstant.USER_IFSC_CODE, ifsc_code);
                        Shared_Preferences.setPrefs(_act, IConstant.USER_BANK_BRANCH_CITY, branch_name);
                        Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD_NO, pan_number);
                        Shared_Preferences.setPrefs(_act, IConstant.USER_GST_NO, gst_number);
                        Shared_Preferences.setPrefs(_act, IConstant.USER_AADHAR_NO, aadhar_number);
                        // Shared_Preferences.setPrefs(_act, IConstant.IS_PAN_VERIFIED, is_pan_verified);
                        Shared_Preferences.setPrefs(_act, IConstant.IS_GST_VERIFIED, is_gst_verified);
                        Shared_Preferences.setPrefs(_act, IConstant.IS_AADHAR_VERIFIED, is_aadhar_verified);
                        Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_ONE, addhar_front_image);
                        Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_TWO, aadhar_back_image);
                        // Shared_Preferences.setPrefs(_act, IConstant.USER_GST_PHOTO, gst_photo);
                        //Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD, pan_image);
                        //Shared_Preferences.setPrefs(_act, IConstant.CITY_NAME, city);
                        //Shared_Preferences.setPrefs(_act, IConstant.POLICE_VERIFICATION_IMAGE, police_verification_image);
                        //Shared_Preferences.setPrefs(_act, IConstant.DRIVING_LICENSES_IMAGE, driving_licenses_image);
                        //Shared_Preferences.setPrefs(_act, IConstant.DRIVING_LICENSES_NO, driving_license_number);
                        //Shared_Preferences.setPrefs(_act, IConstant.IS_DRIVER_VERIFIED, driving_license_verified);
                        Helper_Method.dismissProgessBar();
                        updateProfile();

                    } else {
                        Log.d("Login", "onResponse: " + stringMsg);
                        Helper_Method.dismissProgessBar();
                    }
                } catch (JSONException | IOException e) {
                    Helper_Method.dismissProgessBar();
                    Log.d("JSONException", "onResponse: " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(_act, "Technical Error", Toast.LENGTH_SHORT).show();
                Helper_Method.dismissProgessBar();

            }
        });
    }

}