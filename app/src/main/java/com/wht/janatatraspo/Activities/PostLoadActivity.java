package com.wht.janatatraspo.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wht.janatatraspo.Adapter.AdapterImages;
import com.wht.janatatraspo.Adapter.MultiimageSelectorAdapter;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.wht.janatatraspo.Constant.IConstant.RC_IMAGE;
import static com.wht.janatatraspo.my_library.MyConfig.prepareFilePart;

public class PostLoadActivity extends BaseActivity implements Camera.AsyncResponse {

    private static final int REQUEST_OWNER = 141;
    public static int image_type = 0;
    final Calendar myCalendar = Calendar.getInstance();
    private final int REQUEST_CODE_MEDIA_SELECT = 200;
    ActivityResultLauncher<Intent> launchSomeActivity = null, launchDropActivity = null;
    private Activity _act;
    private TextView tv_calculate;
    private EditText et_length, et_breadth, et_Height, et_VolumetricWeight, et_Pickuplatlong, et_Destinationlatlong;
    private EditText et_Material, et_Tonn, et_Expectedprice, et_Expectedweight, et_Remark, et_Nooflabour, et_Requireddate, et_RequiredTime;
    private AppCompatButton button_submit, btn_add_images;
    private ImageView iv_image;
    private Double weight_final;
    private CheckBox chk_islablour_required, chk_isnegotiaable;
    private String isLabourRequired = "0", isNegotiable = "0", requiredTime = "00:00";
    private RelativeLayout rlLabour;
    private Spinner spnr_loadtype;
    private ArrayList<String> loadList = new ArrayList<>();
    private ArrayAdapter<String> spinnerLoadTypeAdapter;
    private String strloadType = "0", strLoadName = "Full Load";
    //vehicle type
    private ArrayList<VehicleType> vehicleTypeArrayList;
    private RecyclerView recyclerViewVehicleType;
    private VehicleListActivityAdapter mAdapter;
    private String vehicleType = "";
    //City Spinner Zone pickup
    private ArrayList<CityObject> pickupcityObjectArrayList;
    private SearchableSpinner spinnerPickupCity;
    private ArrayAdapter<CityObject> spinnerPickupCity_Adapter;
    private String strPickupCityId = "0", strPickuCityName;
    private String loaderID = "";

    //PickupCity Spinner Zone destination
    private ArrayList<State> statePickupArrayList;
    private SearchableSpinner spnr_statePickup;
    private ArrayAdapter<State> spinnerStatePick_Adapter;
    private String strStatePickupId = "0", strStatePickName;

    //DropCity Spinner Zone destination
    private ArrayList<State> stateDropArrayList;
    private SearchableSpinner spnr_stateDrop;
    private ArrayAdapter<State> spinnerStateDrop_Adapter;
    private String strStateDropupId = "0", strStateDropName;

    //City Spinner Zone destination
    private ArrayList<CityObject> destinationupcityObjectArrayList;
    private SearchableSpinner spinnerDestinationCity;
    private ArrayAdapter<CityObject> spinnerDestination_Adapter;
    private String strDestinationId = "0", strDestinationName;

    // load type
    private RadioButton rb_fullLoad, rb_partload;
    private String loadType = "2"; // loader_type 1:part loader 2:full load
    private RadioButton rb_fixedprice, rb_perton;
    private String isFixed = "1";  //fixed_per_tone:1:fixed 2:per tone
    private RadioButton rb_topay, rb_to_billed;
    private String isToPay = "2"; // payment_mode:   1:to be billed 2:to pay
    //pick up n drop loaction
    private String pickuplat = "", pickuplong = "", pickupAddress= "";
    private String droplat = "", droplong = "", dropAddress="";
    //multiple images
    private RecyclerView recyclerViewImages;
    private GridLayoutManager gridLayoutManager;
    private MultiimageSelectorAdapter mImagesAdapter;
    private ArrayList<String> arrayMediaPath = new ArrayList<>();
    private TextView tv_location, tv_load, tv_weight, tv_price;
    private Camera camera;
    private ProgressDialog progressDialog;
    private String profile_image_name = "", profile_image_path = "";

    /*  @BindView(R.id.tv_select_vehicle_rc_image)
      TextView tv_select_vehicle_rc_image;

      @BindView(R.id.iv_vehicle_rc_image)
      ImageView iv_vehicle_rc_image;*/
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
        setContentView(R.layout.activity_post_load);

        _act = PostLoadActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Post Load");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


       /* tv_select_vehicle_rc_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUpdate = false;
                image_type = RC_IMAGE;
                camera.selectImage(iv_vehicle_rc_image, 0);
            }
        });*/

    }

   /* @OnClick(R.id.tv_select_vehicle_rc_image)
    public void getRCImage() {
        isUpdate = false;
        image_type = RC_IMAGE;
        camera.selectImage(iv_vehicle_rc_image, 0);
    }*/

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

    private void init() {

        tv_select_vehicle_rc_image = findViewById(R.id.tv_select_vehicle_rc_image);
        iv_vehicle_rc_image = findViewById(R.id.iv_vehicle_rc_image);

        rv_vehicle_rc_image = (RecyclerView) findViewById(R.id.rv_vehicle_rc_image);
        rv_vehicle_rc_image.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterImagesRC = new AdapterImages(PostLoadActivity.this, RC_IMAGE, MyConfig.IMG_URL_CAR, RC_IMAGE_DATA, true);
        rv_vehicle_rc_image.setAdapter(adapterImagesRC);

        camera = new Camera(PostLoadActivity.this);
        et_length = findViewById(R.id.et_length);
        et_breadth = findViewById(R.id.et_breadth);
        et_Height = findViewById(R.id.et_Height);
        et_VolumetricWeight = findViewById(R.id.et_VolumetricWeight);
        et_Pickuplatlong = findViewById(R.id.et_Pickuplatlong);
        et_Destinationlatlong = findViewById(R.id.et_Destinationlatlong);
        tv_calculate = findViewById(R.id.tv_calculate);
        chk_islablour_required = findViewById(R.id.chk_islablour_required);
        chk_isnegotiaable = findViewById(R.id.chk_isnegotiaable);
        rlLabour = findViewById(R.id.rlLabour);
        et_Material = findViewById(R.id.et_Material);
        et_Tonn = findViewById(R.id.et_Tonn);
        et_Expectedprice = findViewById(R.id.et_Expectedprice);
        et_Expectedweight = findViewById(R.id.et_Expectedweight);
        et_Remark = findViewById(R.id.et_Remark);
        et_Nooflabour = findViewById(R.id.et_Nooflabour);
        et_Requireddate = findViewById(R.id.et_Requireddate);
        et_RequiredTime = findViewById(R.id.et_RequiredTime);
        button_submit = findViewById(R.id.button_submit);
        btn_add_images = findViewById(R.id.btn_add_images);
        iv_image = findViewById(R.id.iv_image);

        spinnerPickupCity = findViewById(R.id.spnr_pickup);
        spinnerDestinationCity = findViewById(R.id.spnr_dest);
        spnr_loadtype = findViewById(R.id.spnr_loadtype);
        spnr_statePickup = findViewById(R.id.spnr_state);
        spnr_stateDrop = findViewById(R.id.spnr_Dropstate);



        rb_fullLoad = findViewById(R.id.rb_fullLoad);
        rb_partload = findViewById(R.id.rb_partload);
        rb_fixedprice = findViewById(R.id.rb_fixedprice);
        rb_perton = findViewById(R.id.rb_perton);
        rb_topay = findViewById(R.id.rb_topay);
        rb_to_billed = findViewById(R.id.rb_to_billed);
        recyclerViewVehicleType = findViewById(R.id.recyclerViewVehicleType);

        tv_location = findViewById(R.id.tv_location);
        tv_load = findViewById(R.id.tv_load);
        tv_weight = findViewById(R.id.tv_weight);
        tv_price = findViewById(R.id.tv_price);


        tv_select_vehicle_rc_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostLoadActivity.this)
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

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vehicleType.equals("")) {
                    if (validateFields()) {
                        if (!strDestinationId.equals("0") && !strPickupCityId.equals("0")) {
                            addPostLoad();
                        } else {
                            Constants.AlertDailogue("Please update pickup and drop loaction city", PostLoadActivity.this);
                        }
                    } else {
                        Constants.AlertDailogue("Please update all data", PostLoadActivity.this);
                    }
                } else {
                    Constants.AlertDailogue("Please select vehicle type ", PostLoadActivity.this);
                }

            }
        });

        rb_fullLoad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    loadType = "2";  // loader_type 1:part loader 2:full load
                } else {
                    loadType = "1";   // loader_type 1:part loader 2:full load
                }
            }
        });

        rb_partload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    loadType = "1";  // loader_type 1:part loader 2:full load
                } else {
                    loadType = "2";   // loader_type 1:part loader 2:full load
                }
            }
        });


        rb_fixedprice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isFixed = "1";  // fixed_per_tone:1:fixed 2:per tone
                } else {
                    isFixed = "2";   // fixed_per_tone:1:fixed 2:per tone
                }
            }
        });

        rb_perton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isFixed = "2";  // fixed_per_tone:  1:fixed 2:per tone
                } else {
                    isFixed = "1";   //  fixed_per_tone:  1:fixed 2:per tone
                }
            }
        });


        rb_topay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isToPay = "2";  // payment_mode:   1:to be billed 2:to pay
                } else {
                    isToPay = "1";   // payment_mode:   1:to be billed 2:to pay
                }
            }
        });

        rb_to_billed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isToPay = "1";  // payment_mode:   1:to be billed 2:to pay
                } else {
                    isToPay = "2";   //  payment_mode:   1:to be billed 2:to pay
                }
            }
        });


        loadList.add("Full Load");
        loadList.add("Part Load");
        spinnerLoadTypeAdapter = new ArrayAdapter<String>(_act, android.R.layout.simple_spinner_item, loadList);
        spinnerLoadTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_loadtype.setAdapter(spinnerLoadTypeAdapter);
        spnr_loadtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strLoadName = loadList.get(i);
                //Toast.makeText(PostLoadActivity.this, ""+strLoadName, Toast.LENGTH_SHORT).show();

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        chk_islablour_required.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    rlLabour.setVisibility(View.VISIBLE);
                    isLabourRequired = "1";
                } else {
                    rlLabour.setVisibility(View.GONE);
                    isLabourRequired = "0";
                }
            }
        });

        chk_isnegotiaable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isNegotiable = "1";
                } else {
                    isNegotiable = "0";
                }
            }
        });

        et_Pickuplatlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostLoadActivity.this)
                        .withPermissions(
                                Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    //camera.selectImage(iv_image, 0);
                                    openSomeActivityForResult();
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

        et_Destinationlatlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(PostLoadActivity.this)
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

        tv_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyValidator.isValidField(et_length) && MyValidator.isValidField(et_breadth) && MyValidator.isValidField(et_Height)) {
                    Double length = Double.parseDouble(et_length.getText().toString().trim());
                    Double breadth = Double.parseDouble(et_breadth.getText().toString().trim());
                    Double height = Double.parseDouble(et_Height.getText().toString().trim());

                    weight_final = (length * breadth * height) / 366;
                    if (length >= 39.37 || breadth >= 39.37 || height >= 39.37) {
                        weight_final = weight_final * 2;
                    }
                    et_VolumetricWeight.setText("" + String.format("%.2f", weight_final));

                } else {
                    Toast.makeText(PostLoadActivity.this, "Please Enter values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final long now = System.currentTimeMillis() - 1000;

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                view.setMinDate(now);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };


        et_RequiredTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PostLoadActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et_RequiredTime.setText(selectedHour + ":" + selectedMinute);
                        requiredTime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        et_Requireddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* new DatePickerDialog(PostLoadActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();*/

                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(PostLoadActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, (month));
                        calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                        String myFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        et_Requireddate.setText(sdf.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide previous date,month and year
                calendar.add(Calendar.YEAR, 0);
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide future date,month and year
                dialog.show();

            }
        });

        webcallPickStateList();
        //webcallDestinationCityList("");
        getVehicleTypeList();
        launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 123) {
                            Intent data = result.getData();
                            Log.d("valueVIJ", "onActivityResult: " + data.getStringExtra("lat"));
                            Log.d("valueVIJ", "onActivityResult: " + data.getStringExtra("lagn"));
                            pickuplat = data.getStringExtra("lat");
                            pickuplong = data.getStringExtra("lagn");
                            pickupAddress = data.getStringExtra("Address");
                            et_Pickuplatlong.setText(pickupAddress+"\nLatitude : " + pickuplat + ", Longitude : " + pickuplong);
                        }
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
                            dropAddress = data.getStringExtra("Address");
                            et_Destinationlatlong.setText(dropAddress+"\nLatitude : " + droplat + ", Longitude : " + droplong);
                        }
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

    }

    private boolean validateFields() {

        boolean result = true;

        if (!MyValidator.isValidField(et_length)) {
            result = false;
        }
        if (!MyValidator.isValidField(et_breadth)) {
            result = false;
        }
        if (!MyValidator.isValidField(et_Height)) {
            result = false;
        }
        if (!MyValidator.isValidField(et_VolumetricWeight)) {
            result = false;
        }

        if (!MyValidator.isValidField(et_Pickuplatlong)) {
            result = false;
        }
        if (!MyValidator.isValidField(et_Destinationlatlong)) {
            result = false;
        }
        if (!MyValidator.isValidField(et_Material)) {
            result = false;
        }
        if (!MyValidator.isValidField(et_Tonn)) {
            result = false;
        }

        if (!MyValidator.isValidField(et_Expectedprice)) {
            result = false;
        }

        if (!MyValidator.isValidField(et_Expectedweight)) {
            result = false;
        }

        if (!MyValidator.isValidField(et_Remark)) {
            result = false;
        }

        if (!MyValidator.isValidField(et_Requireddate)) {
            result = false;
        }

        if (!MyValidator.isValidSpinner(spinnerPickupCity)) {
            result = false;
        }

        if (!MyValidator.isValidSpinner(spinnerDestinationCity)) {
            result = false;
        }


        return result;
    }

    public void openSomeActivityForResult() {
        Intent intent = new Intent(this, ActivityGetLocation.class);
        launchSomeActivity.launch(intent);
    }

    public void openActivityForResult() {
        Intent intent = new Intent(this, ActivityGetLocation.class);
        launchDropActivity.launch(intent);
    }

    private RequestBody createRequestBodyFromText(String mText) {
        return RequestBody.create(MediaType.parse("text/plain"), mText);
    }

    private void addPostLoad() {
        Helper_Method.showProgressBar(PostLoadActivity.this, "Loading...");

        Log.d("InputValues", "USER_ID: " + Shared_Preferences.getPrefs(getApplicationContext(), IConstant.USER_ID));
        Log.d("InputValues", "USER_API_TOKEN: " + Shared_Preferences.getPrefs(getApplicationContext(), IConstant.USER_API_TOKEN));
        Log.d("InputValues", "loadType: " + loadType);
        Log.d("InputValues", "pickuplat: " + pickuplat);
        Log.d("InputValues", "pickuplong: " + pickuplong);
        Log.d("InputValues", "strPickupCityId: " + strPickupCityId);
        Log.d("InputValues", "strDestinationId: " + strDestinationId);
        Log.d("InputValues", "VehicleSelected: " + vehicleType);
        Log.d("InputValues", "droplat: " + droplat);
        Log.d("InputValues", "droplong: " + droplong);
        Log.d("InputValues", "et_Material: " + et_Material.getText().toString());
        Log.d("InputValues", "et_Tonn: " + et_Tonn.getText().toString());
        Log.d("InputValues", "et_Expectedprice: " + et_Expectedprice.getText().toString());
        Log.d("InputValues", "isFixed: " + isFixed);
        Log.d("InputValues", "isToPay: " + isToPay);
        Log.d("InputValues", "et_VolumetricWeight: " + et_VolumetricWeight.getText().toString());
        Log.d("InputValues", "et_length: " + et_length.getText().toString());
        Log.d("InputValues", "et_breadth: " + et_breadth.getText().toString());
        Log.d("InputValues", "et_Height: " + et_Height.getText().toString());
        Log.d("InputValues", "et_Expectedweight: " + et_Expectedweight.getText().toString());
        Log.d("InputValues", "et_Remark: " + et_Remark.getText().toString());
        Log.d("InputValues", "isLabourRequired: " + isLabourRequired);
        Log.d("InputValues", "et_Nooflabour: " + et_Nooflabour.getText().toString());
        Log.d("InputValues", "et_Requireddate: " + et_Requireddate.getText().toString());
        Log.d("InputValues", "loaderID: " + loaderID);
        Log.d("InputValues", "is_negotialable : " + isNegotiable);
        Log.d("InputValues", "requiredTime : " + requiredTime);

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
        Call<ResponseBody> result = api.POSTaddLoad(
                PostLoadActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(getApplicationContext(), IConstant.USER_ID)),
                PostLoadActivity.this.createRequestBodyFromText(Shared_Preferences.getPrefs(getApplicationContext(), IConstant.USER_API_TOKEN)),
                PostLoadActivity.this.createRequestBodyFromText(loadType),
                PostLoadActivity.this.createRequestBodyFromText(pickuplat),
                PostLoadActivity.this.createRequestBodyFromText(pickuplong),
                PostLoadActivity.this.createRequestBodyFromText(strPickupCityId),
                PostLoadActivity.this.createRequestBodyFromText(strDestinationId),
                PostLoadActivity.this.createRequestBodyFromText(droplat),
                PostLoadActivity.this.createRequestBodyFromText(droplong),
                PostLoadActivity.this.createRequestBodyFromText(vehicleType),
                PostLoadActivity.this.createRequestBodyFromText(et_Material.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_Tonn.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_Expectedprice.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(isFixed),
                PostLoadActivity.this.createRequestBodyFromText(isToPay),
                PostLoadActivity.this.createRequestBodyFromText(et_VolumetricWeight.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_length.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_breadth.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_Height.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_Expectedweight.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_Remark.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(isLabourRequired),
                PostLoadActivity.this.createRequestBodyFromText(et_Nooflabour.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(et_Requireddate.getText().toString()),
                PostLoadActivity.this.createRequestBodyFromText(loaderID),
                PostLoadActivity.this.createRequestBodyFromText(isNegotiable),
                PostLoadActivity.this.createRequestBodyFromText(requiredTime),
                rcPartList

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


                        if (stringCode.equalsIgnoreCase("true")) {
                            //Toast.makeText(PostLoadActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
                            Constants.AlertDailogue(stringMsg, PostLoadActivity.this);
                            Intent intent = new Intent(PostLoadActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(PostLoadActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        //scheduleDismiss();
                        //Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    // Helper_Method.dismissProgessBar();

                } finally {
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

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        et_Requireddate.setText(dateFormat.format(myCalendar.getTime()));
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
                    statePickupArrayList = new ArrayList<>();
                    statePickupArrayList.clear();

                    stateDropArrayList = new ArrayList<>();
                    stateDropArrayList.clear();

                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase("true")) {
                            statePickupArrayList.add(new State("0", "Select Pickup State", "0"));
                            stateDropArrayList.add(new State("0", "Select Destination State", "0"));
                            JSONArray jsonArray = i.getJSONArray("states_list");
                            //pickupcityObjectArrayList.add(new CountryNameObject("0", "Select Country ", "Date"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    statePickupArrayList.add(new State(jsonArray.getJSONObject(index)));
                                    stateDropArrayList.add(new State(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    // scheduleDismiss();

                                }
                            }

                            if (statePickupArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();


                            } else {

                                // scheduleDismiss();

                                spnr_statePickup.setTitle("Select State");
                                spinnerStatePick_Adapter = new ArrayAdapter<State>(_act, android.R.layout.simple_spinner_item, statePickupArrayList);
                                spinnerStatePick_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnr_statePickup.setAdapter(spinnerStatePick_Adapter);
                                spnr_statePickup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strStatePickupId = statePickupArrayList.get(i).id;
                                        strStatePickName = statePickupArrayList.get(i).name;
                                        if(i != 0)
                                            webcallPickCityList(strStatePickupId);


                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


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
                                        webcallDestinationCityList(strStateDropupId);

                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


                                if (statePickupArrayList.size() == 0) {

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
                            pickupcityObjectArrayList.clear();
                            spinnerPickupCity.setTitle("Select Pickup City");
                            spinnerPickupCity_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, pickupcityObjectArrayList);
                            spinnerPickupCity_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPickupCity.setAdapter(spinnerPickupCity_Adapter);
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

    private void webcallPickCityList(String strStateId) {

        Helper_Method.showProgressBar(_act, "Loading...");

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTCity(strStateId);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    pickupcityObjectArrayList = new ArrayList<>();
                    pickupcityObjectArrayList.clear();

                    destinationupcityObjectArrayList = new ArrayList<>();
                    destinationupcityObjectArrayList.clear();

                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase("true")) {
                            pickupcityObjectArrayList.add(new CityObject("0", "Select Pickup City", "0"));
                            //destinationupcityObjectArrayList.add(new CityObject("0", "Select Destination City", "0"));
                            JSONArray jsonArray = i.getJSONArray("city_list");
                            //pickupcityObjectArrayList.add(new CountryNameObject("0", "Select Country ", "Date"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    pickupcityObjectArrayList.add(new CityObject(jsonArray.getJSONObject(index)));
                                    //destinationupcityObjectArrayList.add(new CityObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    // scheduleDismiss();

                                }
                            }

                            if (pickupcityObjectArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();


                            } else {

                                // scheduleDismiss();

                                spinnerPickupCity.setTitle("Select City");
                                spinnerPickupCity_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, pickupcityObjectArrayList);
                                spinnerPickupCity_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerPickupCity.setAdapter(spinnerPickupCity_Adapter);
                                spinnerPickupCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strPickupCityId = pickupcityObjectArrayList.get(i).id;
                                        strPickuCityName = pickupcityObjectArrayList.get(i).city_name;


                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });



                                if (pickupcityObjectArrayList.size() == 0) {

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
                            pickupcityObjectArrayList.clear();
                            spinnerPickupCity.setTitle("Select Pickup City");
                            spinnerPickupCity_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, pickupcityObjectArrayList);
                            spinnerPickupCity_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPickupCity.setAdapter(spinnerPickupCity_Adapter);
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

    private void webcallDestinationCityList(String strStateId) {

        Helper_Method.showProgressBar(_act, "Loading...");

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

                                Helper_Method.dismissProgessBar();


                            } else {

                                // scheduleDismiss();

                                spinnerDestinationCity.setTitle("Select Destination City");
                                spinnerDestination_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, destinationupcityObjectArrayList);
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
                                if (destinationupcityObjectArrayList.size() == 0) {

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
                            destinationupcityObjectArrayList.clear();
                            spinnerDestinationCity.setTitle("Select District");
                            spinnerDestination_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, destinationupcityObjectArrayList);
                            spinnerDestination_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDestinationCity.setAdapter(spinnerDestination_Adapter);
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

    private void pickLocation() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(PostLoadActivity.this, ActivityGetLocation.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            //openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void destinationLocation() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(PostLoadActivity.this, ActivityGetLocation.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            //openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void getVehicleTypeList() {

        //  Helper_Method.showProgressBar(getContext(), "Loading...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTVehicleTypeList(
                Shared_Preferences.getPrefs(getApplicationContext(), IConstant.USER_ID),
                Shared_Preferences.getPrefs(getApplicationContext(), IConstant.USER_API_TOKEN),
                "1"
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

                            mAdapter = new VehicleListActivityAdapter(vehicleTypeArrayList, path);
                            recyclerViewVehicleType.setLayoutManager(new LinearLayoutManager(PostLoadActivity.this,
                                    LinearLayoutManager.HORIZONTAL, false));
                            recyclerViewVehicleType.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewVehicleType.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        }


                    } catch (JSONException e) {
                        //scheduleDismiss();
                        //Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    // Helper_Method.dismissProgessBar();

                } finally {
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

   /* @Override
    public void processFinish(String result, int img_no) {
        String[] parts = result.split("/");
        String imagename = parts[parts.length - 1];
        // Log.d("Image_path", result + " " + img_no);
        profile_image_name = imagename;
        profile_image_path = result;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PostLoadActivity.this);
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

    @Override
    protected void onResume() {
        super.onResume();
        // startActivity(new Intent(this,MainActivity.class));
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