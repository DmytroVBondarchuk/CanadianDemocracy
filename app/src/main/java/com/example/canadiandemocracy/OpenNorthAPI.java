package com.example.canadiandemocracy;

import com.example.canadiandemocracy.LegislatureObjectsSet.LegislatureObject;
import com.example.canadiandemocracy.RepresentativeObjectsSet.Rep;
import com.example.canadiandemocracy.RepresentativeObjectsSet.RepObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface OpenNorthAPI {

    /**
     * Interface to get list of legislatures from OpenNorth server
     * @return
     */
    @GET("/representative-sets/?limit=120")
    Call<LegislatureObject> getAllLegislatureObjects();

    /**
     * Interface to get list of representatives from OpenNorth server
     * @param param1
     * @param param2
     * @return
     */
    @GET("/{param1}/{param2}/?limit=400")
    Call<RepObject> getAllReps(@Path("param1") String param1, @Path("param2") String param2);
}
