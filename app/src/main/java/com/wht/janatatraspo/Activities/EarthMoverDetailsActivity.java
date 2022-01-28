package com.wht.janatatraspo.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Helpers.ConnectionDetector;
import com.wht.janatatraspo.Helpers.DateTimeFormat;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Helpers.Validations;
import com.wht.janatatraspo.Model.EarthMover;
import com.wht.janatatraspo.R;

import java.util.ArrayList;

public class EarthMoverDetailsActivity extends BaseActivity {

    public int remaining = 0;
    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView toolbar_title;
    private Dialog dialog;
    private Button dailogtvOk;
    private ImageView dailogtvCancel;
    private TextView tvBidTotalAmount;
    private CheckBox cbNegotiable;
    private String strNegotiable = "0";
    private EditText etDriverBidAmount;
    //Display Data
    private TextView tvRoute, tvPost, tvTruckName, tvDropLocation,
            tvPaymentMode, tvRemark;
    private TextView tvVerified, tvKYCUpdate, tvBidRaised;
    private TextView tvNegotiable, tvExpectedPrice, tvEstimatePrice, tvMaterial, tvWorkingDay;
    private ImageView ivPostImg2, ivTruckPic;
    private ArrayList<EarthMover> transpoMarketListObjectArrayListRec;
    private String vehicle_path = null;
    private String loader_path = null;
    private int page_count = 1, position = 0;

    private String earth_mover_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_mover_details);

        //earth_mover_id = getIntent().getStringExtra("earth_mover_id");

        _act = EarthMoverDetailsActivity.this;
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.removePhoneKeypad(_act);
        validations = new Validations();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.apptheme)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Earth Mover Details");
        toolbar_title.setTextColor(getResources().getColor(R.color.colorPrimary));
        Helper_Method.setFontToolbard(_act, toolbar_title);

        transpoMarketListObjectArrayListRec = new ArrayList<>();

  /*      tvLabourCharges = findViewById(R.id.tvLabourCharges);
        tvLabourReqCount = findViewById(R.id.tvLabourReqCount);
        tvAllowances = findViewById(R.id.tvAllowances);
        tvWaitngCharges = findViewById(R.id.tvWaitngCharges);*/


        tvRoute = findViewById(R.id.tvRoute);
        tvPost = findViewById(R.id.tvPost);
        tvTruckName = findViewById(R.id.tvTruckName);
        tvDropLocation = findViewById(R.id.tvDropLocation);
        tvPaymentMode = findViewById(R.id.tvPaymentMode);

        tvRemark = findViewById(R.id.tvRemark);
        tvVerified = findViewById(R.id.tvVerified);
        tvKYCUpdate = findViewById(R.id.tvKYCUpdate);

        tvBidRaised = findViewById(R.id.tvBidRaised);
        tvNegotiable = findViewById(R.id.tvNegotiable);
        tvExpectedPrice = findViewById(R.id.tvExpectedPrice);
        tvEstimatePrice = findViewById(R.id.tvEstimatePrice);
        tvMaterial = findViewById(R.id.tvMaterial);
        tvWorkingDay = findViewById(R.id.tvWorkingDay);
        ivPostImg2 = findViewById(R.id.ivPostImg2);
        ivTruckPic = findViewById(R.id.ivTruckPic);

        transpoMarketListObjectArrayListRec = getIntent().getParcelableArrayListExtra(IConstant.EarthMoverList);
        position = getIntent().getIntExtra(IConstant.PositionEarthMover, 0);
        detailsDisplay();

    }


    private boolean isBidValid() {
        if (validations.isBlank(etDriverBidAmount)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etDriverBidAmount.startAnimation(shake);
            etDriverBidAmount.setError(getResources().getString(R.string.error_field_required));
            return false;
        }

        return true;
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

    public void detailsDisplay() {

        tvRoute.setText(Helper_Method.toTitleCase(transpoMarketListObjectArrayListRec.get(position).getDestination_loaction_name()));
        tvDropLocation.setText(Helper_Method.toTitleCase("Working Location : " + transpoMarketListObjectArrayListRec.get(position).getDestination_loaction_name()));
        tvPost.setText("Posted on " + DateTimeFormat.getDate3To4(transpoMarketListObjectArrayListRec.get(position).getCreated_at()));
        tvTruckName.setText("Required " + transpoMarketListObjectArrayListRec.get(position).getTransport_type());

        tvWorkingDay.setText("Required on " + DateTimeFormat.getDate3To10(transpoMarketListObjectArrayListRec.get(position).getRequired_date()));
        tvMaterial.setText(transpoMarketListObjectArrayListRec.get(position).getNo_of_days() + " Days  and " + transpoMarketListObjectArrayListRec.get(position).getNo_of_hours() + " hours( per day work)");
        tvExpectedPrice.setText(Helper_Method.getIndianRupee(transpoMarketListObjectArrayListRec.get(position).getExpected_price()));

        //Log.d("LoadAdapter", "truck :" + transpoMarketListObjectArrayListRec.get(position).getTransport_type_image());

        /*Glide.with(getApplicationContext())
                .load(transpoMarketListObjectArrayListRec.get(position).getTransport_type_image())
                .apply(new RequestOptions().placeholder(R.drawable.ic_truck_icon).error(R.drawable.ic_truck_icon).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivTruckPic);*/

        if (transpoMarketListObjectArrayListRec.get(0).getRemark() != null && !transpoMarketListObjectArrayListRec.get(0).getRemark().isEmpty() && !transpoMarketListObjectArrayListRec.get(0).getRemark().equals("null")) {

            tvRemark.setText("Remark : " + Helper_Method.toTitleCase(transpoMarketListObjectArrayListRec.get(0).getRemark()));
        } else {
            tvRemark.setText("");
            tvRemark.setVisibility(View.GONE);
        }


       /* if (transpoMarketListObjectArrayListRec.get(position).getV().equalsIgnoreCase("1")) {
            tvWorkType.setText("Part Load");
            ivPostImg2.setImageResource(R.drawable.ic_part_load);
        } else {
            tvWorkType.setText("Full Load");
            ivPostImg2.setImageResource(R.drawable.ic_load);
        }*/


/*
        if (transpoMarketListObjectArrayListRec.get(position).getP().equalsIgnoreCase("2")) {
            tvPaymentMode.setText("To Pay");
        } else {
            tvPaymentMode.setText("To Be Billed");
        }*/

        if (transpoMarketListObjectArrayListRec.get(position).getIs_fixed_or_negotiable().equalsIgnoreCase("2")) {
            tvNegotiable.setText("Negotiable");

        } else {
            tvNegotiable.setText("Fixed Rate");
        }


    }
}