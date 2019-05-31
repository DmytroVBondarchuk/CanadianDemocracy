package com.example.canadiandemocracy;

import com.example.canadiandemocracy.RepresentativeObjectsSet.Rep;

import java.util.List;

public interface OnDownloadRepresentativeListener {

    /**
     * Facilitates inserting data into list of representatives when downloading is complete
     * @param repList
     */
    void onDownloadRepListener (List<Rep> repList);

}
