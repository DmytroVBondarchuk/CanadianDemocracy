package com.example.canadiandemocracy;

import com.example.canadiandemocracy.LegislatureObjectsSet.Legislature;

import java.util.List;

public interface OnDownloadLegislatureListener {

    /**
     *  Facilitates inserting data into suggestion box when downloading is complete
     * @param legislatureList
     */
    void onDownloadLegislatureListener (List<Legislature> legislatureList);

}
