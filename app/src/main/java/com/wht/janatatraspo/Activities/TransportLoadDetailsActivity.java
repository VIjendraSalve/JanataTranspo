package com.wht.janatatraspo.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.DateTimeFormat;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.Model.Load;
import com.wht.janatatraspo.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransportLoadDetailsActivity extends BaseActivity {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView toolbar_title;
   // private ExtendedFloatingActionButton efaBid;

    private Dialog dialog;
    private Button dailogtvOk;
    private ImageView dailogtvCancel;
    private TextView tvBidTotalAmount;
    private CheckBox cbNegotiable;
    private String strNegotiable = "0";
    private EditText etDriverBidAmount;
    private EditText etLaboutCharges;
    private EditText etWatingCharges;
    private EditText etAllowances;


    //Display Data
    private TextView tvRoute, tvPost, tvWorkType, tvTruckName, tvPickLocation, tvDropLocation,
            tvLength,
            tvBreadth, tvHeight,
            tvPaymentMode, tvRemark;
    private TextView tvVerified, tvKYCUpdate, tvPaymentRemain, tvBidRaised;
    private TextView tvEstimateWeight, tvNegotiable, tvExpectedPrice, tvEstimatePrice, tvMaterial, tvWorkingDay, tv_status;
    private ImageView ivPostImg2, ivTruckPic;

    private ArrayList<Load> transpoMarketListObjectArrayList;
    private String vehicle_path = null;
    private String loader_path = null;
    public int remaining = 0;
    private int page_count = 1;

    private String strLoad_id = "", strLoad_type = "";
    private int position = 0;

    private TextView tvLabourCharges,
            tvLabourReqCount,
            tvAllowances,
            tvWaitngCharges;

    //Media
   /* private String updateFlag = null;
    private ArrayList<LoadImagesObject> postAttachmentObjectArrayList;
    private LinearLayout llUploadedMedia;
    private RecyclerView rvUploadedMedia;
    private UploadedMediaAdatper uploadedMediaAdatper;
    private GridLayoutManager linearLayoutManager;*/

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_details_new);

        //strLoad_id = getIntent().getStringExtra("load_id");
        //strLoad_type = getIntent().getStringExtra("load_type");

        _act = TransportLoadDetailsActivity.this;
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.removePhoneKeypad(_act);
        validations = new Validations();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Load Details");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transpoMarketListObjectArrayList = new ArrayList<>();

  /*      tvLabourCharges = findViewById(R.id.tvLabourCharges);
        tvLabourReqCount = findViewById(R.id.tvLabourReqCount);
        tvAllowances = findViewById(R.id.tvAllowances);
        tvWaitngCharges = findViewById(R.id.tvWaitngCharges);*/

        //efaBid = findViewById(R.id.efaBid);
        tvRoute = findViewById(R.id.tvRoute);
        tvPost = findViewById(R.id.tvPost);
        tvWorkType = findViewById(R.id.tvWorkType);
        tvTruckName = findViewById(R.id.tvTruckName);
        tvPickLocation = findViewById(R.id.tvPickLocation);
        tvDropLocation = findViewById(R.id.tvDropLocation);
        tvLength = findViewById(R.id.tvLength);
        tvBreadth = findViewById(R.id.tvBreadth);
        tvHeight = findViewById(R.id.tvHeight);
        tvPaymentMode = findViewById(R.id.tvPaymentMode);

        tvRemark = findViewById(R.id.tvRemark);
        tvVerified = findViewById(R.id.tvVerified);
        tvKYCUpdate = findViewById(R.id.tvKYCUpdate);
        //tvPaymentRemain = findViewById(R.id.tvPaymentRemain);
        tvBidRaised = findViewById(R.id.tvBidRaised);
        tvEstimateWeight = findViewById(R.id.tvEstimateWeight);
        tvNegotiable = findViewById(R.id.tvNegotiable);
        tvExpectedPrice = findViewById(R.id.tvExpectedPrice);
        tvEstimatePrice = findViewById(R.id.tvEstimatePrice);
        tvMaterial = findViewById(R.id.tvMaterial);
        tvWorkingDay = findViewById(R.id.tvWorkingDay);
        ivPostImg2 = findViewById(R.id.ivPostImg2);
        ivTruckPic = findViewById(R.id.ivTruckPic);
        tv_status = findViewById(R.id.tv_status);

        //Multiple Image
       /* llUploadedMedia = findViewById(R.id.llUploadedMedia);
        rvUploadedMedia = findViewById(R.id.rvUploadedMedia);
        postAttachmentObjectArrayList = new ArrayList<>();*/


        transpoMarketListObjectArrayList = getIntent().getParcelableArrayListExtra("LoadListDisplay");
        position = getIntent().getIntExtra("PositionOfLoad",0);
        Log.d("position", "onCreate: "+position);


        detailsDisplay();
        //init();
        //webCallLoadDetails(1);

    }


   /* public void webCallLoadDetails(int page_count) {
        Helper_Method.showProgressBar(_act, "Loading...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTLoadList(SharedPref.getPrefs(_act, IConstant.USER_ID),
                SharedPref.getPrefs(_act, IConstant.USER_API_TOKEN),
                strLoad_type,
                strLoad_id,
                String.valueOf(page_count));
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    transpoMarketListObjectArrayList.clear();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {

                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);
                        //Explore List
                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            remaining = jsonObject.getInt("remaining");
                            Log.d("Webcall", "onResponse: Remaining COunt " + remaining);
                            loader_path = jsonObject.getString("loader_path");
                            vehicle_path = jsonObject.getString("vehicle_type_path");


                            JSONArray jsonArrayCategory = jsonObject.getJSONArray("loader_list");
                            for (int index = 0; index < jsonArrayCategory.length(); index++) {
                                try {
                                    transpoMarketListObjectArrayList.add(new TranspoMarketListObject(jsonArrayCategory.getJSONObject(index), vehicle_path, loader_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    Helper_Method.dismissProgessBar();
                                }
                            }

                            if (transpoMarketListObjectArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();


                            } else {
                                Helper_Method.dismissProgessBar();
                                detailsDisplay(transpoMarketListObjectArrayList);


                            }

                        } else {
                            Helper_Method.dismissProgessBar();
                            ;
                            //Helper_Method.dismissProgessBar();
                            if (jsonObject.has("isSessionExpired")) {
                                Helper_Method.autoLogout(_act, true, stringMsg);
                            } else {
                                Helper_Method.toaster(_act, stringMsg);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, t.getMessage());
                Log.d("Error", "onFailure: " + t.getMessage());
                if (t instanceof NetworkErrorException)
                    Helper_Method.warnUser(_act, "Network Error", getString(R.string.error_network), true);
                else if (t instanceof IOException)
                    Helper_Method.warnUser(_act, "Connection Error", getString(R.string.error_network), true);
                    //else if (t instanceof ServerError)
                    //   Helper_Method.warnUser(_act, "Server Error", getString(R.string.error_server), true);
                else if (t instanceof ConnectException)
                    Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_network), true);
                    //else if (t instanceof ConnectException)
                    //Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                else if (t instanceof TimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else if (t instanceof SocketTimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else
                    Helper_Method.warnUser(_act, "Unknown Error", getString(R.string.error_something_wrong), true);

            }
        });
    }*/


    

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

    public void detailsDisplay() {
       
        tvRoute.setText(Helper_Method.toTitleCase(transpoMarketListObjectArrayList.get(position).getPickup_loaction_name() + " - " + transpoMarketListObjectArrayList.get(position).getDrop_loaction_name()));
        tvPickLocation.setText(Helper_Method.toTitleCase("Pick Loacation : " + transpoMarketListObjectArrayList.get(position).getPickup_loaction_name()));
        tvDropLocation.setText(Helper_Method.toTitleCase("Drop Location : " + transpoMarketListObjectArrayList.get(position).getDrop_loaction_name()));
        tvPost.setText("Posted on " + DateTimeFormat.getDate3To4(transpoMarketListObjectArrayList.get(position).getCreated_at()));
        tvEstimateWeight.setText(transpoMarketListObjectArrayList.get(position).getVolumetric_weight() + " Tons Weight");
        tvTruckName.setText("Required " + transpoMarketListObjectArrayList.get(position).getTransport_type());

        tvWorkingDay.setText("Required on " + DateTimeFormat.getDate3To10(transpoMarketListObjectArrayList.get(position).getRequired_date()));
        tvMaterial.setText("Material to carry " + transpoMarketListObjectArrayList.get(position).getMaterial());
        tvExpectedPrice.setText(Helper_Method.getIndianRupee(transpoMarketListObjectArrayList.get(position).getExpected_price()));
        tvLength.setText(Helper_Method.toTitleCase(transpoMarketListObjectArrayList.get(position).getLength()));
        tvBreadth.setText(Helper_Method.toTitleCase(transpoMarketListObjectArrayList.get(position).getBreadth()));
        tvHeight.setText(Helper_Method.toTitleCase(transpoMarketListObjectArrayList.get(position).getHeight()));

       // Log.d("LoadAdapter", "truck :" + transpoMarketListObjectArrayList.get(position).getTransport_type_image());

       /* Glide.with(getApplicationContext())
                .load(transpoMarketListObjectArrayList.get(position).getTransport_type_image())
                .apply(new RequestOptions().placeholder(R.drawable.ic_truck_icon).error(R.drawable.ic_truck_icon).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivTruckPic);*/

        if (transpoMarketListObjectArrayList.get(0).getRemark() != null && !transpoMarketListObjectArrayList.get(0).getRemark().isEmpty() && !transpoMarketListObjectArrayList.get(0).getRemark().equals("null")) {

            tvRemark.setText("Remark : " + Helper_Method.toTitleCase(transpoMarketListObjectArrayList.get(0).getRemark()));
        } else {
            tvRemark.setText("");
            tvRemark.setVisibility(View.GONE);
        }


       /* if (transpoMarketListObjectArrayList.get(position).getV().equalsIgnoreCase("1")) {
            tvWorkType.setText("Part Load");
            ivPostImg2.setImageResource(R.drawable.ic_part_load);
        } else {
            tvWorkType.setText("Full Load");
            ivPostImg2.setImageResource(R.drawable.ic_load);
        }*/

        if (transpoMarketListObjectArrayList.get(position).getLoader_type().equalsIgnoreCase("1")) {
            tvWorkType.setText("Part Load");
            ivPostImg2.setImageResource(R.drawable.iv_load);
        } else {
            tvWorkType.setText("Full Load");
            ivPostImg2.setImageResource(R.drawable.iv_load);
        }


        if (transpoMarketListObjectArrayList.get(position).getPayment_mode().equalsIgnoreCase("2")) {
            tvPaymentMode.setText("To Pay");
        } else {
            tvPaymentMode.setText("To Be Billed");
        }

        if (transpoMarketListObjectArrayList.get(position).getFixed_per_tone().equalsIgnoreCase("1")) {
            tvNegotiable.setText("Negotiable");
        } else {
            tvNegotiable.setText("Fixed Rate");
        }


        if (transpoMarketListObjectArrayList.get(position).getIs_accept().equalsIgnoreCase("1")) {
            tv_status.setText("Accepted");
            tv_status.setTextColor(this.getResources().getColor(R.color.green));
        }
        else if(transpoMarketListObjectArrayList.get(position).getIs_accept().equalsIgnoreCase("0")){
            tv_status.setText("Pending");
            tv_status.setTextColor(this.getResources().getColor(R.color.red));
        }
        //Media Display
      /*  postAttachmentObjectArrayList = transpoMarketListObjectArrayList.get(position).getLoadImagesObjectArrayList();
        Log.d("Received", "onCreate: Media Size" + postAttachmentObjectArrayList.size());

        if (postAttachmentObjectArrayList.size()==0)
        {
            llUploadedMedia.setVisibility(View.GONE);
        }else
        {
            uploadedMediaAdatper = new UploadedMediaAdatper(_act, postAttachmentObjectArrayList);
            rvUploadedMedia = findViewById(R.id.rvUploadedMedia);
            linearLayoutManager = new GridLayoutManager(_act, 3);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rvUploadedMedia.setLayoutManager(linearLayoutManager);
            rvUploadedMedia.setItemAnimator(new DefaultItemAnimator());
            rvUploadedMedia.setHasFixedSize(true);
            rvUploadedMedia.setAdapter(uploadedMediaAdatper);
            uploadedMediaAdatper.notifyDataSetChanged();
            rvUploadedMedia.setVisibility(View.VISIBLE);
            llUploadedMedia.setVisibility(View.VISIBLE);
        }*/


    }
}