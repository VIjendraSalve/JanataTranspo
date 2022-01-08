package com.wht.janatatraspo.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.wht.janatatraspo.Activities.PostLoadActivity;
import com.wht.janatatraspo.Adapter.MyLoadListActivityAdapter;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Model.CityObject;
import com.wht.janatatraspo.Model.Load;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.CheckNetwork;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyLoadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyLoadFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ExtendedFloatingActionButton button_Postload;
    private NestedScrollView nestedScrollView;
    private LinearLayout ll_main_layout;

    private ArrayList<Load> loadArrayList = new ArrayList<>();
    private ArrayList<Load> filteredloadArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private MyLoadListActivityAdapter mAdapter;
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
    private String query = "", flag="0"; //flag 1: bid accepted 0: bid not accepted

    public MyLoadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyLoadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyLoadFragment newInstance(String param1, String param2) {
        MyLoadFragment fragment = new MyLoadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_load, container, false);

        noRecordLayout = (LinearLayout) view.findViewById(R.id.noRecordLayout);
        noConnectionLayout = (LinearLayout) view.findViewById(R.id.noConnectionLayout);
        btnRetry = (Button) view.findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        // swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        //swipeRefreshLayout.setEnabled(false);

        progressView = (ProgressBar) view.findViewById(R.id.progress_view);
        progressBar_endless = (ProgressBar) view.findViewById(R.id.progressBar_endless);

        button_Postload = view.findViewById(R.id.button_Postload);
        button_Postload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostLoadActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        check_connection();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetry) {
            //orderList.clear();
            check_connection();
        }
    }

    public void check_connection() {
        if (CheckNetwork.isInternetAvailable(getContext()))  //if connection available
        {
            noConnectionLayout.setVisibility(GONE);
            noRecordLayout.setVisibility(GONE);
            init();
        } else {
          /*  Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.internet_not_avilable,
                    Snackbar.LENGTH_INDEFINITE).setAction("RETRY",
                    v -> check_connection()).show();*/
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        mHandler = new Handler();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_criminal);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1 && filteredloadArrayList.size() > 49) {
                        if (remainingCount > 0) {
                            page_count++;
                            progressBar_endless.setVisibility(View.VISIBLE);
                            getLoadList();
                        }
                    }
                }
            }
        });

        loadArrayList.clear();
        filteredloadArrayList.clear();
//        mAdapter.notifyDataSetChanged();
       // progressView.setVisibility(View.VISIBLE);
        progressBar_endless.setVisibility(GONE);
        swipeRefreshLayout.setRefreshing(false);
        getLoadList();

    }

    private void getLoadList() {

       // Helper_Method.showProgressBar(getContext(), "Loading...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTLoadList(
                Shared_Preferences.getPrefs(getContext(), IConstant.USER_ID),
                Shared_Preferences.getPrefs(getContext(), IConstant.USER_API_TOKEN),
                ""
                );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    loadArrayList = new ArrayList<>();
                    loadArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);
                        Helper_Method.dismissProgessBar();
                        swipeRefreshLayout.setRefreshing(false);
                        if (stringCode.equalsIgnoreCase("true")) {
                            Helper_Method.dismissProgessBar();
                            JSONArray jsonArray = i.getJSONArray("loader_list");
                            //pickupcityObjectArrayList.add(new CountryNameObject("0", "Select Country ", "Date"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    loadArrayList.add(new Load(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    // scheduleDismiss();

                                }
                            }
                            progressView.setVisibility(GONE);
                            Helper_Method.dismissProgessBar();
                            for (int j = 0; j <loadArrayList.size() ; j++) {
                                if(loadArrayList.get(j).getIs_accept().equals("1")){
                                    flag = "1"; // load accepted
                                    break;
                                }else {
                                    flag = "0"; // load pending or expired
                                }
                            }
                            swipeRefreshLayout.setRefreshing(false);
                            Helper_Method.dismissProgessBar();
                            mAdapter = new MyLoadListActivityAdapter(loadArrayList, "");
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();

                        }
                        swipeRefreshLayout.setRefreshing(false);
                        if(loadArrayList.size() > 0){
                            noRecordLayout.setVisibility(GONE);
                        }else {
                            noRecordLayout.setVisibility(View.VISIBLE);
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
                Helper_Method.dismissProgessBar();

            }
        });
    }

    @Override
    public void onRefresh() {
        if (loadArrayList.size() != 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                swipeRefreshLayout.setRefreshing(true);
                loadArrayList.clear();
                filteredloadArrayList.clear();
                mAdapter.notifyDataSetChanged();
                //page_count = dash1;count = dash1;
                page_count = 1;
                remainingCount = 0;
                //swipe=false;
                //getOrderList();
                getLoadList();
                //swipe=true;
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar_endless.setVisibility(GONE);
            remainingCount = 0;
            page_count = 1;
            //get_my_rides(2);
            getLoadList();
        }
    }
    
}