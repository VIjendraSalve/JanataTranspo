package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class EarthMover implements Parcelable {

    private String id;
    private String user_id;
    private String destination_lat;
    private String destination_lng;
    private String destination_loaction;
    private String transport_type;
    private String is_fixed_or_negotiable;
    private String expected_price;
    private String prices_timestamp;
    private String no_of_days;
    private String no_of_hours;
    private String remark;
    private String required_date;
    private String is_active;
    private String is_bid_accept;
    private String created_at;
    private String destination_loaction_name;
    private String no_of_bid;

    public EarthMover(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.user_id = object.getString("user_id");
            this.destination_lat = object.getString("destination_lat");
            this.destination_lng = object.getString("destination_lng");
            this.destination_loaction = object.getString("destination_loaction");
            this.transport_type = object.getString("transport_type");
            this.is_fixed_or_negotiable = object.getString("is_fixed_or_negotiable");
            this.expected_price = object.getString("expected_price");
            this.prices_timestamp = object.getString("prices_timestamp");
            this.no_of_days = object.getString("no_of_days");
            this.no_of_hours = object.getString("no_of_hours");
            this.remark = object.getString("remark");
            this.required_date = object.getString("required_date");
            this.is_active = object.getString("is_active");
            this.is_bid_accept = object.getString("is_bid_accept");
            this.created_at = object.getString("created_at");
            this.destination_loaction_name = object.getString("destination_loaction_name");
            this.no_of_bid = object.getString("no_of_bid");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected EarthMover(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        destination_lat = in.readString();
        destination_lng = in.readString();
        destination_loaction = in.readString();
        transport_type = in.readString();
        is_fixed_or_negotiable = in.readString();
        expected_price = in.readString();
        prices_timestamp = in.readString();
        no_of_days = in.readString();
        no_of_hours = in.readString();
        remark = in.readString();
        required_date = in.readString();
        is_active = in.readString();
        is_bid_accept = in.readString();
        created_at = in.readString();
        destination_loaction_name = in.readString();
        no_of_bid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(destination_lat);
        dest.writeString(destination_lng);
        dest.writeString(destination_loaction);
        dest.writeString(transport_type);
        dest.writeString(is_fixed_or_negotiable);
        dest.writeString(expected_price);
        dest.writeString(prices_timestamp);
        dest.writeString(no_of_days);
        dest.writeString(no_of_hours);
        dest.writeString(remark);
        dest.writeString(required_date);
        dest.writeString(is_active);
        dest.writeString(is_bid_accept);
        dest.writeString(created_at);
        dest.writeString(destination_loaction_name);
        dest.writeString(no_of_bid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EarthMover> CREATOR = new Creator<EarthMover>() {
        @Override
        public EarthMover createFromParcel(Parcel in) {
            return new EarthMover(in);
        }

        @Override
        public EarthMover[] newArray(int size) {
            return new EarthMover[size];
        }
    };

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

    public String getDestination_lat() {
        return destination_lat;
    }

    public void setDestination_lat(String destination_lat) {
        this.destination_lat = destination_lat;
    }

    public String getDestination_lng() {
        return destination_lng;
    }

    public void setDestination_lng(String destination_lng) {
        this.destination_lng = destination_lng;
    }

    public String getDestination_loaction() {
        return destination_loaction;
    }

    public void setDestination_loaction(String destination_loaction) {
        this.destination_loaction = destination_loaction;
    }

    public String getTransport_type() {
        return transport_type;
    }

    public void setTransport_type(String transport_type) {
        this.transport_type = transport_type;
    }

    public String getIs_fixed_or_negotiable() {
        return is_fixed_or_negotiable;
    }

    public void setIs_fixed_or_negotiable(String is_fixed_or_negotiable) {
        this.is_fixed_or_negotiable = is_fixed_or_negotiable;
    }

    public String getExpected_price() {
        return expected_price;
    }

    public void setExpected_price(String expected_price) {
        this.expected_price = expected_price;
    }

    public String getPrices_timestamp() {
        return prices_timestamp;
    }

    public void setPrices_timestamp(String prices_timestamp) {
        this.prices_timestamp = prices_timestamp;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getNo_of_hours() {
        return no_of_hours;
    }

    public void setNo_of_hours(String no_of_hours) {
        this.no_of_hours = no_of_hours;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequired_date() {
        return required_date;
    }

    public void setRequired_date(String required_date) {
        this.required_date = required_date;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_bid_accept() {
        return is_bid_accept;
    }

    public void setIs_bid_accept(String is_bid_accept) {
        this.is_bid_accept = is_bid_accept;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDestination_loaction_name() {
        return destination_loaction_name;
    }

    public void setDestination_loaction_name(String destination_loaction_name) {
        this.destination_loaction_name = destination_loaction_name;
    }

    public String getNo_of_bid() {
        return no_of_bid;
    }

    public void setNo_of_bid(String no_of_bid) {
        this.no_of_bid = no_of_bid;
    }
}
