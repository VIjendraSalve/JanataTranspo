package com.wht.janatatraspo.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wht.janatatraspo.Model.ImagePOJO;
import com.wht.janatatraspo.R;
import com.wht.janatatraspo.my_library.Constants;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.wht.janatatraspo.my_library.Constants.loadImageDoc;


/**
 * Created by wind Prasanna on 11/1/2017.
 */

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ImageViewHolder> {

    Context context;
    int IMG_TYPE,DOC_TYPE;
    String IMG_URL,id,doc_name;
    List<ImagePOJO> data=new ArrayList<>();
    boolean isShow =false;
    private ProgressDialog progressDialog;

    public AdapterImages(Context context, int IMG_TYPE, String IMG_URL, List<ImagePOJO> data, int DOC_TYPE, String id, String doc_name) {
        this.context = context;
        this.IMG_TYPE = IMG_TYPE;
        this.IMG_URL = IMG_URL;
        this.data = data;
        this.isShow =false;
        this.DOC_TYPE=DOC_TYPE;
        this.doc_name=doc_name;
        this.id=id;

    }
 public AdapterImages(Context context, int IMG_TYPE, String IMG_URL, List<ImagePOJO> data, boolean isShow) {
        this.context = context;
        this.IMG_TYPE = IMG_TYPE;
        this.IMG_URL = IMG_URL;
        this.data = data;
        this.isShow =true;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_image_row,parent,false));
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position)
    {
        final ImagePOJO current=data.get(position);
        if (!current.getImg_path().equalsIgnoreCase(""))
        Glide.with(context).load(current.getImg_path())
                .thumbnail(0.5f)
                .into(holder.iv_tag_img);
        else
            loadImageDoc(context, IMG_URL+current.getImg_name(),holder.iv_tag_img);


        holder.imgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!current.getImg_path().equalsIgnoreCase(""))
                    Constants.showDocument(context,current.getImg_path());
                else
                    Constants.showDocument(context,IMG_URL+current.getImg_name());

            }
        });

        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageUpdate)context).onUpdateImage(IMG_TYPE,position,holder.iv_tag_img);
                notifyItemChanged(position);
            }
        });

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!current.getImg_id().equalsIgnoreCase("0"))
                remove_alert(position);
                else
                {
                    notifyItemRemoved(position);
                    data.remove(position);
                }

            }
        });

        if (isShow)
        {
            holder.imgUpdate.setVisibility(View.GONE);
            holder.imgRemove.setVisibility(View.GONE);
        }

    }
    private void remove_alert(final int position1)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Remove Document Image");
        alertDialog.setMessage("Are You Sure You Want to Remove this Document Image?");
        alertDialog.setIcon(android.R.drawable.ic_delete);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //remove(position1);
            }
        });

        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_tag_img, imgRemove,imgUpdate,imgShow;

        public ImageViewHolder(View itemView) {
            super(itemView);
            iv_tag_img = (ImageView) itemView.findViewById(R.id.iv_tag_img);
            imgRemove = (ImageView) itemView.findViewById(R.id.imgRemove);
            imgUpdate = (ImageView) itemView.findViewById(R.id.imgUpdate);
            imgShow = (ImageView) itemView.findViewById(R.id.imgShow);
        }
    }


    public interface ImageUpdate {
        void onUpdateImage(int IMG_TYPE, int position, ImageView imageView);
            }
}
