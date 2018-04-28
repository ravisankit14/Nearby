package com.nearby.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ankit on 25/04/18.
 */

public class Nearby implements Parcelable{

    private String landmark;

    private String lat;
    private String lng;

    private int sDist;

    public Nearby(String landmark, String lat, String lng){
        this.landmark = landmark;
        this.lat = lat;
        this.lng = lng;
    }

    public Nearby(String landmark, String lat, String lng, int sDist){
        this.landmark = landmark;
        this.lat = lat;
        this.lng = lng;
        this.sDist = sDist;
    }

    protected Nearby(Parcel in) {
        landmark = in.readString();
        lat = in.readString();
        lng = in.readString();
        sDist = Integer.parseInt(in.readString());
    }

    public static final Creator<Nearby> CREATOR = new Creator<Nearby>() {
        @Override
        public Nearby createFromParcel(Parcel in) {
            return new Nearby(in);
        }

        @Override
        public Nearby[] newArray(int size) {
            return new Nearby[size];
        }
    };

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getsDist() {
        return sDist;
    }

    public void setsDist(int sDist) {
        this.sDist = sDist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(landmark);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(String.valueOf(sDist));
    }
}
