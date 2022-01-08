package com.wht.janatatraspo.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.wht.janatatraspo.R;


public class InformationActivity extends BaseActivity {

    private WebView webView;
    private String URL ;
    private ProgressBar progressBar;
    private Bundle bundle;
    private Activity _act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        bundle=getIntent().getExtras();
        _act = InformationActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(bundle.getString("title"));

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //toolbar_title.setText(bundle.getString("title"));
        URL=bundle.getString("url");

        webView = (WebView)findViewById(R.id.webview);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);

        WebViewLoadFunction();
    }

    public void WebViewLoadFunction(){

        // Add setWebViewClient on WebView.
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something here on page load start time.

            }
            @Override
            public void onPageFinished(WebView view, String url){
                // Show the toast message after finishing page loading.
                //Toast.makeText(ActivityPolicies.this,"Page Loading Finish.",Toast.LENGTH_SHORT).show();
            }
        });


        // Add setWebChromeClient on WebView.
        webView.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView webView1, int newProgress){

                //WebViewStatusTextView.setText("loading = " + newProgress + "%");
                progressBar.setProgress(newProgress);

                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                    // Page loading finish
                    //WebViewStatusTextView.setText("Page Load Finish.");
                }
            }
        });

        // Giving permissio to enable JavScript.
        webView.getSettings().setJavaScriptEnabled(true);

        // Pass the String variable which holds the website URL.
        webView.loadUrl(URL);
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
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}


