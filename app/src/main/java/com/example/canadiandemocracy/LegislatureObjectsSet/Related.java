package com.example.canadiandemocracy.LegislatureObjectsSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Constructor that helps to unwrap single legislature
 * objects from multi-level json data
 */
public class Related {

    @SerializedName("representatives_url")
    @Expose
    private String representatives_url;

    public String getRepresentatives_url() {
        return representatives_url;
    }

}
