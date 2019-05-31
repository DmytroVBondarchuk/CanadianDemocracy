package com.example.canadiandemocracy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.canadiandemocracy.RepresentativeObjectsSet.Rep;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 *  An activity to display list menbers of selected legislature.
 */
public class ListOfRepresentatives extends AppCompatActivity implements OnDownloadRepresentativeListener {

    // Member variables
    private RepsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Rep> mRepsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_representatives);

        // Setting up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("rep_set_name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onResponse: Server Response: toolbar: " + getIntent().getStringExtra("rep_set_name"));

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        int gridColumnCount = 1;

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new
                GridLayoutManager(this, gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        mRepsData = new ArrayList<>();


        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new RepsAdapter(this, ListOfRepresentatives.this, mRepsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();

    }

    /**
     * Initializes the reps data from OpenNorth API.
     */
    private void initializeData() {
        WebController controller = new WebController();
        controller.downloadAllReps(this, this, getIntent().getStringExtra("rep_set_url"));
    }

    /**
     * Listener method that fires up when downloading of representatives list is complete.
     * @param repList
     */
    @Override
    public void onDownloadRepListener(List<Rep> repList) {
        // Initialize the adapter and set it to the RecyclerView.
        mRepsData = repList;
        mAdapter = new RepsAdapter(this, ListOfRepresentatives.this, mRepsData);
        mRecyclerView.setAdapter(mAdapter);
    }
}
