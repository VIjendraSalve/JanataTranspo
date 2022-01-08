package com.wht.janatatraspo.Activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.wht.janatatraspo.Model.Load;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class LoadDetailsActivity extends BaseActivity {

    private TextView tv_load_type, tv_expectedprice, tv_pickup_city, tv_drop_city, tv_PaymentMode, tv_expectedweight,
            tv_islabourrequired, tv_numberoflabour, tv_RequiredDate, tv_Material, tv_Tone, tv_length, tv_breadth, tv_title,
            tv_vehicleName, tv_height;
    private ArrayList<Load> loadArrayList = new ArrayList<>();
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Load Details");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Activated Offers");*/

        loadArrayList = getIntent().getParcelableArrayListExtra("LoadListDisplay");
        position = getIntent().getIntExtra("PositionOfLoad",0);
        Log.d("position", "onCreate: "+position);
        init();

    }

    private void init() {

        tv_load_type = findViewById(R.id.tv_load_type);
        tv_expectedprice = findViewById(R.id.tv_expectedprice);
        tv_pickup_city = findViewById(R.id.tv_pickup_city);
        tv_drop_city = findViewById(R.id.tv_drop_city);
        tv_PaymentMode = findViewById(R.id.tv_PaymentMode);
        tv_expectedweight = findViewById(R.id.tv_expectedweight);
        tv_islabourrequired = findViewById(R.id.tv_islabourrequired);
        tv_numberoflabour = findViewById(R.id.tv_numberoflabour);
        tv_RequiredDate = findViewById(R.id.tv_RequiredDate);
        tv_Material = findViewById(R.id.tv_Material);
        tv_Tone = findViewById(R.id.tv_Tone);
        tv_length = findViewById(R.id.tv_length);
        tv_breadth = findViewById(R.id.tv_breadth);
        tv_title = findViewById(R.id.tv_title);
        tv_vehicleName = findViewById(R.id.tv_vehicleName);
        tv_height = findViewById(R.id.tv_height);

        if(loadArrayList.get(position).getLoader_type().equals("1")){
            tv_load_type.setText("Part Load");
        }else {
            tv_load_type.setText("Full Load");
        }
        tv_expectedprice.setText(Constants.rs + loadArrayList.get(position).getExpected_price());
        tv_pickup_city.setText(loadArrayList.get(position).getPickup_loaction_name());
        tv_drop_city.setText(loadArrayList.get(position).getDrop_loaction_name());
        Log.d("Values", "init: "+loadArrayList.get(position).getPayment_mode());
        if(loadArrayList.get(position).getPayment_mode().equals("1")){
            tv_PaymentMode.setText("To be billed");
        }else {
            tv_PaymentMode.setText("To Pay");
        }

        Log.d("Values", "getIs_lablor_required: "+loadArrayList.get(position).getIs_lablor_required());
        if(loadArrayList.get(position).getIs_lablor_required().equals("1")){
            tv_islabourrequired.setText("Yes");
        }else {
            tv_islabourrequired.setText("No");
        }

        Log.d("Values", "getNo_of_labour: "+loadArrayList.get(position).getNo_of_labour());
        tv_numberoflabour.setText(loadArrayList.get(position).getNo_of_labour());
        tv_RequiredDate.setText(loadArrayList.get(position).getRequired_date());
        tv_Material.setText(loadArrayList.get(position).getMaterial());
        tv_Tone.setText(loadArrayList.get(position).getNumber_of_ton());
        tv_length.setText(loadArrayList.get(position).getLength());
        tv_breadth.setText(loadArrayList.get(position).getBreadth());
        tv_height.setText(loadArrayList.get(position).getHeight());
        tv_expectedweight.setText(loadArrayList.get(position).getExpected_weight() + " Ton");
        //tv_vehicleName.setText(loadArrayList.get(position).lo());

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