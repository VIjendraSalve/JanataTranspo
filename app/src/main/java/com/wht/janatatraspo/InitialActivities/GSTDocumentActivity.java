package com.wht.janatatraspo.InitialActivities;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IErrors;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.DateTimeFormat;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GSTDocumentActivity extends BaseActivity {

    private Activity _act;
    public static final int REQUEST_AADHAR_ONE = 200;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView toolbar_title;

    private String user_profile_path = null, user_document_path = null;
    private String frontAadharImagePath = "";
    private Uri iurl;
    private File frontFile = null;
    private MultipartBody.Part bodyAadharFrontImg = null;
    private RequestBody frontRequestFile = null;

    //Private Aadhar image upload
    private ImageView ivAadharImgOne, ivEditAadharOne;
    private ExtendedFloatingActionButton efaSubmit;
    private TextView tvVerify, tvVerifiedUser;
    private EditText etGSTNo;
    private boolean boolStatus = false;
    private String role = null;
    private String verified_document_status = "1", client_id, gstin, pan_number, business_name, legal_name;
    private String center_jurisdiction, state_jurisdiction, date_of_registration, constitution_of_business, taxpayer_type, gstin_status;
    private String date_of_cancellation, field_visit_conducted, address;

    //Verification
    private LinearLayout llGstDetails;
    private TextView tvCompanyName, tvOwnerName, tvRegDate;
    private String strFullJSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gstdocument);

        role = getIntent().getStringExtra("role");

        _act = GSTDocumentActivity.this;
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
        toolbar_title.setText("GST Verification");
        toolbar_title.setTextColor(getResources().getColor(R.color.black));
        Helper_Method.setFontToolbard(_act, toolbar_title);

        //Adhar Photo Upload Image
        ivAadharImgOne = findViewById(R.id.ivAadharImgOne);
        ivEditAadharOne = findViewById(R.id.ivEditAadharOne);
        llGstDetails = findViewById(R.id.llGstDetails);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvOwnerName = findViewById(R.id.tvOwnerName);
        tvRegDate = findViewById(R.id.tvRegDate);
        efaSubmit = findViewById(R.id.efaSubmit);

        //Profile Image Upload
        tvVerify = findViewById(R.id.tvVerify);
        tvVerify.setVisibility(View.GONE);
        efaSubmit.setVisibility(View.GONE);
        tvVerifiedUser = findViewById(R.id.tvVerifiedUser);
        etGSTNo = findViewById(R.id.etGSTNo);

        etGSTNo.addTextChangedListener(new TextWatcher() {

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
                if (s.length() != 0) {
                    tvVerify.setVisibility(View.VISIBLE);
                } else {
                    tvVerify.setVisibility(View.GONE);
                }
                // field2.setText("");
            }
        });
        ///disableEditText(etGSTNo);



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


        efaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    if (boolStatus) {
                       /* if (frontAadharImagePath != null && !frontAadharImagePath.isEmpty() && !frontAadharImagePath.equals("null")) {
                            webcallVerifiedAadhar();
                        } else {
                            Helper_Method.toaster(_act, "Select GST Document");
                        }*/
                        webcallVerifiedAadhar();
                    } else {
                        Helper_Method.toaster(_act, "Verified GST");
                    }
                }
            }
        });

        tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    webCallThirdPartyAadharApi(etGSTNo.getText().toString());
                }

            }
        });
    }

    private void webCallThirdPartyAadharApi(String aadhar_no) {

        boolStatus = false;
        tvVerifiedUser.setVisibility(View.GONE);
        llGstDetails.setVisibility(View.GONE);
        Helper_Method.showProgressBar(_act, "Authentication...");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id_number", aadhar_no);

        Interface api = IUrls.getApiThirdPartyService();
        Call<ResponseBody> result = api.POSTGSTVerification(params);

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
                            gstin = jsonObject.getString("gstin");
                            pan_number = jsonObject.getString("pan_number");
                            business_name = jsonObject.getString("business_name");
                            legal_name = jsonObject.getString("legal_name");
                            center_jurisdiction = jsonObject.getString("center_jurisdiction");
                            state_jurisdiction = jsonObject.getString("state_jurisdiction");
                            date_of_registration = jsonObject.getString("date_of_registration");
                            constitution_of_business = jsonObject.getString("constitution_of_business");
                            taxpayer_type = jsonObject.getString("taxpayer_type");
                            gstin_status = jsonObject.getString("gstin_status");
                            date_of_cancellation = jsonObject.getString("date_of_cancellation");
                            field_visit_conducted = jsonObject.getString("field_visit_conducted");
                            address = jsonObject.getString("address");
                            strFullJSON = jsonObject.toString();
                            boolStatus = true;
                            tvVerifiedUser.setVisibility(View.VISIBLE);


                            efaSubmit.setVisibility(View.VISIBLE);
                            llGstDetails.setVisibility(View.VISIBLE);
                            tvCompanyName.setText(Helper_Method.toTitleCase(business_name));
                            tvOwnerName.setText(state_jurisdiction);
                            tvRegDate.setText(DateTimeFormat.getDate(date_of_registration));

                            Helper_Method.dismissProgessBar();
                            Helper_Method.removePhoneKeypad(_act);

                        } else {
                            Log.d("Login", "onResponse: " + stringMsg);
                            Helper_Method.dismissProgessBar();
                            boolStatus = false;
                            tvVerifiedUser.setVisibility(View.GONE);
                            llGstDetails.setVisibility(View.GONE);
                        }
                    } catch (JSONException | IOException e) {
                        Helper_Method.dismissProgessBar();
                        Log.d("JSONException", "onResponse: " + e.getMessage());
                        boolStatus = false;
                        tvVerifiedUser.setVisibility(View.GONE);
                        llGstDetails.setVisibility(View.GONE);
                    }
                } else if(response.code()==500)
                {
                    Helper_Method.dismissProgessBar();
                    boolStatus = false;
                    tvVerifiedUser.setVisibility(View.GONE);
                    llGstDetails.setVisibility(View.GONE);
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
                }else {
                    Helper_Method.dismissProgessBar();
                    boolStatus = false;
                    tvVerifiedUser.setVisibility(View.GONE);
                    Helper_Method.toaster(_act, "Invalid Number");
                    llGstDetails.setVisibility(View.GONE);
                    new AlertDialog.Builder(_act)
                            .setTitle("Alert")
                            .setMessage("You Enter Invalid Number")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialogInterface, int arg1) {
                                    // ActivityCompat.finishAffinity(_act);
                                    dialogInterface.dismiss();
                                    etGSTNo.setText("");
                                }
                            }).create().show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(_act, "Technical Error", Toast.LENGTH_SHORT).show();
                Helper_Method.dismissProgessBar();
                boolStatus = false;
                tvVerifiedUser.setVisibility(View.GONE);
                llGstDetails.setVisibility(View.GONE);
            }
        });
    }

    private boolean isValid() {

        if (validations.isBlank(etGSTNo)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etGSTNo.startAnimation(shake);
            etGSTNo.setError(IErrors.EMPTY);
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
                GSTDocumentActivity.this.openSettings();
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
            bodyAadharFrontImg = MultipartBody.Part.createFormData("gst_photo", frontFile.getName(), frontRequestFile);
        }

        Log.d("mytag", "Front file: " + frontFile);

        Call<ResponseBody> result = service.POSTGSTVerification(
                GSTDocumentActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_ID)),
                GSTDocumentActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_API_TOKEN)),
                GSTDocumentActivity.this.createRequestBodyFromText(role),
                GSTDocumentActivity.this.createRequestBodyFromText(verified_document_status),
                GSTDocumentActivity.this.createRequestBodyFromText(gstin_status),
                GSTDocumentActivity.this.createRequestBodyFromText(address),
                GSTDocumentActivity.this.createRequestBodyFromText(date_of_registration),
                GSTDocumentActivity.this.createRequestBodyFromText(business_name),
                GSTDocumentActivity.this.createRequestBodyFromText(gstin),
                GSTDocumentActivity.this.createRequestBodyFromText(strFullJSON),
                bodyAadharFrontImg);

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
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        } else {
                            Constants.AlertDailogue(stringMsg, _act);
                           // Toast.makeText(_act, stringMsg, Toast.LENGTH_SHORT).show();
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
    private void loadFirstAadhar(String url) {
        Log.d(TAG, "Image logo cache path IMG1: " + url);
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivAadharImgOne);
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
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
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
}