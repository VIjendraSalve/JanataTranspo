package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Bid implements Parcelable {
    private String bid_id;
    private String id;
    private String user_id;
    private String loader_type;
    private String loader_id;
    private String is_bider_type;
    private String is_negotialable;
    private String bid_amount;
    private String labour_charge_per_person;
    private String waiting_chagres;
    private String allownces;
    private String is_accept;
    private String first_name;
    private String last_name;
    private String image;
    private String business_name;
    private String created_at;
    private String mobile_no;
    private String volumetric_weight;
    private String transport_type;
    private String material;
    private String number_of_ton;
    private String expected_price;
    private String fixed_per_tone;
    private String payment_mode;






    public Bid(JSONObject object) {
        try {
            this.bid_id = object.getString("bid_id");
            this.id = object.getString("id");
            this.user_id = object.getString("user_id");
            this.loader_id = object.getString("loader_id");
            this.is_bider_type = object.getString("is_bider_type");
            this.is_negotialable = object.getString("is_negotialable");
            this.bid_amount = object.getString("bid_amount");
            this.labour_charge_per_person = object.getString("labour_charge_per_person");
            this.waiting_chagres = object.getString("waiting_chagres");
            this.allownces = object.getString("allownces");
            this.is_accept = object.getString("is_accept");
            this.first_name = object.getString("first_name");
            this.last_name = object.getString("last_name");
            this.image = object.getString("image");
            this.business_name = object.getString("business_name");
            this.created_at = object.getString("created_at");
            this.mobile_no = object.getString("mobile_no");

            this.transport_type = object.getString("transport_type");
            this.material = object.getString("material");
            this.number_of_ton = object.getString("number_of_ton");
            this.expected_price = object.getString("expected_price");
            this.fixed_per_tone = object.getString("fixed_per_tone");
            this.payment_mode = object.getString("payment_mode");
            this.volumetric_weight = object.getString("volumetric_weight");
            this.loader_type = object.getString("loader_type");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Bid(Parcel in) {
        bid_id = in.readString();
        id = in.readString();
        user_id = in.readString();
        loader_type = in.readString();
        loader_id = in.readString();
        is_bider_type = in.readString();
        is_negotialable = in.readString();
        bid_amount = in.readString();
        labour_charge_per_person = in.readString();
        waiting_chagres = in.readString();
        allownces = in.readString();
        is_accept = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        image = in.readString();
        business_name = in.readString();
        created_at = in.readString();
        mobile_no = in.readString();
        volumetric_weight = in.readString();
        transport_type = in.readString();
        material = in.readString();
        number_of_ton = in.readString();
        expected_price = in.readString();
        fixed_per_tone = in.readString();
        payment_mode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bid_id);
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(loader_type);
        dest.writeString(loader_id);
        dest.writeString(is_bider_type);
        dest.writeString(is_negotialable);
        dest.writeString(bid_amount);
        dest.writeString(labour_charge_per_person);
        dest.writeString(waiting_chagres);
        dest.writeString(allownces);
        dest.writeString(is_accept);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(image);
        dest.writeString(business_name);
        dest.writeString(created_at);
        dest.writeString(mobile_no);
        dest.writeString(volumetric_weight);
        dest.writeString(transport_type);
        dest.writeString(material);
        dest.writeString(number_of_ton);
        dest.writeString(expected_price);
        dest.writeString(fixed_per_tone);
        dest.writeString(payment_mode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bid> CREATOR = new Creator<Bid>() {
        @Override
        public Bid createFromParcel(Parcel in) {
            return new Bid(in);
        }

        @Override
        public Bid[] newArray(int size) {
            return new Bid[size];
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

    public String getLoader_id() {
        return loader_id;
    }

    public void setLoader_id(String loader_id) {
        this.loader_id = loader_id;
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

    public String getLabour_charge_per_person() {
        return labour_charge_per_person;
    }

    public void setLabour_charge_per_person(String labour_charge_per_person) {
        this.labour_charge_per_person = labour_charge_per_person;
    }

    public String getWaiting_chagres() {
        return waiting_chagres;
    }

    public void setWaiting_chagres(String waiting_chagres) {
        this.waiting_chagres = waiting_chagres;
    }

    public String getAllownces() {
        return allownces;
    }

    public void setAllownces(String allownces) {
        this.allownces = allownces;
    }

    public String getIs_accept() {
        return is_accept;
    }

    public void setIs_accept(String is_accept) {
        this.is_accept = is_accept;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
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

    public String getLoader_type() {
        return loader_type;
    }

    public void setLoader_type(String loader_type) {
        this.loader_type = loader_type;
    }
}
