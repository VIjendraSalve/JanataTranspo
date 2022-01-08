package com.wht.janatatraspo.InitialActivities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IErrors;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends BaseActivity {

    private static final String TAG = "UpdateProfileActivity";
    private Activity _act;
    private TextView toolbar_title;
    private Validations validations;
    private ConnectionDetector connectionDetector;


    //Date Of Birth
    private Date currentDate;
    private SimpleDateFormat df, ymd;
    private String formattedDate;
    private DatePickerDialog datePickerDialogFromDate;
    private String strCurrentDateToSet = "Select Date", selectedDate;
    private TextView tvDOB;


    //Personal Profile
    private EditText etFirstName, etMobile, etEmail, etAddress;
    private EditText etLastName, etPancard, etaadhar, etBillingAddress, etBloodGroup;
    private EditText etAboutMe, etBusinessName, etBusinessGSTNO;
    private EditText etBank, etAccountNo, etIFSCode;
    private EditText etBranchName;
    private TextView tvRole;
    private RelativeLayout rlBusinessName, rlBusinessGSTNO;
    private ExtendedFloatingActionButton efaSubmit;

    private RadioButton rb_individual, rb_company;
    private String isIndividual = "1";


    //Image Selection
    private String imagePath = "", receiveImgPath;
    private Uri iurl;
    private File file = null;
    private MultipartBody.Part bodyUserImage = null;
    private MultipartBody.Part bodyAadharFront = null;
    private MultipartBody.Part bodyAadharBack = null;
    private MultipartBody.Part bodyPanCard = null;
    private MultipartBody.Part bodyPoliceVerification = null;
    private RequestBody requestFile = null;

    private String user_profile_path = null, user_document_path = null;

/*
    //State Spinner Zone
    private ArrayList<StateObject> stateObjectArrayList;
    private SearchableSpinner spinnerStatelist;
    private ArrayAdapter<StateObject> spinnerStatelist_Adapter;
    private String strStateId = "0", strStateName;

    //City Spinner Zone
    private ArrayList<CityObject> stateObjectArrayList;
    private SearchableSpinner spinnerStatelist;
    private ArrayAdapter<CityObject> spinnerStatelist_Adapter;
    private String strCityId = "0", strCityName;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        _act = UpdateProfileActivity.this;
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);
        validations = new Validations();
        removePhoneKeypad();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Helper_Method.toTitleCase("Update Profile"));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.colorPrimary)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Update Profile");
        toolbar_title.setTextColor(getResources().getColor(R.color.white));

        currentDate = Calendar.getInstance().getTime();
        System.out.println("Current time => " + currentDate);
        df = new SimpleDateFormat("dd MMM yyyy");
        ymd = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(currentDate);
        // strCurrentDateToSet = ymd.format(currentDate);
        //System.out.println("Formated Date => " + formattedDate);
        // System.out.println("Formated Date => " + strCurrentDateToSet);

        tvRole = findViewById(R.id.tvRole);
        etFirstName = findViewById(R.id.etFirstName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etLastName = findViewById(R.id.etLastName);
        etPancard = findViewById(R.id.etPancard);
        etaadhar = findViewById(R.id.etaadhar);
        etBillingAddress = findViewById(R.id.etBillingAddress);
        etBloodGroup = findViewById(R.id.etBloodGroup);
        //etAboutMe = findViewById(R.id.etAboutMe);
        etBusinessName = findViewById(R.id.etBusinessName);
        etBusinessGSTNO = findViewById(R.id.etBusinessGSTNO);
        etBank = findViewById(R.id.etBank);
        etAccountNo = findViewById(R.id.etAccountNo);
        etIFSCode = findViewById(R.id.etIFSCode);
        etBranchName = findViewById(R.id.etBranchName);
        tvDOB = findViewById(R.id.tvDOB);
        rlBusinessName = findViewById(R.id.rlBusinessName);
        rlBusinessGSTNO = findViewById(R.id.rlBusinessGSTNO);
        efaSubmit = findViewById(R.id.efaSubmit);
        rb_individual = findViewById(R.id.rb_individual);
        rb_company = findViewById(R.id.rb_company);

        rlBusinessName.setVisibility(View.GONE);
        rlBusinessGSTNO.setVisibility(View.GONE);

        rb_individual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rlBusinessName.setVisibility(View.GONE);
                    rlBusinessGSTNO.setVisibility(View.GONE);
                    isIndividual = "1";
                }
            }
        });

        rb_company.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rlBusinessName.setVisibility(View.VISIBLE);
                    rlBusinessGSTNO.setVisibility(View.VISIBLE);
                    isIndividual = "2"; // company
                }
            }
        });

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.add(Calendar.YEAR, -18);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialogFromDate = new DatePickerDialog(_act,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                formattedDate = df.format(date);
                                strCurrentDateToSet = ymd.format(date);
                                tvDOB.setText(formattedDate);
                                //refreshData();
                                // webServiceCall();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogFromDate.show();
                //datePickerDialogFromDate.getDatePicker().setMinDate(System.currentTimeMillis());
                // datePickerDialogFromDate.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialogFromDate.getDatePicker().setMaxDate(c.getTimeInMillis());
                //datePickerDialogFromDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //datePickerDialogFromDate.getDatePicker().setMinDate(System.currentTimeMillis() - (1* 24 * 60 * 60 * 1000));
                //datePickerDialogFromDate.getDatePicker().setMaxDate(System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000));
            }
        });


        efaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {
                    //Default Dress Code Webcall
                    if (connectionDetector.checkConnection(_act)) {
                        removePhoneKeypad();
                        webcallUpdateProfile();

                    } else {
                        Helper_Method.toaster(_act, getResources().getString(R.string.string_internet_connection_warning));
                    }
                }

            }
        });

        updateProfile();
    }

    private void webcallUpdateProfile() {

        Helper_Method.showProgressBar(_act, getResources().getString(R.string.update_profile));

        //Create Upload Server Client
        Interface service = IUrls.getApiService();

        // Logo Image
        file = null;
        bodyUserImage = null;
        requestFile = null;
        int compressionRatioLogoImg = 50; //1 == originalImage, 2 = 50% compression, 4=25% compress
        if (!imagePath.equalsIgnoreCase("")) {

            file = new File(String.valueOf(imagePath));
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatioLogoImg, new FileOutputStream(file));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            //requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            bodyUserImage = MultipartBody.Part.createFormData("image", "", requestFile);
            bodyAadharFront = MultipartBody.Part.createFormData("addhar_front_image", "", requestFile);
            bodyAadharBack = MultipartBody.Part.createFormData("addhar_back_image", "", requestFile);
            bodyPanCard = MultipartBody.Part.createFormData("pan_photo", "", requestFile);
            bodyPoliceVerification = MultipartBody.Part.createFormData("gst_photo", "", requestFile);


        }
        Log.d("mytag", "Logo file: " + file);
        Log.d("mytag", "strCurrentDateToSet " + strCurrentDateToSet);

        /*UpdateProfileActivity.this.createRequestBodyFromText(etPancard.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBusinessGSTNO.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etaadhar.getText().toString().trim()),*/

        Call<ResponseBody> result = service.POSTUpdateProfileCustomer(
                UpdateProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_ID)),
                UpdateProfileActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(_act, IConstant.USER_API_TOKEN)),
                UpdateProfileActivity.this.createRequestBodyFromText(etFirstName.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etLastName.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etEmail.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(strCurrentDateToSet),
                UpdateProfileActivity.this.createRequestBodyFromText(isIndividual),
                UpdateProfileActivity.this.createRequestBodyFromText(etAddress.getText().toString()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBusinessName.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBank.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etAccountNo.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etIFSCode.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBranchName.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etPancard.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBusinessGSTNO.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etaadhar.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBusinessName.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBillingAddress.getText().toString().trim()),
                UpdateProfileActivity.this.createRequestBodyFromText(etBloodGroup.getText().toString().trim())
        );

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

                        Log.d("test123", "onResponse: 00");
                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            Helper_Method.dismissProgessBar();
                            Log.d("test123", "onResponse: 01");
                            user_profile_path = i.getString("user_profile_path");
                            user_document_path = i.getString("user_document_path");
                            Log.d("test123", "onResponse: 02");
                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);
                            String id = jsonObjectData.getString("id");
                            Log.d("Vijendra", "onResponse: " + jsonObjectData.getString("customer_type"));
                            String customer_type = jsonObjectData.getString("customer_type");
                            String first_name = jsonObjectData.getString("first_name");
                            String last_name = jsonObjectData.getString("last_name");
                            Log.d("test123", "onResponse: 03");
                            String email = jsonObjectData.getString("email");
                            String mobile_no = jsonObjectData.getString("mobile_no");
                            String image = user_profile_path + jsonObjectData.getString("image");
                            String role = jsonObjectData.getString("role");
                            String api_token = jsonObjectData.getString("api_token");
                            Log.d("test123", "onResponse: 04");
                            //String is_driver = jsonObjectData.getString("is_driver");
                            String birthdate = jsonObjectData.getString("birthdate");
                            //String address = jsonObjectData.getString("address");
                            String business_name = jsonObjectData.getString("business_name");
                            String bank_name = jsonObjectData.getString("bank_name");
                            Log.d("test123", "onResponse: 05");
                            String account_number = jsonObjectData.getString("account_number");
                            String ifsc_code = jsonObjectData.getString("ifsc_code");
                            Log.d("test123", "onResponse: 1");
                            String branch_name = jsonObjectData.getString("branch_name");
                            String pan_number = jsonObjectData.getString("pan_number");
                            String gst_number = jsonObjectData.getString("gst_number");
                            String aadhar_number = jsonObjectData.getString("aadhar_number");
                            //String is_pan_verified = jsonObjectData.getString("is_pan_verified");
                            String is_gst_verified = jsonObjectData.getString("is_gst_verified");
                            Log.d("test123", "onResponse: 2");
                            String is_aadhar_verified = jsonObjectData.getString("is_aadhar_verified");
                            String blood_group = jsonObjectData.getString("blood_group");
                            String billing_address = jsonObjectData.getString("billing_address");
                            //String addhar_front_image = user_document_path + jsonObjectData.getString("addhar_front_image");
                            // String aadhar_back_image = user_document_path + jsonObjectData.getString("aadhar_back_image");
                            //String pan_image = user_document_path + jsonObjectData.getString("pan_photo");
                            //String gst_photo = user_document_path + jsonObjectData.getString("gst_photo");
                            Log.d("test123", "onResponse: 3");
                            // String city = jsonObjectData.getString("city");
                            //String police_verification_image = user_document_path + jsonObjectData.getString("police_verification_image");
                            //String driving_licenses_image = user_document_path + jsonObjectData.getString("driving_licenses_image");


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
                            //Shared_Preferences.setPrefs(_act, IConstant.USER_IS_DRIVER, is_driver);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_DOB, birthdate);
                            Log.d("test123", "onResponse: 4");
                            //Shared_Preferences.setPrefs(_act, IConstant.USER_ADDRESS, address);
                            Shared_Preferences.setPrefs(_act, IConstant.OWNER_BUSINESS_NAME, business_name);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_BANK_NAME, bank_name);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_ACCOUNT_NO, account_number);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_IFSC_CODE, ifsc_code);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_BANK_BRANCH_CITY, branch_name);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD_NO, pan_number);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_GST_NO, gst_number);
                            Log.d("test123", "onResponse: 5");
                            Shared_Preferences.setPrefs(_act, IConstant.USER_AADHAR_NO, aadhar_number);
                            //Shared_Preferences.setPrefs(_act, IConstant.IS_PAN_VERIFIED, is_pan_verified);
                            Shared_Preferences.setPrefs(_act, IConstant.IS_GST_VERIFIED, is_gst_verified);
                            Shared_Preferences.setPrefs(_act, IConstant.IS_AADHAR_VERIFIED, is_aadhar_verified);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_BLOODGROUP, blood_group);
                            Shared_Preferences.setPrefs(_act, IConstant.USER_BILLINGADDRESS, billing_address);
                            Log.d("test123", "onResponse: 6");
                            //Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_ONE, addhar_front_image);
                            //Shared_Preferences.setPrefs(_act, IConstant.AADHAR_IMAGE_TWO, aadhar_back_image);
                            //Shared_Preferences.setPrefs(_act, IConstant.USER_GST_PHOTO, gst_photo);
                            //Shared_Preferences.setPrefs(_act, IConstant.USER_PAN_CARD, pan_image);

                            //Shared_Preferences.setPrefs(_act, IConstant.CITY_NAME, city);
                            //Shared_Preferences.setPrefs(_act, IConstant.POLICE_VERIFICATION_IMAGE, police_verification_image);
                            //Shared_Preferences.setPrefs(_act, IConstant.DRIVING_LICENSES_IMAGE, driving_licenses_image);
                            //  Shared_Preferences.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");
                            Log.d("test123", "onResponse: 7");
                            Intent intent = new Intent(_act, ProfileActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();


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

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        editText.setClickable(false);
        //editText.setBackground(Drawable.bo);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackgroundDrawable(ContextCompat.getDrawable(_act, R.drawable.border_layout));
        } else {
            editText.setBackground(ContextCompat.getDrawable(_act, R.drawable.border_layout));
        }
        // editText.setTextColor(Color.BLACK);
    }


    private boolean isValid() {

      /*  if (validations.isBlank(etBusinessName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etBusinessName.startAnimation(shake);
            etBusinessName.setError(IErrors.EMPTY);
            return false;
        }*/

        if (validations.isBlank(etFirstName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etFirstName.startAnimation(shake);
            etFirstName.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etLastName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etLastName.startAnimation(shake);
            etLastName.setError(IErrors.EMPTY);
            return false;
        }

       /* if (validations.isBlank(etEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etEmail.startAnimation(shake);
            etEmail.setError(IErrors.EMPTY);
            return false;
        }
        if (!validations.isValidEmail(etEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etEmail.startAnimation(shake);
            etEmail.setError(IErrors.INVALID_EMAIL);
            return false;
        }*/

       /* if (validations.isBlank(etMobile)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);
            etMobile.setError(getResources().getString(R.string.error_field_required));
            return false;
        }
        if (!validations.isValidPhone(etMobile)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);
            etMobile.setError(getResources().getString(R.string.error_invalid_mobile));
            return false;
        }*/

        if (validations.isBlank(etBloodGroup)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etBloodGroup.startAnimation(shake);
            etBloodGroup.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isTextviewBlank(tvDOB)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            tvDOB.startAnimation(shake);
            tvDOB.setError(IErrors.INVALID);
            return false;
        }

        /*if (validations.isPanCard(etPancard)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etPancard.startAnimation(shake);
            etPancard.setError(IErrors.EMPTY);
            return false;
        }*/

       /* if (validations.isBlank(etBillingAddress)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etBillingAddress.startAnimation(shake);
            etBillingAddress.setError(IErrors.EMPTY);
            return false;
        }*/

        /*if (validations.isBlank(etBank)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etBank.startAnimation(shake);
            etBank.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etAccountNo)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etAccountNo.startAnimation(shake);
            etAccountNo.setError(IErrors.EMPTY);
            return false;
        }


        if (validations.isBlank(etIFSCode)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etIFSCode.startAnimation(shake);
            etIFSCode.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etBranchName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etBranchName.startAnimation(shake);
            etBranchName.setError(IErrors.EMPTY);
            return false;
        }*/
/*
        if (validations.isBlank(etaadhar)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etaadhar.startAnimation(shake);
            etaadhar.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isaadharCard(etaadhar)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etaadhar.startAnimation(shake);
            etaadhar.setError(IErrors.INVALID);
            return false;
        }*/

     /*   if (validations.isBlank(etAddress)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etAddress.startAnimation(shake);
            etAddress.setError(IErrors.EMPTY);
            return false;
        }*/
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private RequestBody createRequestBodyFromText(String mText) {
        return RequestBody.create(MediaType.parse("text/plain"), mText);
    }


    public void updateProfile() {


     /*   if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equals("null")) {
            if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equalsIgnoreCase("2")) {
                tvRole.setText("Owner");
                rlBusinessName.setVisibility(View.VISIBLE);
                rlBusinessGSTNO.setVisibility(View.GONE);

            } else if (Shared_Preferences.getPrefs(_act, IConstant.USER_ROLE_ID).equalsIgnoreCase("3")) {
                tvRole.setText("Driver");
                rlBusinessName.setVisibility(View.GONE);
                rlBusinessGSTNO.setVisibility(View.GONE);
            } else {
                tvRole.setText("Customer");
                rlBusinessName.setVisibility(View.VISIBLE);
                rlBusinessGSTNO.setVisibility(View.GONE);
            }

        } else {
            tvRole.setText("");
        }*/
        tvRole.setText("Customer");
        Log.d(TAG, "CUSTOMER_TYPE: " + Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE));
        if (Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE) != null &&
                !Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE).isEmpty() &&
                !Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE).equals("null")) {

            if (Shared_Preferences.getPrefs(_act, IConstant.CUSTOMER_TYPE).equals("1")) {
                rlBusinessName.setVisibility(View.GONE);
                rlBusinessGSTNO.setVisibility(View.GONE);
                rb_individual.setChecked(true);
                rb_company.setChecked(false);
            } else {
                rb_individual.setChecked(false);
                rb_company.setChecked(true);
                rlBusinessName.setVisibility(View.VISIBLE);
                rlBusinessGSTNO.setVisibility(View.VISIBLE);
            }
        }else {
            rlBusinessName.setVisibility(View.GONE);
            rlBusinessGSTNO.setVisibility(View.GONE);
        }


        if (Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME) != null && !Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME).equals("null")) {
            etBusinessName.setText(Shared_Preferences.getPrefs(_act, IConstant.OWNER_BUSINESS_NAME));
        } else {
            etBusinessName.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO).equals("null")) {
            etBusinessGSTNO.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_GST_NO));
        } else {
            etBusinessGSTNO.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME).equals("null") && !Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME).equals("")) {
            etFirstName.setText(Helper_Method.toTitleCase(Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME)));
            etLastName.setText(Helper_Method.toTitleCase(Shared_Preferences.getPrefs(_act, IConstant.USER_LAST_NAME)));
        } else {
            etFirstName.setHint("First Name");
            etLastName.setHint("Last Name");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_DOB) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_DOB).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_DOB).equals("null")) {
            try {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = df.format(inputFormat.parse(Shared_Preferences.getPrefs(_act, IConstant.USER_DOB)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            strCurrentDateToSet = Shared_Preferences.getPrefs(_act, IConstant.USER_DOB);
            Log.d(TAG, "updateData: " + Shared_Preferences.getPrefs(_act, IConstant.USER_DOB));
            Log.d(TAG, "updateData: " + strCurrentDateToSet);
            Log.d(TAG, "updateData: " + formattedDate);

            tvDOB.setText(formattedDate);

        } else {
            tvDOB.setText("");
        }

       /* if (Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE).equals("null")) {
            tv_phone.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE));
        } else {
            tv_phone.setText("");
            tv_phone.setVisibility(View.GONE);
        }
*/
        if (Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL).equals("null")) {
            etEmail.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL));
        } else {
            etEmail.setText("");
        }

        Log.d(TAG, "USERBLOODGROUP: " + Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP));

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP) != null &&
                !Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP).isEmpty() &&
                !Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP).equals("null")) {
            etBloodGroup.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_BLOODGROUP));
        } else {
            etBloodGroup.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_BILLINGADDRESS) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_BILLINGADDRESS).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_BILLINGADDRESS).equals("null")) {
            etBillingAddress.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_BILLINGADDRESS));
        } else {
            etBillingAddress.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS).equals("null")) {
            etAddress.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_ADDRESS));
        } else {
            etAddress.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO).equals("null")) {
            etPancard.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_PAN_CARD_NO));
        } else {
            etPancard.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO).equals("null")) {
            etaadhar.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_AADHAR_NO));
        } else {
            etaadhar.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME).equals("null")) {
            etBank.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_NAME));
        } else {
            etBank.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO).equals("null")) {
            etAccountNo.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_ACCOUNT_NO));
        } else {
            etAccountNo.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE).equals("null")) {
            etIFSCode.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_IFSC_CODE));
        } else {
            etIFSCode.setText("");
        }

        if (Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY) != null && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY).isEmpty() && !Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY).equals("null")) {
            etBranchName.setText(Shared_Preferences.getPrefs(_act, IConstant.USER_BANK_BRANCH_CITY));
        } else {
            etBranchName.setText("");
        }

    }

}