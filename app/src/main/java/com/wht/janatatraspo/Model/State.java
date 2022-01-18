package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class State implements Parcelable {

    public String id;
    public String name;
    public String country_id;
    private boolean isSelected;

    public State(String id, String city_name, String country_id) {
        this.id = id;
        this.name = city_name;
        this.country_id = country_id;
    }

    public State(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.name = object.getString("name");
            this.country_id = object.getString("country_id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected State(Parcel in) {
        id = in.readString();
        name = in.readString();
        country_id = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(country_id);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_name() {
        return name;
    }

    public void setCity_name(String city_name) {
        this.name = city_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return  name;
    }
}
