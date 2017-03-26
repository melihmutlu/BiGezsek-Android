package com.hackathon.getir.bigezsek;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mlih on 26/03/17.
 */

public class Place{

    @SerializedName("geometry.lat")
    @Expose
    private double lat;

    @SerializedName("geometry.lng")
    @Expose
    private double lng;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;


    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
