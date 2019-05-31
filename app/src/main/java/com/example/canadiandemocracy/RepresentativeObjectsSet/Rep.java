package com.example.canadiandemocracy.RepresentativeObjectsSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rep {

    // Member variables
    @SerializedName("name")
    @Expose
    private String rep_name;
    @SerializedName("party_name")
    @Expose
    private String party;
    @SerializedName("personal_url")
    @Expose
    private String personal_url;
    @SerializedName("source_url")
    @Expose
    private String source_url;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("photo_url")
    @Expose
    private String img_url;

    /**
     * Constructor for the Rep data model.
     * @param rep_name The name of the representative.
     * @param party Party affiliation of the representative.
     * @param personal_url Personal website url of the representative. If rep doesn't
     *                             have a website, will contain legislature website link if available.
     * @param img_url A link to a resource that contains rep's image.
     */
    public Rep(String rep_name, String party, String personal_url, String source_url, String url, String img_url){
        this.rep_name = rep_name;
        this.party = party;
        this.personal_url = personal_url;
        this.source_url = source_url;
        this.url = url;
        this.img_url = img_url;
    }

    public String getRep_name() {
        return rep_name;
    }

    public String getParty() {
        return party;
    }

    public String getPersonal_url() {
        return personal_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public String getUrl() {
        return url;
    }

    public String getImg_url() {
        return img_url;
    }
}
