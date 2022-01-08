package com.wht.janatatraspo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class VehicleType implements Parcelable {

    private String type;
    private String image;
    public boolean Selected = false;

    public VehicleType(JSONObject object) {

        try {
            this.type = object.getString("type");
            this.image = object.getString("image");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected VehicleType(Parcel in) {
        type = in.readString();
        image = in.readString();
        Selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(image);
        dest.writeByte((byte) (Selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VehicleType> CREATOR = new Creator<VehicleType>() {
        @Override
        public VehicleType createFromParcel(Parcel in) {
            return new VehicleType(in);
        }

        @Override
        public VehicleType[] newArray(int size) {
            return new VehicleType[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
