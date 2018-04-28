package com.nearby.restapi.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by ankit on 01/02/18.
 */

public class Result {

    @SerializedName("geometry")
    public Geometry geometry ;

    public Geometry getGeometry() {
        return geometry;
    }

    @SerializedName("formatted_address")
    public String formatted_address;

    @SerializedName("name")
    public String name;

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
