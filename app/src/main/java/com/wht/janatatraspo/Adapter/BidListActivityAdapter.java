package com.wht.janatatraspo.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wht.janatatraspo.Activities.AddEarthMoverRequestActivity;
import com.wht.janatatraspo.Activities.LoadBidDetailsActivity;
import com.wht.janatatraspo.Activities.PaymentAgainstBidActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Constant.IUrls;
import com.wht.janatatraspo.Constant.Interface;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.InitialActivities.OTPActivity;
import com.wht.janatatraspo.MainActivity;
import com.wht.janatatraspo.Model.Bid;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Constants;
import com.wht.janatatraspo.my_library.DateTimeFormat;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidListActivityAdapter extends RecyclerView.Adapter<BidListActivityAdapter.SponsorsHolder> {

    private ArrayList<Bid> bidArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag = 0;
    private ArrayList<String> policeList = new ArrayList<>();
    private String flagaccepted = "0", flagToDispalyAcceptButton="";
    private Activity activity;
    private String commision="";

    public BidListActivityAdapter(ArrayList<Bid> bidArrayList, String flagToDispalyAcceptButton, Activity activity, String commision) {
        this.bidArrayList = bidArrayList;
        this.flagToDispalyAcceptButton = flagToDispalyAcceptButton;
        this.activity = activity;
        this.commision = commision;
    }

    @Override
    public BidListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_bidding_list, parent, false);

        return new BidListActivityAdapter.SponsorsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BidListActivityAdapter.SponsorsHolder holder, final int position) {
        final Bid bid = bidArrayList.get(position);

        holder.tv_username.setTypeface(holder.tv_username.getTypeface(), Typeface.BOLD);
        holder.tv_bidamount.setTypeface(holder.tv_bidamount.getTypeface(), Typeface.BOLD);
        holder.tv_username.setText(bid.getFirst_name() + " " + bid.getLast_name());
        holder.tvTime1.setText(DateTimeFormat.getDate2(bid.getCreated_at()));
        holder.tv_bidamount.setText(Helper_Method.getIndianRupee(bid.getBid_amount()));
        holder.tv_allownces.setText( Helper_Method.getIndianRupee(bid.getAllownces()));
        holder.tv_waitingcharge.setText( Helper_Method.getIndianRupee(bid.getWaiting_chagres()));
        holder.tv_labourcharge.setText( Helper_Method.getIndianRupee(bid.getLabour_charge_per_person()));

        if (bid.getIs_accept().equals("0")) {
            holder.tvVerified1.setText("Pending");
            holder.tvVerified1.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else {
            holder.tvVerified1.setText("Accepted");
            holder.tvVerified1.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.tv_accept.setVisibility(View.GONE);
        }

        holder.tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flagToDispalyAcceptButton.equals("1")) {
                    showSettingsDialog(bid.getLoader_id(), bid.getBid_id());
                }else {
                callDialogueBox(bid.getMobile_no());
            }
            }
        });

        if(flagToDispalyAcceptButton.equals("1")){
            holder.tv_accept.setVisibility(View.VISIBLE);
            holder.tv_accept.setText("View Contact Details");
        }else {
            holder.tv_accept.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, ""+commision, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LoadBidDetailsActivity.class);
                intent.putParcelableArrayListExtra(IConstant.TranspoBidList, bidArrayList);
                intent.putExtra(IConstant.Position, position);
                intent.putExtra(IConstant.Commission, commision);
                context.startActivity(intent);
            }
        });

    }

    private void callDialogueBox(final String mobileNo) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dailog_contact_view);
        dialog.setCancelable(true);
        final TextView tv_mobile_number = (TextView) dialog.findViewById(R.id.tv_mobile_number);
        TextView tv_header = (TextView) dialog.findViewById(R.id.tv_header);
        tv_header.setTypeface(tv_header.getTypeface(), Typeface.BOLD);

        tv_mobile_number.setText(""+mobileNo);

        Button btn_ok = (Button) dialog.findViewById(R.id.btnSubmit);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        tv_mobile_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(activity)
                        .withPermissions(Manifest.permission.CALL_PHONE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +mobileNo));
                                    context.startActivity(intent);
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getAttributes().windowAnimations = R.style.MaterialDialog; //style id
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return bidArrayList.size();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }

    private void showSettingsDialog(final String load_id, final String bid_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation ");
        builder.setMessage("Are you sure, you want to accept this bid ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AcceptLoad(load_id, bid_id);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        public TextView tv_username, tvTime1, tv_bidamount, tv_allownces, tv_waitingcharge, tv_labourcharge, tvVerified1, tv_accept;
        public Button btn_update_cr_number;
        private ImageView ivPostImg1;


        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();

            tv_username = (TextView) view.findViewById(R.id.tv_username);
            tvTime1 = (TextView) view.findViewById(R.id.tvTime1);
            ivPostImg1 = (ImageView) view.findViewById(R.id.ivPostImg1);
            tv_bidamount = (TextView) view.findViewById(R.id.tv_bidamount);
            tv_allownces = (TextView) view.findViewById(R.id.tv_allownces);
            tv_waitingcharge = (TextView) view.findViewById(R.id.tv_waitingcharge);
            tv_labourcharge = (TextView) view.findViewById(R.id.tv_labourcharge);
            tvVerified1 = (TextView) view.findViewById(R.id.tvVerified1);
            tv_accept = (TextView) view.findViewById(R.id.tv_accept);


        }
    }

    private void AcceptLoad(String load_id, String Bid_id) {

        Helper_Method.showProgressBar(context, "Accepting...");

        Log.d("Input", "load_id: "+load_id);
        Log.d("Input", "Bid_id: "+Bid_id);
        Log.d("Input", "USER_ID: "+Shared_Preferences.getPrefs(context, IConstant.USER_ID));
        Log.d("Input", "USER_API_TOKEN: "+Shared_Preferences.getPrefs(context, IConstant.USER_API_TOKEN));

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTAcceptLoadBid(
                load_id,
                Bid_id,
                Shared_Preferences.getPrefs(context, IConstant.USER_ID),
                Shared_Preferences.getPrefs(context, IConstant.USER_API_TOKEN)
        );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);
                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            //String is_verified = jsonObject.getString(IConstant.RESPONSE_OTP_VERIFIED);
                            //if (is_verified.equalsIgnoreCase(IConstant.RESPONSE_VERIFIED)) {

                            Helper_Method.hideSoftInput(context);
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(context, ""+stringMsg, Toast.LENGTH_SHORT).show();
                            Helper_Method.dismissProgessBar();

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);


                        } else {
                            Helper_Method.hideSoftInput(context);
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(context, ""+stringMsg, Toast.LENGTH_SHORT).show();
                            Helper_Method.dismissProgessBar();

                        }

                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Issue", "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }
}

