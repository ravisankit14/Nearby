package com.nearby.restapi.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ankit on 26/04/18.
 */

public class Location {

    @SerializedName("lat")
    public String lat ;

    @SerializedName("lng")
    public String lng ;
}
