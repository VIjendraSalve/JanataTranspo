package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Load implements Parcelable {

    private String id;
    private String user_id;
    private String loader_type;
    private String pickup_lat;
    private String pickup_lng;
    private String pickup_loaction;
    private String drop_loaction;
    private String drop_lat;
    private String drop_lng;
    private String transport_type;
    private String material;
    private String number_of_ton;
    private String expected_price;
    private String fixed_per_tone;
    private String payment_mode;
    private String volumetric_weight;
    private String length;
    private String breadth;
    private String height;
    private String expected_weight;
    private String remark;
    private String is_lablor_required;
    private String no_of_labour;
    private String required_date;
    private String is_active;
    private String pickup_loaction_name;
    private String drop_loaction_name;
    private String created_at;
    private String no_of_bid;
    private String is_accept;
    private String ride_status;

    public Load(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.user_id = object.getString("user_id");
            this.loader_type = object.getString("loader_type");
            this.pickup_lat = object.getString("pickup_lat");
            this.pickup_lng = object.getString("pickup_lng");
            this.pickup_loaction = object.getString("pickup_loaction");
            this.drop_loaction = object.getString("drop_loaction");
            this.drop_lat = object.getString("drop_lat");
            this.drop_lng = object.getString("drop_lng");
            this.transport_type = object.getString("transport_type");
            this.material = object.getString("material");
            this.number_of_ton = object.getString("number_of_ton");
            this.expected_price = object.getString("expected_price");
            this.fixed_per_tone = object.getString("fixed_per_tone");
            this.payment_mode = object.getString("payment_mode");
            this.volumetric_weight = object.getString("volumetric_weight");
            this.length = object.getString("length");
            this.breadth = object.getString("breadth");
            this.height = object.getString("height");
            this.expected_weight = object.getString("expected_weight");
            this.remark = object.getString("remark");
            this.is_lablor_required = object.getString("is_lablor_required");
            this.no_of_labour = object.getString("no_of_labour");
            this.required_date = object.getString("required_date");
            this.is_active = object.getString("is_active");
            this.pickup_loaction_name = object.getString("pickup_loaction_name");
            this.drop_loaction_name = object.getString("drop_loaction_name");
            this.created_at = object.getString("created_at");
            this.no_of_bid = object.getString("no_of_bid");
            this.is_accept = object.getString("is_bid_accept");
            this.ride_status = object.getString("ride_status");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Load(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        loader_type = in.readString();
        pickup_lat = in.readString();
        pickup_lng = in.readString();
        pickup_loaction = in.readString();
        drop_loaction = in.readString();
        drop_lat = in.readString();
        drop_lng = in.readString();
        transport_type = in.readString();
        material = in.readString();
        number_of_ton = in.readString();
        expected_price = in.readString();
        fixed_per_tone = in.readString();
        payment_mode = in.readString();
        volumetric_weight = in.readString();
        length = in.readString();
        breadth = in.readString();
        height = in.readString();
        expected_weight = in.readString();
        remark = in.readString();
        is_lablor_required = in.readString();
        no_of_labour = in.readString();
        required_date = in.readString();
        is_active = in.readString();
        pickup_loaction_name = in.readString();
        drop_loaction_name = in.readString();
        created_at = in.readString();
        no_of_bid = in.readString();
        is_accept = in.readString();
        ride_status = in.readString();
    }

    public static final Creator<Load> CREATOR = new Creator<Load>() {
        @Override
        public Load createFromParcel(Parcel in) {
            return new Load(in);
        }

        @Override
        public Load[] newArray(int size) {
            return new Load[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(user_id);
        parcel.writeString(loader_type);
        parcel.writeString(pickup_lat);
        parcel.writeString(pickup_lng);
        parcel.writeString(pickup_loaction);
        parcel.writeString(drop_loaction);
        parcel.writeString(drop_lat);
        parcel.writeString(drop_lng);
        parcel.writeString(transport_type);
        parcel.writeString(material);
        parcel.writeString(number_of_ton);
        parcel.writeString(expected_price);
        parcel.writeString(fixed_per_tone);
        parcel.writeString(payment_mode);
        parcel.writeString(volumetric_weight);
        parcel.writeString(length);
        parcel.writeString(breadth);
        parcel.writeString(height);
        parcel.writeString(expected_weight);
        parcel.writeString(remark);
        parcel.writeString(is_lablor_required);
        parcel.writeString(no_of_labour);
        parcel.writeString(required_date);
        parcel.writeString(is_active);
        parcel.writeString(pickup_loaction_name);
        parcel.writeString(drop_loaction_name);
        parcel.writeString(created_at);
        parcel.writeString(no_of_bid);
        parcel.writeString(is_accept);
        parcel.writeString(ride_status);
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

    public String getLoader_type() {
        return loader_type;
    }

    public void setLoader_type(String loader_type) {
        this.loader_type = loader_type;
    }

    public String getPickup_lat() {
        return pickup_lat;
    }

    public void setPickup_lat(String pickup_lat) {
        this.pickup_lat = pickup_lat;
    }

    public String getPickup_lng() {
        return pickup_lng;
    }

    public void setPickup_lng(String pickup_lng) {
        this.pickup_lng = pickup_lng;
    }

    public String getPickup_loaction() {
        return pickup_loaction;
    }

    public void setPickup_loaction(String pickup_loaction) {
        this.pickup_loaction = pickup_loaction;
    }

    public String getDrop_loaction() {
        return drop_loaction;
    }

    public void setDrop_loaction(String drop_loaction) {
        this.drop_loaction = drop_loaction;
    }

    public String getDrop_lat() {
        return drop_lat;
    }

    public void setDrop_lat(String drop_lat) {
        this.drop_lat = drop_lat;
    }

    public String getDrop_lng() {
        return drop_lng;
    }

    public void setDrop_lng(String drop_lng) {
        this.drop_lng = drop_lng;
    }

    public String getTransport_type() {
        return transport_type;
    }

    public void setTransport_type(String transport_type) {
        this.transport_type = transport_type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getNumber_of_ton() {
        return number_of_ton;
    }

    public void setNumber_of_ton(String number_of_ton) {
        this.number_of_ton = number_of_ton;
    }

    public String getExpected_price() {
        return expected_price;
    }

    public void setExpected_price(String expected_price) {
        this.expected_price = expected_price;
    }

    public String getFixed_per_tone() {
        return fixed_per_tone;
    }

    public void setFixed_per_tone(String fixed_per_tone) {
        this.fixed_per_tone = fixed_per_tone;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getVolumetric_weight() {
        return volumetric_weight;
    }

    public void setVolumetric_weight(String volumetric_weight) {
        this.volumetric_weight = volumetric_weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBreadth() {
        return breadth;
    }

    public void setBreadth(String breadth) {
        this.breadth = breadth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getExpected_weight() {
        return expected_weight;
    }

    public void setExpected_weight(String expected_weight) {
        this.expected_weight = expected_weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIs_lablor_required() {
        return is_lablor_required;
    }

    public void setIs_lablor_required(String is_lablor_required) {
        this.is_lablor_required = is_lablor_required;
    }

    public String getNo_of_labour() {
        return no_of_labour;
    }

    public void setNo_of_labour(String no_of_labour) {
        this.no_of_labour = no_of_labour;
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

    public String getPickup_loaction_name() {
        return pickup_loaction_name;
    }

    public void setPickup_loaction_name(String pickup_loaction_name) {
        this.pickup_loaction_name = pickup_loaction_name;
    }

    public String getDrop_loaction_name() {
        return drop_loaction_name;
    }

    public void setDrop_loaction_name(String drop_loaction_name) {
        this.drop_loaction_name = drop_loaction_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getNo_of_bid() {
        return no_of_bid;
    }

    public void setNo_of_bid(String no_of_bid) {
        this.no_of_bid = no_of_bid;
    }

    public String getIs_accept() {
        return is_accept;
    }

    public void setIs_accept(String is_accept) {
        this.is_accept = is_accept;
    }

    public String getRide_status() {
        return ride_status;
    }

    public void setRide_status(String ride_status) {
        this.ride_status = ride_status;
    }
}
