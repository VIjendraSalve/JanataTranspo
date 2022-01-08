package com.wht.janatatraspo.Activities;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.wht.janatatraspo.R;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }
}
