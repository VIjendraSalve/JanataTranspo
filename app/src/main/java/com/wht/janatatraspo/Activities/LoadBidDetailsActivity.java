package com.wht.janatatraspo.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.DateTimeFormat;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.MainActivity;
import com.wht.janatatraspo.Model.Bid;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadBidDetailsActivity extends BaseActivity implements PaymentResultListener {

    public int remaining = 0;
    public String strBidId = "";
    TextView tvRoute, tvPost, tvWorkType, tvVehicleType;
    RelativeLayout rlTopBackground;
    Double commissionAmount = 0.0;
    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView toolbar_title;
    private TextView tvVerified, tvKYCUpdate, tvPaymentRemain;
    private TextView tvEstimateWeight, tvNegotiable, tvExpectedPrice, tvEstimatePrice, tvMaterial, tvWorkingDay;
    private ImageView ivPostImg2;
    private TextView tvOnGoing, tvWon;
    private TextView tv_allownces, tv_waitingcharge, tv_labourcharge, tvMyBidAmount;
    private ExtendedFloatingActionButton efaPayAdavance, efaEndRide;
    private TextView tvStatusTitle;
    private TextView tvTotalBidAmount;
    private TextView tvTotalAmountPay;
    private TextView tvRemaingAmount;
    private TextView tvCompanyFees;
    //Customer Details
    private ImageView ivDriverProfile, ivCall;
    private RelativeLayout rlProfile;
    private TextView tvDriverName;
    private ArrayList<Bid> bidListObjectArrayList;
    private String user_profile_path = null;
    private String vehicle_path = null;
    private String loader_path = null;
    private int page_count = 1;
    private int last_position = 0;
    private ArrayList<Bid> bidArrayList = new ArrayList<>();
    private int position = 0;
    private String commisionAmount = "";
    private TextView tv_load_type_isfix_or_perton, tv_no_of_ton, tv_per_ton_price, tv_final_price, tv_commision_amount;
    private String commission = "";
    private LinearLayout ll_ton, ll_pricePerTon;
    //Get Current Location
    // private GPS_TRACKER gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_bid_details);

        Checkout.preload(getApplicationContext());
        //strBidId = getIntent().getStringExtra("bid_id");

        _act = LoadBidDetailsActivity.this;
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.removePhoneKeypad(_act);
        validations = new Validations();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Advance Payment");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bidArrayList = getIntent().getParcelableArrayListExtra(IConstant.TranspoBidList);
        position = getIntent().getIntExtra(IConstant.Position, 0);
        commission = getIntent().getStringExtra(IConstant.Commission);

        Log.d("PaymentAgainstBid", "onCreate Size: " + bidArrayList.get(position).getVolumetric_weight());
        Log.d("PaymentAgainstBid", "onCreate position: " + position);
        Log.d("PaymentAgainstBid", "onCreate Commission: " + commission);


        rlTopBackground = findViewById(R.id.rlTopBackground);
        efaPayAdavance = findViewById(R.id.efaPayAdavance);
        efaEndRide = findViewById(R.id.efaEndRide);
        tvStatusTitle = findViewById(R.id.tvStatusTitle);

        //bidListObjectArrayList = new ArrayList<>();

        tvRoute = findViewById(R.id.tvRoute);
        tvPost = findViewById(R.id.tvPost);
        tvWorkType = findViewById(R.id.tvWorkType);
        tvVehicleType = findViewById(R.id.tvVehicleType);
        tvVerified = findViewById(R.id.tvVerified);
        tvKYCUpdate = findViewById(R.id.tvKYCUpdate);

        tvEstimateWeight = findViewById(R.id.tvEstimateWeight);
        tvExpectedPrice = findViewById(R.id.tvExpectedPrice);
        tvEstimatePrice = findViewById(R.id.tvEstimatePrice);
        tvMaterial = findViewById(R.id.tvMaterial);
        tvWorkingDay = findViewById(R.id.tvWorkingDay);
        ivPostImg2 = findViewById(R.id.ivPostImg2);

        tvOnGoing = findViewById(R.id.tvOnGoing);
        tvWon = findViewById(R.id.tvWon);

        tv_allownces = findViewById(R.id.tv_allownces);
        tv_waitingcharge = findViewById(R.id.tv_waitingcharge);
        tv_labourcharge = findViewById(R.id.tv_labourcharge);
        tvMyBidAmount = findViewById(R.id.tvMyBidAmount);

        ivDriverProfile = findViewById(R.id.ivDriverProfile);
        rlProfile = findViewById(R.id.rlProfile);
        tvDriverName = findViewById(R.id.tvDriverName);
        ivCall = findViewById(R.id.ivCall);
        ll_ton = findViewById(R.id.ll_ton);
        ll_pricePerTon = findViewById(R.id.ll_pricePerTon);


        tv_load_type_isfix_or_perton = findViewById(R.id.tv_load_type_isfix_or_perton);
        tv_no_of_ton = findViewById(R.id.tv_no_of_ton);
        tv_per_ton_price = findViewById(R.id.tv_per_ton_price);
        tv_final_price = findViewById(R.id.tv_final_price);
        tv_commision_amount = findViewById(R.id.tv_commision_amount);

        efaPayAdavance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

        LoadDetails();

    }


    public void LoadDetails() {

        //tvRoute.setText(Helper_Method.toTitleCase(bidArrayList.get(position).pickup_loaction_name + " - " + bidArrayList.get(position).drop_loaction_name));
        tvEstimateWeight.setText(bidArrayList.get(position).getVolumetric_weight() + " Tons Weight");
        tvVehicleType.setText("Required " + bidArrayList.get(position).getTransport_type());
        tvPost.setText("Posted on " + DateTimeFormat.getDate3To4(bidArrayList.get(position).getCreated_at()));
        //tvWorkingDay.setText("Required on " + DateTimeFormat.getDate3To10(bidArrayList.get(position).getRequired_date()));
        tvMaterial.setText("Material to carry " + bidArrayList.get(position).getMaterial());
        Log.d("Values", "LoadDetails: " + bidArrayList.get(position).getExpected_price());

        tvExpectedPrice.setText(Helper_Method.getIndianRupee(bidArrayList.get(position).getExpected_price()));
        tv_allownces.setText(Helper_Method.getIndianRupee(bidArrayList.get(position).getAllownces()));
        tv_waitingcharge.setText(Helper_Method.getIndianRupee(bidArrayList.get(position).getWaiting_chagres()));
        tv_labourcharge.setText(Helper_Method.getIndianRupee(bidArrayList.get(position).getLabour_charge_per_person()));
        tvMyBidAmount.setText("Final Bid Amount : " + Helper_Method.getIndianRupee(bidArrayList.get(position).getBid_amount()));


        Log.d("Amount", "commissionAmount: " + commissionAmount);
        Log.d("Amount", "getFixed_per_tone: " + bidArrayList.get(position).getFixed_per_tone());
        Log.d("Amount", "getBid_amount: " + bidArrayList.get(position).getBid_amount());


        if (bidArrayList.get(position).getFixed_per_tone().equals("1")) {
            tv_load_type_isfix_or_perton.setText("Fixed");
            Double finalAmount = Double.parseDouble(bidArrayList.get(position).getBid_amount());
            tv_final_price.setText("" + Helper_Method.getIndianRupee(String.valueOf(finalAmount)));
            commissionAmount = finalAmount * (Double.parseDouble(commission) / 100);
            Math.round(commissionAmount);
            tv_commision_amount.setText("" + Helper_Method.getIndianRupee(String.valueOf(commissionAmount)));
            tv_no_of_ton.setVisibility(View.GONE);
            tv_per_ton_price.setVisibility(View.GONE);

            ll_ton.setVisibility(View.GONE);
            ll_pricePerTon.setVisibility(View.GONE);
        } else {
            Double finalAmount = ((Double.parseDouble(bidArrayList.get(position).getBid_amount())) *
                    Double.parseDouble(bidArrayList.get(position).getNumber_of_ton()));
            tv_load_type_isfix_or_perton.setText("Per Ton");
            tv_final_price.setText("" + Helper_Method.getIndianRupee(String.valueOf(finalAmount)));
            commissionAmount = finalAmount * (Double.parseDouble(commission) / 100);
            Math.round(commissionAmount);
            tv_commision_amount.setText("" + Helper_Method.getIndianRupee(String.valueOf(commissionAmount)));
            tv_no_of_ton.setVisibility(View.VISIBLE);
            tv_per_ton_price.setVisibility(View.VISIBLE);

            ll_ton.setVisibility(View.VISIBLE);
            ll_pricePerTon.setVisibility(View.VISIBLE);
        }

        tv_no_of_ton.setText(bidArrayList.get(position).getNumber_of_ton());
        tv_per_ton_price.setText(Helper_Method.getIndianRupee(bidArrayList.get(position).getBid_amount()));


        if (bidArrayList.get(position).getLoader_type().equalsIgnoreCase("1")) {
            tvWorkType.setText("Part Load");
            //ivPostImg2.setImageResource(R.drawable.ic_part_load);
        } else {
            tvWorkType.setText("Full Load");
            //ivPostImg2.setImageResource(R.drawable.ic_load);
        }

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDialogueBox(bidArrayList.get(position).getMobile_no());
            }
        });

        tvOnGoing.setVisibility(View.GONE);

        if (bidArrayList.get(position).getIs_accept().equals("0")) {
            tvWon.setText("Pending");
            tvWon.setBackgroundColor(this.getResources().getColor(R.color.red));
            ivCall.setVisibility(View.GONE);
            efaPayAdavance.setVisibility(View.VISIBLE);
            tvStatusTitle.setText("Pending");


        } else {
            tvWon.setBackgroundColor(this.getResources().getColor(R.color.green));
            tvWon.setText("Completed");
            ivCall.setVisibility(View.VISIBLE);
            efaPayAdavance.setVisibility(View.GONE);
            tvStatusTitle.setText("Completed");
            ll_ton.setVisibility(View.GONE);
            ll_pricePerTon.setVisibility(View.GONE);


        }

        Glide.with(_act)
                .load(bidArrayList.get(position).getImage())
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivDriverProfile);

        tvDriverName.setText(Html.fromHtml(bidArrayList.get(position).getFirst_name() + " " + bidArrayList.get(position).getLast_name()));
        rlTopBackground.setBackgroundColor(getResources().getColor(R.color.green_variant));



    }

    private void callDialogueBox(final String mobileNo) {
        final Dialog dialog = new Dialog(LoadBidDetailsActivity.this);
        dialog.setContentView(R.layout.dailog_contact_view);
        dialog.setCancelable(true);
        final TextView tv_mobile_number = (TextView) dialog.findViewById(R.id.tv_mobile_number);
        TextView tv_header = (TextView) dialog.findViewById(R.id.tv_header);
        tv_header.setTypeface(tv_header.getTypeface(), Typeface.BOLD);

        tv_mobile_number.setText("" + mobileNo);

        Button btn_ok = (Button) dialog.findViewById(R.id.btnSubmit);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        tv_mobile_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(LoadBidDetailsActivity.this)
                        .withPermissions(Manifest.permission.CALL_PHONE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
                                    startActivity(intent);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getAttributes().windowAnimations = R.style.MaterialDialog; //style id
        dialog.show();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoadBidDetailsActivity.this);
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
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    //Roozer Pay
    public void startPayment() {

        // You need to pass current activity in order to let Razorpay create CheckoutActivity

        final Activity activity = this;

        final Checkout co = new Checkout();
        //co.setKeyID("ZC2cY7j3M6rP96XNrdGprwfs");
        // co.setImage(R.drawable.final_logo);
        try {

            Double amount = 0.0;
            amount = Double.parseDouble(String.valueOf(commissionAmount)) * 100;

            JSONObject options = new JSONObject();
            options.put("name", Shared_Preferences.getPrefs(_act, IConstant.USER_FIRST_NAME) + " " + Shared_Preferences.getPrefs(_act, IConstant.USER_LAST_NAME));
            options.put("description", "Janta Transpo ");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            //options.put("amount", String.valueOf(amount)); // on deployment multiply by 100
            options.put("amount", String.valueOf("100")); // on deployment multiply by 100
            JSONObject preFill = new JSONObject();
            preFill.put("email", Shared_Preferences.getPrefs(_act, IConstant.USER_EMAIL));
            preFill.put("contact", Shared_Preferences.getPrefs(_act, IConstant.USER_MOBILE));
            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(final String razorpayPaymentID) {
        try {

            bidTranscation(razorpayPaymentID);
            //AcceptLoad();
           /* final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_act);
            alertDialogBuilder.setTitle("Message");
            alertDialogBuilder.setMessage("Payment Successful: " + razorpayPaymentID);
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            finish();
                            // webcallOrderPlacing("1", "2", razorpayPaymentID, razorpayPaymentID, strCurrentDateToSet, strSlotId, strDeliveryType, strOfferId);
                           // webcallOrderPlacing("0", razorpayPaymentID, "payNow");
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            //alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            alert.show();*/
        } catch (Exception e) {
            Log.e("Payment", "Exception in onPaymentSuccess", e);
        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            // Constants.AlertDailogue("Payment failed: " + code + " \n" + response, BillActivity.this);
        } catch (Exception e) {
            Log.e("payment_gateway", "Exception in onPaymentError", e);
        }
    }


    private void AcceptLoad() {

        Helper_Method.showProgressBar(LoadBidDetailsActivity.this, "Accepting...");

        Log.d("Input", "load_id: " + bidArrayList.get(position).getLoader_id());
        Log.d("Input", "Bid_id: " + bidArrayList.get(position).getBid_id());
        Log.d("Input", "USER_ID: " + Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_ID));
        Log.d("Input", "USER_API_TOKEN: " + Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_API_TOKEN));

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTAcceptLoadBid(
                bidArrayList.get(position).getLoader_id(),
                bidArrayList.get(position).getBid_id(),
                Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_ID),
                Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_API_TOKEN)
        );

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

                            Helper_Method.hideSoftInput(LoadBidDetailsActivity.this);
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(LoadBidDetailsActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
                            Helper_Method.dismissProgessBar();

                            Intent intent = new Intent(LoadBidDetailsActivity.this, MainActivity.class);
                            LoadBidDetailsActivity.this.startActivity(intent);
                            finish();


                        } else {
                            Helper_Method.hideSoftInput(LoadBidDetailsActivity.this);
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(LoadBidDetailsActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
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

    private void bidTranscation(String razorPayID) {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        Helper_Method.showProgressBar(LoadBidDetailsActivity.this, "Accepting...");

        Log.d("Input", "load_id: " + bidArrayList.get(position).getLoader_id());
        Log.d("Input", "Bid_id: " + bidArrayList.get(position).getBid_id());
        Log.d("Input", "USER_ID: " + Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_ID));
        Log.d("Input", "USER_API_TOKEN: " + Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_API_TOKEN));

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTAcceptPayment(
                razorPayID,
                formattedDate,
                commisionAmount,
                bidArrayList.get(position).getLoader_id(),
                "1",
                bidArrayList.get(position).getBid_id(),
                Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_ID),
                Shared_Preferences.getPrefs(LoadBidDetailsActivity.this, IConstant.USER_API_TOKEN)
        );

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

                            Helper_Method.hideSoftInput(LoadBidDetailsActivity.this);
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(LoadBidDetailsActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
                            Helper_Method.dismissProgessBar();

                            AcceptLoad();


                        } else {
                            Helper_Method.hideSoftInput(LoadBidDetailsActivity.this);
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(LoadBidDetailsActivity.this, "" + stringMsg, Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}