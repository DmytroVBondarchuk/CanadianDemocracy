package com.example.canadiandemocracy.RepresentativeObjectsSet;

import com.example.canadiandemocracy.LegislatureObjectsSet.Legislature;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Constructor that helps to unwrap single representative
 * objects from multi-level json data
 */
public class RepObject {

    @SerializedName("objects")
    @Expose
    private ArrayList<Rep> reps;

    public ArrayList<Rep> getReps() {
        return reps;
    }

    @Override
    public String toString() {
        return "RepObject{" +
                "reps=" + reps +
                '}';
    }
}
