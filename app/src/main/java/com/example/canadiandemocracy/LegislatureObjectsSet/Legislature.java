package com.example.canadiandemocracy.LegislatureObjectsSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Legislature {
    // Member variables
    @SerializedName("name")
    @Expose
    private String legislature_name;
    @SerializedName("related")
    @Expose
    private Related legislature_url;

    @Override
    public String toString() {
        return "Legislature{" +
                "legislature_name='" + legislature_name + '\'' +
                ", legislature_url=" + legislature_url +
                '}';
    }

    /**
     * Constructor for the Legislature data model.
     * @param legislature_name The name of the legislature.
     * @param legislature_url The link to legislatures website.
     */
    public Legislature(String legislature_name, Related legislature_url){
        this.legislature_name = legislature_name;
        this.legislature_url = legislature_url;
    }



    public String getLegislature_name() {
        return legislature_name;
    }

    public Related getLegislature_url() {
        return legislature_url;
    }
}
