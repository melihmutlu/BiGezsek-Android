package com.hackathon.getir.bigezsek;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mlih on 26/03/17.
 */

public class Place{

    @SerializedName("geometry")
    @Expose
    private String geometry;

    @SerializedName("id")
    @Expose
    private String id;

    public String getGeometry() {
        return geometry;
    }

    public String getId() {
        return id;
    }
}
