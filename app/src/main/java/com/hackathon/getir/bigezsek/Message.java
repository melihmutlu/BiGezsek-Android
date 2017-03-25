package com.hackathon.getir.bigezsek;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by taha on 25/03/17.
 */

public class Message {

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("from")
    @Expose
    public String from;

}
