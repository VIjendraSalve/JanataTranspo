package com.wht.janatatraspo.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.wht.janatatraspo.Activities.BidListActivity;
import com.wht.janatatraspo.Activities.LoadDetailsActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Model.Load;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.DateTimeFormat;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import java.util.ArrayList;

public class MyLoadListActivityAdapter extends RecyclerView.Adapter<MyLoadListActivityAdapter.SponsorsHolder> {

    private ArrayList<Load> loadArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag = 0;
    private String flagtoDisplay = "";
    private ArrayList<String> criminalList = new ArrayList<>();

    public MyLoadListActivityAdapter(ArrayList<Load> loadArrayList, String path) {
        this.loadArrayList = loadArrayList;
        this.flagtoDisplay = path;
    }

    @Override
    public MyLoadListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_load_list, parent, false);

        return new MyLoadListActivityAdapter.SponsorsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyLoadListActivityAdapter.SponsorsHolder holder, final int position) {
        final Load load = loadArrayList.get(position);

        holder.tvWeight.setTypeface(holder.tvWeight.getTypeface(), Typeface.BOLD);
        holder.tvTitle.setTypeface(holder.tvTitle.getTypeface(), Typeface.BOLD);
        holder.tv_expectedprice.setTypeface(holder.tv_expectedprice.getTypeface(), Typeface.BOLD);
        holder.tvTitle.setText(Shared_Preferences.getPrefs(context, IConstant.USER_FIRST_NAME) + " "
                + Shared_Preferences.getPrefs(context, IConstant.USER_LAST_NAME));
        holder.tvpostedOn.setText(DateTimeFormat.getDate2(load.getCreated_at()));
        holder.tv_required_on.setText("Required on : " + DateTimeFormat.getDate31(load.getRequired_date()));
        holder.tvWeight.setText("- " + load.getNumber_of_ton() + " Tons Weight");
        holder.tv_pick_drop_loaction.setText("- " + load.getPickup_loaction_name() + " To " + load.getDrop_loaction_name());
        holder.tv_material.setText("- " + "Material to carry " + load.getMaterial());
        if (Integer.parseInt(load.getNo_of_bid()) > 0)
            holder.bid1.setText("" + load.getNo_of_bid() + " Bids");
        else
            holder.bid1.setText("No Bids");
        holder.tv_expectedprice.setText(Helper_Method.getIndianRupee(load.getExpected_price()));

        if (load.getLoader_type().equals("1")) {
            holder.tv_ispartload.setText("- " + "Part Load");
        } else {
            holder.tv_ispartload.setText("- " + "Full Load");
        }

        if (load.getIs_accept().equals("0")) {
            holder.tvVerified.setText("Active");
        } else {
            holder.tvVerified.setText("Accepted");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoadDetailsActivity.class);
                intent.putParcelableArrayListExtra("LoadListDisplay", loadArrayList);
                intent.putExtra("PositionOfLoad", position);
                context.startActivity(intent);
            }
        });

        holder.bid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BidListActivity.class);
                intent.putExtra("LoadID", loadArrayList.get(position).getId());
                intent.putExtra("FlagToDisplayAcceptButton", load.getIs_accept()); // 1: bid accepted, 0: bid is active
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return loadArrayList.size();
    }

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        public TextView tvWeight, tv_pick_drop_loaction, tv_ispartload, tv_required_on,
                tv_material, tvTitle, tvpostedOn, tv_expectedprice, tvVerified, tvKYCUpdate;
        private AppCompatButton bid1, is_partload;

        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();
            tvWeight = (TextView) view.findViewById(R.id.tvWeight);
            tv_pick_drop_loaction = (TextView) view.findViewById(R.id.tv_pick_drop_loaction);
            tv_material = (TextView) view.findViewById(R.id.tv_material);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvpostedOn = (TextView) view.findViewById(R.id.tvpostedOn);
            tv_expectedprice = (TextView) view.findViewById(R.id.tv_expectedprice);
            tvVerified = (TextView) view.findViewById(R.id.tvVerified);
            tv_required_on = (TextView) view.findViewById(R.id.tv_required_on);
            tv_ispartload = (TextView) view.findViewById(R.id.tv_ispartload);
            //tvKYCUpdate = (TextView) view.findViewById(R.id.tvKYCUpdate);
            bid1 = (AppCompatButton) view.findViewById(R.id.bid1);
            is_partload = (AppCompatButton) view.findViewById(R.id.is_partload);
        }
    }

}

