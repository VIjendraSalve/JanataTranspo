package com.wht.janatatraspo.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wht.janatatraspo.Constant.IConstant;
import com.wht.janatatraspo.Model.Load;
import com.wht.janatatraspo.Model.VehicleType;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.DateTimeFormat;
import com.wht.janatatraspo.my_library.Shared_Preferences;

import java.util.ArrayList;

public class VehicleListActivityAdapter extends RecyclerView.Adapter<VehicleListActivityAdapter.SponsorsHolder> {

    private ArrayList<VehicleType> criminalArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag=0;
    private String path="";
    private ArrayList<String> criminalList = new ArrayList<>();
    private int lastSelectedPos = -1;

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        public TextView tv_vehicle_type_name;
        public ImageView iv_image, iv_image_selected;


        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();
            tv_vehicle_type_name = (TextView) view.findViewById(R.id.tv_vehicle_type_name);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            iv_image_selected = (ImageView) view.findViewById(R.id.iv_image_selected);

        }
    }


    public VehicleListActivityAdapter(ArrayList<VehicleType> criminalArrayList, String path) {
        this.criminalArrayList = criminalArrayList;
        this.path = path;
    }

    @Override
    public VehicleListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_vehicle_type, parent, false);

        return new VehicleListActivityAdapter.SponsorsHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final VehicleListActivityAdapter.SponsorsHolder holder, final int position) {
        final VehicleType vehicleType = criminalArrayList.get(position);

        holder.tv_vehicle_type_name.setTypeface(holder.tv_vehicle_type_name.getTypeface(), Typeface.BOLD);
        holder.tv_vehicle_type_name.setText(vehicleType.getType());

        if (criminalArrayList.get(position).isSelected()) {
            holder.iv_image_selected.setVisibility(View.VISIBLE);
        } else {
            holder.iv_image_selected.setVisibility(View.INVISIBLE);
        }


        Glide.with(context)
                .load(path + vehicleType.getImage()) // this uri is always same. because using one cache file
                .apply(new RequestOptions().placeholder(R.drawable.no_image_available).error(R.drawable.no_image_available))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.iv_image);


    }


    @Override
    public int getItemCount() {
        return criminalArrayList.size();
    }

}

