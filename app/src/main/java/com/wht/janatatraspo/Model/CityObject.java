package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class CityObject implements Parcelable {

    public String id;
    public String city_name;
    public String state_id;
    private boolean isSelected;

    public CityObject(String id, String city_name, String state_id) {
        this.id = id;
        this.city_name = city_name;
        this.state_id = state_id;
    }

    public CityObject(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.city_name = object.getString("name");
            this.state_id = object.getString("state_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected CityObject(Parcel in) {
        id = in.readString();
        city_name = in.readString();
        state_id = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(city_name);
        dest.writeString(state_id);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CityObject> CREATOR = new Creator<CityObject>() {
        @Override
        public CityObject createFromParcel(Parcel in) {
            return new CityObject(in);
        }

        @Override
        public CityObject[] newArray(int size) {
            return new CityObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return city_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }
}
