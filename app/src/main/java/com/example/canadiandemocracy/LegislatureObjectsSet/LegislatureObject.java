package com.example.canadiandemocracy.LegislatureObjectsSet;

import com.example.canadiandemocracy.LegislatureObjectsSet.Legislature;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
/**
 * Constructor that helps to unwrap single legislature
 * objects from multi-level json data
 */
public class LegislatureObject {


    @SerializedName("objects")
    @Expose
    private ArrayList<Legislature> legislatures;

    @Override
    public String toString() {
        return "LegislatureObject{" +
                "legislatures=" + legislatures +
                '}';
    }

    public ArrayList<Legislature> getLegislatures() {
        return legislatures;
    }

}
