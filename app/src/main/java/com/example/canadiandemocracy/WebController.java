package com.example.canadiandemocracy;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.canadiandemocracy.LegislatureObjectsSet.Legislature;
import com.example.canadiandemocracy.LegislatureObjectsSet.LegislatureObject;
import com.example.canadiandemocracy.RepresentativeObjectsSet.Rep;
import com.example.canadiandemocracy.RepresentativeObjectsSet.RepObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Method handles downloading of relevant information from OpenNorth server.
 */
public class WebController {

    // Member variables
    static final String BASE_URL = "http://represent.opennorth.ca";
    private List<Rep> mRepList;
    private List<Legislature> mLegislatureList = new ArrayList<>();
    static final String ERROR_MSG = "Error: check your network connection";

    /**
     * Method gets List of all legislatures from OpenNorthAPI and
     * stores in mLegislature variable.
     */
    public void downloadAllLegislatures(final Context context, final OnDownloadLegislatureListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenNorthAPI api = retrofit.create(OpenNorthAPI.class);
        Call<LegislatureObject> call = api.getAllLegislatureObjects();
        call.enqueue(new Callback<LegislatureObject>() {
            @Override
            public void onResponse(Call<LegislatureObject> call, Response<LegislatureObject> response) {
                if(!response.isSuccessful()){
                    String error_msg = "Conn.error: " + response.code();
                    Toast toast = Toast.makeText(context, error_msg, Toast.LENGTH_LONG);
                    toast.show();
                }
                Log.d(TAG, "onResponse: Server ResponseLegislasture: " + response.toString());
                mLegislatureList = response.body().getLegislatures();
                // passing a data to an activity whe downloading is complete
                listener.onDownloadLegislatureListener(mLegislatureList);

            }

            @Override
            public void onFailure(Call<LegislatureObject> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
                Toast toast = Toast.makeText(context, ERROR_MSG, Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        });
    }

    /**
     * Method gets list of all representatives from legislature (specified in passed url)
     * from OpenNorthAPI and stores in mRepList variable.
     */
    public void downloadAllReps(final Context context, final OnDownloadRepresentativeListener listener, String legislature_url) {
        String[] tokens = legislature_url.split("/");
        String param1 = tokens[1];
        String param2 = tokens[2];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenNorthAPI api = retrofit.create(OpenNorthAPI.class);
        Call<RepObject> call = api.getAllReps(param1, param2);

        call.enqueue(new Callback<RepObject>() {
            @Override
            public void onResponse(Call<RepObject> call, Response<RepObject> response) {
                Log.d(TAG, "onResponse: Server Response1: " + response.toString());
                if(!response.isSuccessful()){
                    String error_msg = "Conn.error: " + response.code();
                    Toast toast = Toast.makeText(context, error_msg, Toast.LENGTH_LONG);
                    toast.show();
                }
                mRepList = response.body().getReps();
                // passing a data to an activity whe downloading is complete
                listener.onDownloadRepListener(mRepList);
            }

            @Override
            public void onFailure(Call<RepObject> call, Throwable t) {
                Toast toast = Toast.makeText(context, ERROR_MSG, Toast.LENGTH_LONG);
                toast.show();
                mRepList = null;
            }
        });
    }
}
