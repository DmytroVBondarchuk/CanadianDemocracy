package com.example.canadiandemocracy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canadiandemocracy.LegislatureObjectsSet.Legislature;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements OnDownloadLegislatureListener {

    // Member variables
    AutoCompleteTextView suggestion_box;
    ArrayList<String> rep_sets = new ArrayList<>();
    ArrayList<String> rep_sets_urls = new ArrayList<>();
    List<Legislature> mLegislatureList;
    static final String ERROR_MSG = "Error: check your network connection";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rep_sets.add("Loading...");
        Log.d(TAG, "onResponse: Server : " + rep_sets.size());
        //attaching assembly list to suggestion box
        suggestion_box = (AutoCompleteTextView)findViewById(R.id.suggestion_box);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, rep_sets);
        suggestion_box.setAdapter(adapter);

        // right-away attempt to populate suggestion box with list of legislatures.
        populateSuggestionBox();
        // Text change listener to ensure the suggestion box is populated in case if newtwork error
        // at first attempt.
        suggestion_box.addTextChangedListener(new TextWatcher() {
                                                  @Override
                                                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                  }

                                                  @Override
                                                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                      ConnectivityManager cm =
                                                              (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                                                      NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                                      boolean isConnected = activeNetwork != null &&
                                                              activeNetwork.isConnectedOrConnecting();

                                                      if(!isConnected){
                                                          Toast toast = Toast.makeText(getApplicationContext(), ERROR_MSG, Toast.LENGTH_LONG);
                                                          toast.show();
                                                      }
                                                      else if(rep_sets.size() == 1){
                                                          populateSuggestionBox();
                                                      }
                                                      else {
                                                          return;
                                                      }
                                                  }

                                                  @Override
                                                  public void afterTextChanged(Editable s) {

                                                  }
        });
    }

    /**
     * This method makes async network call and populates rep_sets
     * and with relevant data.
     */
    private void populateSuggestionBox() {
        WebController controller = new WebController();
        controller.downloadAllLegislatures(this, this);
    }

    /**
     * This method opens ListOfRepresentatives activity to display members of
     * selected set of representatives.
     * @param view
     */
    public void showSetOfReps(View view) {
        if(rep_sets.size() <= 1){
            Toast toast = Toast.makeText(this, ERROR_MSG, Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            int selected_set_id = rep_sets.indexOf(suggestion_box.getText().toString());
            if(selected_set_id == -1){
                Toast toast = Toast.makeText(this, "There is no such a legislature body", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            String selected_set = rep_sets_urls.get(selected_set_id);

            Intent detailIntent = new Intent(this, ListOfRepresentatives.class);
            detailIntent.putExtra("rep_set_name", suggestion_box.getText().toString());
            detailIntent.putExtra("rep_set_url", selected_set);
            this.startActivity(detailIntent);
        }
    }

    /**
     * Listener method that fires up when downloading of legislature list is complete.
     * @param legislatureList
     */
    @Override
    public void onDownloadLegislatureListener (List<Legislature> legislatureList){
        mLegislatureList = legislatureList;
        rep_sets.clear();

        for(Legislature legislature: mLegislatureList){
            rep_sets.add(legislature.getLegislature_name());
            rep_sets_urls.add(legislature.getLegislature_url().getRepresentatives_url());
        }
        //attaching assembly list to suggestion box
        suggestion_box = (AutoCompleteTextView)findViewById(R.id.suggestion_box);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, rep_sets);
        suggestion_box.setAdapter(adapter);
    }
}
