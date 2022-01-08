package com.wht.janatatraspo.InitialActivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.R;

public class ZoomScreenActivity extends BaseActivity {
    public static Activity _act;
    String Recipe_img_path = null;
    private ImageView pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_screen);
        _act = ZoomScreenActivity.this;

        pager = findViewById(R.id.pager);
        pager.setOnTouchListener(new ImageMatrixTouchHandler(_act));
        Recipe_img_path = getIntent().getStringExtra("image_path");

        DisplayMetrics metricscard = getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = metricscard.heightPixels;
        pager.getLayoutParams().height = cardheight / 1;
        pager.getLayoutParams().width = (int) (cardwidth / 1);
        Glide.with(_act)
                .load(Recipe_img_path)
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(pager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}