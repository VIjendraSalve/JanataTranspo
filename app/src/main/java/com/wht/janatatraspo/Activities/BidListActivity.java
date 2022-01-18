package com.wht.janatatraspo.Activities;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wht.janatatraspo.Adapter.BidListActivityAdapter;
import com.wht.janatatraspo.Adapter.MyLoadListActivityAdapter;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Model.Bid;
import com.wht.janatatraspo.Model.Load;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.CheckNetwork;
import com.wht.janatatraspo.my_library.Constants;
import com.wht.janatatraspo.my_library.MyConfig;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static android.view.View.GONE;

public class BidListActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "intent123";
    private ArrayList<Bid> bidArrayList = new ArrayList<>();
    private ArrayList<Bid> filteredbidArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private BidListActivityAdapter mAdapter;
    private LinearLayout noRecordLayout, noConnectionLayout;
    private ProgressBar progressView, progressBar_endless;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page_count = 1, remainingCount = 0;
    private int shipFlag = 1;
    private Button btnRetry, btn_submit, btn_next;
    //public static final String IMAGE_URL = "http://annadata.windhans.in/";
    private View retView;
    private Bundle bundle;
    private SearchView mSearchView;
    private String name = "", path = "";
    private Handler mHandler;
    private String query = "", load_id= "", flagToDispalyAcceptButton="";
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Bid List");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Activated Offers");*/

        load_id = getIntent().getStringExtra("LoadID");
        flagToDispalyAcceptButton = getIntent().getStringExtra("FlagToDisplayAcceptButton");

        noRecordLayout = (LinearLayout) findViewById(R.id.noRecordLayout);
        noConnectionLayout = (LinearLayout) findViewById(R.id.noConnectionLayout);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        // swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(this);
        progressView = (ProgressBar) findViewById(R.id.progress_view);
        progressBar_endless = (ProgressBar) findViewById(R.id.progressBar_endless);

    }


    private void getBidList() {

        Helper_Method.showProgressBar(BidListActivity.this, "Loading...");

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTBidList(
                Shared_Preferences.getPrefs(BidListActivity.this, IConstant.USER_ID),
                Shared_Preferences.getPrefs(BidListActivity.this , IConstant.USER_API_TOKEN),
                load_id
        );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    bidArrayList = new ArrayList<>();
                    bidArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        swipeRefreshLayout.setRefreshing(false);
                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("bider_list");
                            //pickupcityObjectArrayList.add(new CountryNameObject("0", "Select Country ", "Date"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    bidArrayList.add(new Bid(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    // scheduleDismiss();

                                }
                            }
                            progressView.setVisibility(GONE);
                            Log.d(TAG, "onResponse: "+i.getString("commision_percentage_bid_accept"));
                            mAdapter = new BidListActivityAdapter(bidArrayList,flagToDispalyAcceptButton,
                                    BidListActivity.this, i.getString("commision_percentage_bid_accept"));
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BidListActivity.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                            if(bidArrayList.size() > 0){
                                noRecordLayout.setVisibility(GONE);
                            }else {
                                noRecordLayout.setVisibility(View.VISIBLE);
                            }
                            swipeRefreshLayout.setRefreshing(false);

                        }





                    } catch (JSONException e) {
                        //scheduleDismiss();
                        swipeRefreshLayout.setRefreshing(false);
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    Helper_Method.dismissProgessBar();

                } finally {
                    swipeRefreshLayout.setRefreshing(false);
                    Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                swipeRefreshLayout.setRefreshing(false);
                Helper_Method.dismissProgessBar();

            }
        });
    }

    public void check_connection() {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))  //if connection available
        {
            noConnectionLayout.setVisibility(GONE);
            noRecordLayout.setVisibility(GONE);
            initFun();
        } else {
          /*  Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.internet_not_avilable,
                    Snackbar.LENGTH_INDEFINITE).setAction("RETRY",
                    v -> check_connection()).show();*/
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initFun() {
        mHandler = new Handler();
        //bundle = getActivity().getIntent().getExtras();
        //    exhibitorsPojo = bundle.getParcelable(Constants.TOUR_PLAN_DATA);
        progressDialog = new ProgressDialog(BidListActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_incidence);


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1 && filteredbidArrayList.size() > 9) {
                        if (remainingCount > 0) {
                            page_count++;
                            progressBar_endless.setVisibility(View.VISIBLE);
                            getBidList();
                        }
                    }
                }
            }
        });

        bidArrayList.clear();
       // progressView.setVisibility(View.VISIBLE);
        progressBar_endless.setVisibility(GONE);
        getBidList();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetry) {
            //orderList.clear();
            check_connection();
        }
    }

    @Override
    public void onRefresh() {
        if (bidArrayList.size() != 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                swipeRefreshLayout.setRefreshing(true);
                bidArrayList.clear();
                filteredbidArrayList.clear();
                mAdapter.notifyDataSetChanged();
                //page_count = dash1;count = dash1;
                page_count = 1;
                remainingCount = 0;
                //swipe=false;
                //getOrderList();
                getBidList();
                //swipe=true;
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar_endless.setVisibility(GONE);
            remainingCount = 0;
            page_count = 1;
            //get_my_rides(2);
            getBidList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        page_count = 1;
        check_connection();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

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