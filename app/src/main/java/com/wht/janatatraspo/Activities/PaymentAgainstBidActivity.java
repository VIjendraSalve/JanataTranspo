package com.wht.janatatraspo.Activities;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Model.Bid;
import com.wht.janatatraspo.R;

import java.util.ArrayList;

public class PaymentAgainstBidActivity extends BaseActivity {

    private Activity _act;
    private TextView tv_ispartload, tvWeight, tv_volumetric_weight, tv_material;
    private TextView tv_load_type_isfix_or_perton, tv_no_of_ton, tv_per_ton_price, tv_final_price, tv_commision_amount;
    private Button btn_pay_advance;

    private ArrayList<Bid> bidArrayList = new ArrayList<>();
    private int position = 0;
    private String commisionAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_bid_details);

        _act = PaymentAgainstBidActivity.this;
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

        Log.d("PaymentAgainstBid", "onCreate Size: "+bidArrayList.size());
        Log.d("PaymentAgainstBid", "onCreate position: "+position);

        initFun();

    }

    private void initFun() {

        tv_ispartload = findViewById(R.id.tv_ispartload);
        tvWeight = findViewById(R.id.tvWeight);
        tv_volumetric_weight = findViewById(R.id.tv_volumetric_weight);
        tv_material = findViewById(R.id.tv_material);
        tv_load_type_isfix_or_perton = findViewById(R.id.tv_load_type_isfix_or_perton);
        tv_no_of_ton = findViewById(R.id.tv_no_of_ton);
        tv_per_ton_price = findViewById(R.id.tv_per_ton_price);
        tv_final_price = findViewById(R.id.tv_final_price);
        tv_commision_amount = findViewById(R.id.tv_commision_amount);
        btn_pay_advance = findViewById(R.id.btn_pay_advance);

        if(bidArrayList.get(position).getLoader_type().equals("1")) {
            tv_ispartload.setText("Part Load");
        }
        else {
            tv_ispartload.setText("Full Load");
        }
        tvWeight.setText(bidArrayList.get(position).getNumber_of_ton()+" Tons Weight");
        tv_volumetric_weight.setText(bidArrayList.get(position).getVolumetric_weight()+" Volumetric Weight");
        tv_material.setText(bidArrayList.get(position).getMaterial());

    }
}