package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class EarthMoverBid implements Parcelable {

    private String bid_id;
    private String id;
    private String user_id;
    private String earth_mover_id;
    private String is_bider_type;
    private String is_negotialable;
    private String bid_amount;
    private String is_accept;
    private String created_by;
    private String created_at;
    private String updated_at;
    private String first_name;
    private String last_name;
    private String image;
    private String business_name;
    private String mobile_no;

    public EarthMoverBid(JSONObject object) {
        try {
            this.bid_id = object.getString("bid_id");
            this.id = object.getString("id");
            this.user_id = object.getString("user_id");
            this.earth_mover_id = object.getString("earth_mover_id");
            this.is_bider_type = object.getString("is_bider_type");
            this.is_negotialable = object.getString("is_negotialable");
            this.bid_amount = object.getString("bid_amount");
            this.is_accept = object.getString("is_accept");
            this.created_by = object.getString("created_by");
            this.created_at = object.getString("created_at");
            this.updated_at = object.getString("updated_at");
            this.first_name = object.getString("first_name");
            this.last_name = object.getString("last_name");
            this.image = object.getString("image");
            this.business_name = object.getString("business_name");
            this.mobile_no = object.getString("mobile_no");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected EarthMoverBid(Parcel in) {
        bid_id = in.readString();
        id = in.readString();
        user_id = in.readString();
        earth_mover_id = in.readString();
        is_bider_type = in.readString();
        is_negotialable = in.readString();
        bid_amount = in.readString();
        is_accept = in.readString();
        created_by = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        image = in.readString();
        business_name = in.readString();
        mobile_no = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bid_id);
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(earth_mover_id);
        dest.writeString(is_bider_type);
        dest.writeString(is_negotialable);
        dest.writeString(bid_amount);
        dest.writeString(is_accept);
        dest.writeString(created_by);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(image);
        dest.writeString(business_name);
        dest.writeString(mobile_no);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EarthMoverBid> CREATOR = new Creator<EarthMoverBid>() {
        @Override
        public EarthMoverBid createFromParcel(Parcel in) {
            return new EarthMoverBid(in);
        }

        @Override
        public EarthMoverBid[] newArray(int size) {
            return new EarthMoverBid[size];
        }
    };

    public String getBid_id() {
        return bid_id;
    }

    public void setBid_id(String bid_id) {
        this.bid_id = bid_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEarth_mover_id() {
        return earth_mover_id;
    }

    public void setEarth_mover_id(String earth_mover_id) {
        this.earth_mover_id = earth_mover_id;
    }

    public String getIs_bider_type() {
        return is_bider_type;
    }

    public void setIs_bider_type(String is_bider_type) {
        this.is_bider_type = is_bider_type;
    }

    public String getIs_negotialable() {
        return is_negotialable;
    }

    public void setIs_negotialable(String is_negotialable) {
        this.is_negotialable = is_negotialable;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getIs_accept() {
        return is_accept;
    }

    public void setIs_accept(String is_accept) {
        this.is_accept = is_accept;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
