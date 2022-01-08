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
import com.wht.janatatraspo.Activities.EarthMoverBidListActivity;
import com.wht.janatatraspo.Activities.LoadDetailsActivity;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Helpers.Helper_Method;
import com.wht.janatatraspo.Model.EarthMover;
import com.wht.janatatraspo.Model.Load;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Constants;
import com.wht.janatatraspo.my_library.DateTimeFormat;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import java.util.ArrayList;

public class MyEarthMoverListActivityAdapter extends RecyclerView.Adapter<MyEarthMoverListActivityAdapter.SponsorsHolder> {

    private ArrayList<EarthMover> loadArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag = 0;
    private String flagtoDisplay = "";
    private ArrayList<String> criminalList = new ArrayList<>();

    public MyEarthMoverListActivityAdapter(ArrayList<EarthMover> loadArrayList, String path) {
        this.loadArrayList = loadArrayList;
        this.flagtoDisplay = path;
    }

    @Override
    public MyEarthMoverListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_earth_mover_list, parent, false);

        return new MyEarthMoverListActivityAdapter.SponsorsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyEarthMoverListActivityAdapter.SponsorsHolder holder, final int position) {
        final EarthMover earthMover = loadArrayList.get(position);

        holder.tvWeight.setTypeface(holder.tvWeight.getTypeface(), Typeface.BOLD);
        holder.tvTitle.setTypeface(holder.tvTitle.getTypeface(), Typeface.BOLD);
        holder.tv_expectedprice.setTypeface(holder.tv_expectedprice.getTypeface(), Typeface.BOLD);
        holder.tvTitle.setText(Shared_Preferences.getPrefs(context, IConstant.USER_FIRST_NAME) + " "
                + Shared_Preferences.getPrefs(context, IConstant.USER_LAST_NAME));
        holder.tvpostedOn.setText(DateTimeFormat.getDate2(earthMover.getCreated_at()));
        holder.tv_pick_drop_loaction.setText("- "+" "+earthMover.getDestination_loaction_name());
        holder.tv_transport_type.setText("- "+" " + earthMover.getTransport_type());
        if (Integer.parseInt(earthMover.getNo_of_bid()) > 0)
            holder.bid1.setText("" + earthMover.getNo_of_bid() + " Bids");
        else
            holder.bid1.setText("No Bids");
        holder.tv_expectedprice.setText(Helper_Method.getIndianRupee(earthMover.getExpected_price()));
        holder.tv_required_on.setText("Required on : "+DateTimeFormat.getDate31(earthMover.getRequired_date()));

        if (earthMover.getPrices_timestamp().equals("1")) {
            holder.tvWeight.setText("- "+ " Hours : "+earthMover.getNo_of_hours()  );
        } else {
            holder.tvWeight.setText(("- "+ " Hours : "+earthMover.getNo_of_hours() + " For "+earthMover.getNo_of_days() +" Days" ));
        }

        if (earthMover.getIs_bid_accept().equals("0")) {
            holder.tvVerified.setText("Active");
        } else {
            holder.tvVerified.setText("Accepted");
        }

       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoadDetailsActivity.class);
                intent.putParcelableArrayListExtra("EarthMoverListDisplay", loadArrayList);
                intent.putExtra("PositionOfEarthMover", position);
                context.startActivity(intent);
            }
        });*/

        holder.bid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EarthMoverBidListActivity.class);
                intent.putExtra("EarthMoverID", loadArrayList.get(position).getId());
                intent.putExtra("FlagToDisplayAcceptButton", earthMover.getIs_bid_accept()); // 1: bid accepted, 0: bid is active
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return loadArrayList.size();
    }

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        public TextView tvWeight, tv_pick_drop_loaction,tv_transport_type,tv_required_on,  tvTitle, tvpostedOn, tv_expectedprice, tvVerified, tvKYCUpdate;
        private AppCompatButton bid1, is_partload;

        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();
            tvWeight = (TextView) view.findViewById(R.id.tvWeight);
            tv_pick_drop_loaction = (TextView) view.findViewById(R.id.tv_pick_drop_loaction);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvpostedOn = (TextView) view.findViewById(R.id.tvpostedOn);
            tv_expectedprice = (TextView) view.findViewById(R.id.tv_expectedprice);
            tvVerified = (TextView) view.findViewById(R.id.tvVerified);
            tv_transport_type = (TextView) view.findViewById(R.id.tv_transport_type);
            //tvKYCUpdate = (TextView) view.findViewById(R.id.tvKYCUpdate);
            bid1 = (AppCompatButton) view.findViewById(R.id.bid1);
            is_partload = (AppCompatButton) view.findViewById(R.id.is_partload);
            tv_required_on = (TextView) view.findViewById(R.id.tv_required_on);
        }
    }

}

