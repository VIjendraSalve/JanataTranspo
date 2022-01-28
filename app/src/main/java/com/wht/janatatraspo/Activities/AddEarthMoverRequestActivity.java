package com.wht.janatatraspo.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wht.janatatraspo.Adapter.AdapterImages;
import com.wht.janatatraspo.Adapter.VehicleListActivityAdapter;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.RecyclerItemClickListener;
import com.wht.janatatraspo.Location.ActivityGetLocation;
import com.wht.janatatraspo.MainActivity;
import com.wht.janatatraspo.Model.CityObject;
import com.wht.janatatraspo.Model.ImagePOJO;
import com.wht.janatatraspo.Model.State;
import com.wht.janatatraspo.Model.VehicleType;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Camera;
import com.wht.janatatraspo.my_library.Constants;
import com.wht.janatatraspo.my_library.MyConfig;
import com.wht.janatatraspo.my_library.MyValidator;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wht.janatatraspo.Constant.IConstant.RC_IMAGE;
import static com.wht.janatatraspo.my_library.MyConfig.prepareFilePart;

public class AddEarthMoverRequestActivity extends BaseActivity implements Camera.AsyncResponse {

    final Calendar myCalendar = Calendar.getInstance();
    private final int REQUEST_CODE_MEDIA_SELECT = 200;
    ActivityResultLauncher<Intent> launchSomeActivity = null, launchDropActivity = null;
    private Activity _act;
    private AppCompatButton button_submit;
    private String isNegotiable = "2", isHourly = "1";

    //vehicle type
    private ArrayList<VehicleType> vehicleTypeArrayList;
    private RecyclerView recyclerViewVehicleType;
    private VehicleListActivityAdapter mAdapter;
    private String vehicleType = "";

    //other
    private EditText et_Destinationlatlong, et_Expectedprice, et_hours, et_hoursPerDay, et_NoofDays, et_Requireddate, et_remark, et_TotalAmount;
    private MaterialCheckBox chk_isnegotiaable;
    private RadioButton rb_hourly, rb_daily;
    private RelativeLayout rlHour;
    private LinearLayout ll_hourly;

    //City Spinner Zone destination
    private ArrayList<CityObject> destinationupcityObjectArrayList;
    private SearchableSpinner spinnerDestinationCity;
    private ArrayAdapter<CityObject> spinnerDestination_Adapter;
    private String strDestinationId = "0", strDestinationName;
    private String droplat = "", droplong = "";
    private EditText et_RequiredTime;
    private String requiredTime="00:00";

    //DropCity Spinner Zone destination
    private ArrayList<State> stateDropArrayList;
    private SearchableSpinner spnr_stateDrop;
    private ArrayAdapter<State> spinnerStateDrop_Adapter;
    private String strStateDropupId = "0", strStateDropName;

    //DropCity Spinner Zone destination
    private SearchableSpinner spnr_vehicle;
    private ArrayAdapter<VehicleType> vehicleTypeArrayAdapter;
    private String strVehicelId = "0", strVehicleName="";

    //multiple image
    private static final int REQUEST_OWNER = 141;
    public static int image_type = 0;
    private Camera camera;
    private TextView tv_select_vehicle_rc_image;
    private ImageView iv_vehicle_rc_image;
    private boolean isUpdate = false;
    private int type = 0;
    private RecyclerView rv_vehicle_rc_image;
    private AdapterImages adapterImagesRC;
    private ArrayList<ImagePOJO> RC_IMAGE_DATA = new ArrayList<>();
    private int update_position = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_earth_mover_request);

        _act = AddEarthMoverRequestActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Earth Mover Request ");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initTest();

    }

    @Override
    public void processFinish(String result, int img_no) {
        String[] parts = result.split("/");
        String imagename = parts[parts.length - 1];
        // Log.d("Image_path",result+" "+img_no);
        switch (image_type) {

            case RC_IMAGE:
                if (!isUpdate) {
                    RC_IMAGE_DATA.add(new ImagePOJO("0", "" + imagename, prepareFilePart("vehicle_doc_rc[" + (RC_IMAGE_DATA.size() - 1) + "]", result), result));
                    adapterImagesRC.notifyDataSetChanged();
                } else {
                    RC_IMAGE_DATA.get(update_position).setImg_name(imagename);
                    RC_IMAGE_DATA.get(update_position).setImage_path_multipart(prepareFilePart("vehicle_doc_rc[" + (RC_IMAGE_DATA.size() - 1) + "]", result));
                    RC_IMAGE_DATA.get(update_position).setImg_path(result);
                    adapterImagesRC.notifyItemChanged(update_position);
                }
                break;
        }

    }

    private boolean validateFields() {

        boolean result = true;
        if (!MyValidator.isValidField(et_Destinationlatlong)) {
            result = false;
        }
        if (!MyValidator.isValidField(et_Expectedprice)) {
            result = false;
        }

        if (!MyValidator.isValidField(et_hours)) {
            result = false;
        }

       /* if (!MyValidator.isValidField(et_NoofDays)) {
            result = false;
        }*/

        if (!MyValidator.isValidField(et_remark)) {
            result = false;
        }
        if (!MyValidator.isValidSpinner(spinnerDestinationCity)) {
            result = false;
        }

        return result;
    }

    private void webcallPickStateList() {

        Helper_Method.showProgressBar(_act, "Loading...");

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTState("101");

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {

                    stateDropArrayList = new ArrayList<>();
                    stateDropArrayList.clear();

                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase("true")) {
                            stateDropArrayList.add(new State("0", "Select Destination State", "0"));
                            JSONArray jsonArray = i.getJSONArray("states_list");
                            //pickupcityObjectArrayList.add(new CountryNameObject("0", "Select Country ", "Date"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    stateDropArrayList.add(new State(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    // scheduleDismiss();

                                }
                            }

                            if (stateDropArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();


                            } else {

                                // scheduleDismiss();

                                spnr_stateDrop.setTitle("Select Destination State");
                                spinnerStateDrop_Adapter = new ArrayAdapter<State>(_act, android.R.layout.simple_spinner_item, stateDropArrayList);
                                spinnerStateDrop_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnr_stateDrop.setAdapter(spinnerStateDrop_Adapter);
                                spnr_stateDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strStateDropupId = stateDropArrayList.get(i).id;
                                        strStateDropName = stateDropArrayList.get(i).name;
                                        if(i != 0)
                                            webcallPickCityList(strStateDropupId);

                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


                                if (stateDropArrayList.size() == 0) {

                                } else {
                                    /*if (strActionFlag.equalsIgnoreCase(IConstant.UPDATE)) {
                                        if (driverListObject.city != null && !driverListObject.city.isEmpty() && !driverListObject.city.equals("null")) {

                                            for (int k = 0; k < pickupcityObjectArrayList.size(); k++) {
                                                if (pickupcityObjectArrayList.get(k).getId().equals(driverListObject.city)) {
                                                    spinnerPickupCity.setSelection(k);
                                                }
                                            }
                                        } else {

                                        }
                                    }*/

                                }


                            }

                        } else {
                            stateDropArrayList.clear();
                            spnr_stateDrop.setTitle("Select Destination State");
                            spinnerStateDrop_Adapter = new ArrayAdapter<State>(_act, android.R.layout.simple_spinner_item, stateDropArrayList);
                            spinnerStateDrop_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnr_stateDrop.setAdapter(spinnerStateDrop_Adapter);
                            // Helper_Method.toaster(_act, stringMsg);
                            // scheduleDismiss();
                            //  Helper_Method.dismissProgessBar();


                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();

                } finally {
                    Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void initTest() {
        camera = new Camera(AddEarthMoverRequestActivity.this);
        tv_select_vehicle_rc_image = findViewById(R.id.tv_select_vehicle_rc_image);
        iv_vehicle_rc_image = findViewById(R.id.iv_vehicle_rc_image);

        rv_vehicle_rc_image = (RecyclerView) findViewById(R.id.rv_vehicle_rc_image);
        rv_vehicle_rc_image.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterImagesRC = new AdapterImages(AddEarthMoverRequestActivity.this, RC_IMAGE, MyConfig.IMG_URL_CAR, RC_IMAGE_DATA, true);
        rv_vehicle_rc_image.setAdapter(adapterImagesRC);

        recyclerViewVehicleType = findViewById(R.id.recyclerViewVehicleType);
        spinnerDestinationCity = findViewById(R.id.spnr_dest);
        et_Destinationlatlong = findViewById(R.id.et_Destinationlatlong);
        et_Expectedprice = findViewById(R.id.et_Expectedprice);
        et_hours = findViewById(R.id.et_hours);
        et_NoofDays = findViewById(R.id.et_NoofDays);
        et_TotalAmount = findViewById(R.id.et_TotalAmount);
        et_remark = findViewById(R.id.et_remark);
        chk_isnegotiaable = findViewById(R.id.chk_isnegotiaable);
        rb_hourly = findViewById(R.id.rb_hourly);
        rb_daily = findViewById(R.id.rb_daily);
        rlHour = findViewById(R.id.rlHour);
        ll_hourly = findViewById(R.id.ll_hourly);
        button_submit = findViewById(R.id.button_submit);
        et_Requireddate = findViewById(R.id.et_Requireddate);
        et_RequiredTime = findViewById(R.id.et_RequiredTime);
        spnr_stateDrop = findViewById(R.id.spnr_state);

        et_RequiredTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddEarthMoverRequestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et_RequiredTime.setText( selectedHour + ":" + selectedMinute);
                        requiredTime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!vehicleType.equals("") ) {
                    if (validateFields()) {
                        if(!rb_daily.isChecked()) {
                            addEarthMoverRequest();
                        }else {
                            if(MyValidator.isValidField(et_NoofDays)){
                                addEarthMoverRequest();
                            }
                        }
                    } else {
                        Constants.AlertDailogue("Please update all data", AddEarthMoverRequestActivity.this);
                    }
                }else {
                    Constants.AlertDailogue("Please select vehicle type ", AddEarthMoverRequestActivity.this);
                }
            }
        });

        chk_isnegotiaable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isNegotiable = "2";
                } else {
                    isNegotiable = "1";
                }
            }
        });

        rb_hourly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_hourly.setVisibility(View.GONE);
                    isHourly = "1";
                    et_TotalAmount.setText("");
                }
            }
        });

        rb_daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_hourly.setVisibility(View.VISIBLE);
                    isHourly = "2";
                    et_TotalAmount.setText("");
                }
            }
        });

        et_Destinationlatlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(AddEarthMoverRequestActivity.this)
                        .withPermissions(
                                Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    //camera.selectImage(iv_image, 0);
                                    openActivityForResult();
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();


            }
        });

        launchDropActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 123) {
                            Intent data = result.getData();
                            Log.d("valueVIJ1", "onActivityResult: " + data.getStringExtra("lat"));
                            Log.d("valueVIJ1", "onActivityResult: " + data.getStringExtra("lagn"));
                            droplat = data.getStringExtra("lat");
                            droplong = data.getStringExtra("lagn");
                            et_Destinationlatlong.setText("Latitude : " + droplat + ", Longitude : " + droplong);
                        }
                    }
                });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        et_Requireddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* new DatePickerDialog(AddEarthMoverRequestActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(AddEarthMoverRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, (month));
                        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                        String myFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        et_Requireddate.setText(sdf.format(calendar.getTime()));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide previous date,month and year
                calendar.add(Calendar.YEAR, 0);
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide future date,month and year
                dialog.show();
            }
        });


        recyclerViewVehicleType.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        for (int i = 0; i < vehicleTypeArrayList.size(); i++) {

                            if (i == position) {

                                //Remove logic
                                if (vehicleTypeArrayList.get(i).isSelected()) {
                                    //vehicleTypeObjectArrayList.get(i).setSelected(false);
                                    // strSubCategoryId = "";
                                } else {
                                    //vehicleTypeObjectArrayList.get(i).setSelected(true);
                                    //strSubCategoryId = vehicleTypeObjectArrayList.get(i).getTab_id();

                                }

                                vehicleTypeArrayList.get(i).setSelected(true);
                            } else {
                                vehicleTypeArrayList.get(i).setSelected(false);
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                        vehicleType = vehicleTypeArrayList.get(position).getType();
                        Log.d("vehicleType", "onItemClick: vehicleType Id" + vehicleType);


                    }
                })
        );


        et_hours.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // Toast.makeText(_act, "a", Toast.LENGTH_SHORT).show();
                if(!et_Expectedprice.getText().toString().isEmpty() && !et_hours.getText().toString().isEmpty() ){
                    if(rb_hourly.isChecked()) {
                        int totalAmount = ((Integer.parseInt(et_Expectedprice.getText().toString())) *
                                (Integer.parseInt(et_hours.getText().toString())));
                        et_TotalAmount.setText(""+totalAmount);
                    }else if(!et_Expectedprice.getText().toString().isEmpty() && !et_NoofDays.getText().toString().isEmpty()){
                        int totalAmount = ((Integer.parseInt(et_Expectedprice.getText().toString())) *
                                (Integer.parseInt(et_hours.getText().toString())) *
                                (Integer.parseInt(et_NoofDays.getText().toString())));
                        et_TotalAmount.setText(""+totalAmount);
                    }
                }else {
                    int totalAmount = 0;
                    et_TotalAmount.setText(""+totalAmount);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_NoofDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(_act, "a", Toast.LENGTH_SHORT).show();
                if(!et_Expectedprice.getText().toString().isEmpty() && !et_hours.getText().toString().isEmpty()
                        && !et_NoofDays.getText().toString().isEmpty() ){
                    if(rb_hourly.isChecked()) {
                        int totalAmount = ((Integer.parseInt(et_Expectedprice.getText().toString())) *
                                (Integer.parseInt(et_hours.getText().toString())));
                        et_TotalAmount.setText(""+totalAmount);
                    }else if(!et_Expectedprice.getText().toString().isEmpty() && !et_NoofDays.getText().toString().isEmpty()
                            && !et_hours.getText().toString().isEmpty()){
                        int totalAmount = ((Integer.parseInt(et_Expectedprice.getText().toString())) *
                                (Integer.parseInt(et_hours.getText().toString())) *
                                (Integer.parseInt(et_NoofDays.getText().toString())));
                        et_TotalAmount.setText(""+totalAmount);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tv_select_vehicle_rc_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AddEarthMoverRequestActivity.this)
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                        )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    //camera.selectImage(iv_image, 0);
                                    isUpdate = false;
                                    image_type = RC_IMAGE;
                                    camera.selectImage(iv_vehicle_rc_image, 0);
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();
            }
        });

        getVehicleTypeList();


    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddEarthMoverRequestActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        et_Requireddate.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void openActivityForResult() {
        Intent intent = new Intent(this, ActivityGetLocation.class);
        launchDropActivity.launch(intent);
    }

    private void getVehicleTypeList() {

        Helper_Method.showProgressBar(_act, "Loading Vehicle Type...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTVehicleTypeList(
                Shared_Preferences.getPrefs(AddEarthMoverRequestActivity.this, IConstant.USER_ID),
                Shared_Preferences.getPrefs(AddEarthMoverRequestActivity.this, IConstant.USER_API_TOKEN),
                "2"
        );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    vehicleTypeArrayList = new ArrayList<>();
                    vehicleTypeArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {

                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        String path = i.getString("vehicle_type_path");
                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("vechicle_model_type_list");
                            //pickupcityObjectArrayList.add(new CountryNameObject("0", "Select Country ", "Date"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {

                                    vehicleTypeArrayList.add(new VehicleType(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    // scheduleDismiss();

                                }
                            }

                            //Helper_Method.dismissProgessBar();
                            mAdapter = new VehicleListActivityAdapter(vehicleTypeArrayList, path);
                            recyclerViewVehicleType.setLayoutManager(new LinearLayoutManager(AddEarthMoverRequestActivity.this,
                                    LinearLayoutManager.HORIZONTAL, false));
                            recyclerViewVehicleType.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewVehicleType.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            Helper_Method.dismissProgessBar();
                            Log.d("VijendraTest", "onResponse: ");
                            webcallPickStateList();

                        }


                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();

                } finally {
                    Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void webcallPickCityList(String strStateId) {

        Helper_Method.showProgressBar(_act, "Loading City list...");

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTCity(strStateId);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {

                    destinationupcityObjectArrayList = new ArrayList<>();
                    destinationupcityObjectArrayList.clear();

                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase("true")) {

                            destinationupcityObjectArrayList.add(new CityObject("0", "Select Destination City", "0"));
                            JSONArray jsonArray = i.getJSONArray("city_list");
                            //pickupcityObjectArrayList.add(new CountryNameObject("0", "Select Country ", "Date"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {

                                    destinationupcityObjectArrayList.add(new CityObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    // scheduleDismiss();

                                }
                            }

                            if (destinationupcityObjectArrayList.size() == 0) {



                            } else {

                                // scheduleDismiss();
                                // Toast.makeText(_act, "In Set adapter", Toast.LENGTH_SHORT).show();
                                spinnerDestinationCity.setTitle("Select Destination City");
                                spinnerDestination_Adapter = new ArrayAdapter<CityObject>(_act,
                                        android.R.layout.simple_spinner_item, destinationupcityObjectArrayList);
                                spinnerDestination_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerDestinationCity.setAdapter(spinnerDestination_Adapter);
                                spinnerDestinationCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strDestinationId = destinationupcityObjectArrayList.get(i).id;
                                        strDestinationName = destinationupcityObjectArrayList.get(i).city_name;


                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


                            }

                            Helper_Method.dismissProgessBar();

                        } else {

                            Helper_Method.dismissProgessBar();
                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();

                } finally {
                    Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void addEarthMoverRequest() {

        Helper_Method.showProgressBar(AddEarthMoverRequestActivity.this, "Loading...");

        List<MultipartBody.Part> rcPartList = new ArrayList<>();

        int j = 0;
        for (int i = 0; i < RC_IMAGE_DATA.size(); i++) {

            if (RC_IMAGE_DATA.get(i).getImage_path_multipart() != null) {
                rcPartList.add(prepareFilePart("images[" + j + "]", RC_IMAGE_DATA.get(i).getImg_path()));
                j++;
            }
        }
        Log.d("my_tag", "uploadData: " + rcPartList.size());

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTaddEarthMoverRequest(
                Shared_Preferences.getPrefs(AddEarthMoverRequestActivity.this, IConstant.USER_ID),
                Shared_Preferences.getPrefs(AddEarthMoverRequestActivity.this, IConstant.USER_API_TOKEN),
                droplat,
                droplong,
                strDestinationId,
                vehicleType,
                isNegotiable,
                et_Expectedprice.getText().toString(),
                isHourly,
                et_NoofDays.getText().toString(),
                et_hours.getText().toString(),
                et_remark.getText().toString(),
                et_Requireddate.getText().toString(),
                requiredTime

        );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    vehicleTypeArrayList = new ArrayList<>();
                    vehicleTypeArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        Helper_Method.dismissProgessBar();
                        if (stringCode.equalsIgnoreCase("true")) {
                            //Toast.makeText(AddEarthMoverRequestActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
                            Constants.AlertDailogue(stringMsg, AddEarthMoverRequestActivity.this);
                            Intent intent = new Intent(AddEarthMoverRequestActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Constants.AlertDailogue(stringMsg, AddEarthMoverRequestActivity.this);
                            //Toast.makeText(AddEarthMoverRequestActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                        //Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();
                    // Helper_Method.dismissProgessBar();

                } finally {
                    Helper_Method.dismissProgessBar();
                    // Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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