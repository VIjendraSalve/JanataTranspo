package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Taluka implements Parcelable {

    public String id;
    public String taluka_name;
    public String city_id;

    public Taluka(String id, String taluka_name, String city_id) {
        this.id = id;
        this.taluka_name = taluka_name;
        this.city_id = city_id;
    }

    public Taluka(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.taluka_name = object.getString("taluka_name");
            this.city_id = object.getString("city_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Taluka(Parcel in) {
        id = in.readString();
        taluka_name = in.readString();
        city_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taluka_name);
        dest.writeString(city_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Taluka> CREATOR = new Creator<Taluka>() {
        @Override
        public Taluka createFromParcel(Parcel in) {
            return new Taluka(in);
        }

        @Override
        public Taluka[] newArray(int size) {
            return new Taluka[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaluka_name() {
        return taluka_name;
    }

    public void setTaluka_name(String taluka_name) {
        this.taluka_name = taluka_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    @Override
    public String toString() {
        return  taluka_name;
    }
}
