package com.wht.janatatraspo;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.wht.janatatraspo.Activities.BaseActivity;
import com.wht.janatatraspo.Activities.InformationActivity;
import com.wht.janatatraspo.Activities.PostLoadActivity;
import com.wht.janatatraspo.Adapter.HomeTabAdapter;
import com.wht.janatatraspo.Helpers.SharedPref;
import com.wht.janatatraspo.InitialActivities.LoginActivity;
import com.wht.janatatraspo.InitialActivities.ProfileActivity;
//import com.wht.janatatraspo.Notification.ActivityNotification;
import com.wht.janatatraspo.Notification.ActivityNotification;
import com.wht.janatatraspo.my_library.CheckNetwork;
import com.wht.janatatraspo.my_library.Shared_Preferences;


import static android.view.View.GONE;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        TabLayout.OnTabSelectedListener {

    //navigation drawer
    public Menu menu;
    boolean doubleBackToExitPressedOnce = false;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView nav_version;

    //layout
    private LinearLayout noConnectionLayout;
    private ScrollView ll_scroll;
    private Button btnRetry;

    //tab layout
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Janata Transpo");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        //navigationView.setItemIconTintList(null);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        View header = navigationView.getHeaderView(0);
        menu = navigationView.getMenu();

        nav_version = (TextView) navigationView.findViewById(R.id.nav_version);
        String versionName = null;
        try {
            versionName = MainActivity.this.getPackageManager()
                    .getPackageInfo(MainActivity.this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String version = versionName;
        Log.d("abc", "onCreate: " + version);
        nav_version.setText("version " + version);
        noConnectionLayout = findViewById(R.id.noConnectionLayout);

        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_connection();
            }
        });
        initFun();
    }

    private void initFun() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_home);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Transpo"));
        tabLayout.addTab(tabLayout.newTab().setText("Earth Mover"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager_complain);

        //Creating our pager adapter
        HomeTabAdapter adapter = new HomeTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
        int defaultValue = 0;
        int page = getIntent().getIntExtra("ARG_PAGE", defaultValue);
        viewPager.setCurrentItem(page);
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        // startActivity(new Intent(this,MainActivity.class));
    }

    public void check_connection() {
        if (CheckNetwork.isInternetAvailable(MainActivity.this))  //if connection available
        {
            noConnectionLayout.setVisibility(GONE);
            ll_scroll.setVisibility(View.VISIBLE);
            initFun();

        } else {
          /*  Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.internet_not_avilable,
                    Snackbar.LENGTH_INDEFINITE).setAction("RETRY",
                    v -> check_connection()).show();*/
            noConnectionLayout.setVisibility(View.VISIBLE);
            ll_scroll.setVisibility(GONE);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // true, then it has handled the app icon touch event
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_privacy) {

            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
            intent.putExtra("title","About Us");
            intent.putExtra("url","http://janatatranspo.com/");
            startActivity(intent);

        } else if (id == R.id.nav_about_us) {

            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
            intent.putExtra("title","About Us");
            intent.putExtra("url","http://janatatranspo.com/");
            startActivity(intent);

        } else if (id == R.id.nav_notification) {

            Toast.makeText(this, "Comming Soon", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ActivityNotification.class);
            startActivity(intent);

        }else if (id == R.id.nav_rate_us) {

            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/"));
            startActivity(viewIntent);

        } else if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_share_app) {
            /*Create an ACTION_SEND Intent*/
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            /*This will be the actual content you wish you share.*/
            String shareBody = "Download Janata Transpo app https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            /*The type of the content is text, obviously.*/
            intent.setType("text/plain");
            /*Applying information Subject and Body.*/
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            /*Fire!*/
            startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Are you sure you want to Logout?");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        Shared_Preferences.clearPref(MainActivity.this);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(MainActivity.this, "Logout Successfully...", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        //alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
       onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                System.exit(0);
            }


            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}