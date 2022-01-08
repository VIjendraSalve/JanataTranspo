package com.wht.janatatraspo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wht.janatatraspo.R;

import java.util.ArrayList;

public class MultiimageSelectorAdapter extends RecyclerView.Adapter<MultiimageSelectorAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mImagesList;

    public MultiimageSelectorAdapter(Context context, ArrayList<String> imagesList) {
        mContext = context;
        mImagesList = imagesList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.multi_image_seletor_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        String extension = mImagesList.get(holder.getAdapterPosition()).substring(mImagesList.get(holder.getAdapterPosition()).lastIndexOf("."));
        Log.d("Image_Extension", "onBindViewHolder: " + extension);

        if (extension.equalsIgnoreCase(".jpg")
                || extension.equalsIgnoreCase(".png")
                || extension.equalsIgnoreCase(".jpeg")) {
            Glide.with(mContext).load(mImagesList.get(holder.getAdapterPosition()))
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square).centerCrop())
                    .into(holder.getImage());
            holder.iv_play.setVisibility(View.GONE);
        } else {
            Glide.with(mContext).load(mImagesList.get(holder.getAdapterPosition()))
                    .apply(new RequestOptions().placeholder(R.drawable.default_square).error(R.drawable.default_square).centerCrop())
                    .into(holder.getImage());
            holder.iv_play.setVisibility(View.VISIBLE);
        }

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagesList.remove(position);
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mImagesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image, iv_play,ivDelete;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image_view);
            iv_play = v.findViewById(R.id.iv_play);
            ivDelete = v.findViewById(R.id.ivDelete);
        }

        public ImageView getImage() {
            return image;
        }

    }
}

