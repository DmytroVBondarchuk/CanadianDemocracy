package com.example.canadiandemocracy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.canadiandemocracy.RepresentativeObjectsSet.Rep;

import static android.content.ContentValues.TAG;

/**
 * An adapter class that inserts info into representative list item.
 */
public class RepsAdapter extends RecyclerView.Adapter<RepsAdapter.ViewHolder>{

    // Member variables.
    private List<Rep> mRepsData;
    private Context mContext;
    private ListOfRepresentatives mCurrentActivity;

    /**
     * Constructor that passes in the reps data and the context.
     *
     * @param repsData ArrayList containing the reps data.
     * @param context Context of the application.
     */
    RepsAdapter(Context context, ListOfRepresentatives currentActivity, List<Rep> repsData) {
        this.mRepsData = repsData;
        this.mContext = context;
        this.mCurrentActivity = currentActivity;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public RepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));

    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(RepsAdapter.ViewHolder holder,
                                 int position) {
        // Get current game.
        Rep currentRep = mRepsData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentRep);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mRepsData.size();
    }

    public ListOfRepresentatives getCurrentActivity(){
        return mCurrentActivity;
    }








    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mNameText;
        private TextView mPartyText;
        private TextView mLinkText;
        private ImageView mRepsImage;
        private String mLink;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mNameText = itemView.findViewById(R.id.reps_name);
            mPartyText = itemView.findViewById(R.id.reps_party);
            mLinkText = itemView.findViewById(R.id.reps_website);
            mRepsImage = itemView.findViewById(R.id.reps_picture);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        /**
         * Method binds specific representative to a separate list item.
         * Inserts an available link to by priority, if higher by priority link is missing.
         * @param currentRep
         */
        void bindTo(Rep currentRep){
            // Populate the textviews with data.
            mNameText.setText(currentRep.getRep_name());
            mPartyText.setText(currentRep.getParty());
            if(!currentRep.getPersonal_url().isEmpty()){
                mLink = currentRep.getPersonal_url();
                mLinkText.setText(mLink);
            }
            else if(!currentRep.getUrl().isEmpty()){
                mLink = currentRep.getUrl();
                mLinkText.setText(mLink);
            }
            else if(!currentRep.getSource_url().isEmpty()){
                mLink = currentRep.getSource_url();
                mLinkText.setText(mLink);
            }
            else{
                mLink = "link is missing";
                mLinkText.setText("www.No website available");
            }

            // coloring representatives according to a party affiliation
            ConstraintLayout root = itemView.findViewById(R.id.listItemLayout);
            if(currentRep.getParty().toLowerCase().contains("conservative")){
                root.setBackgroundResource(R.drawable.item_border_c);
            }
            else if(currentRep.getParty().toLowerCase().contains("liberal")){
                root.setBackgroundResource(R.drawable.item_border_l);
            }
            else if(currentRep.getParty().toLowerCase().contains("ndp") || currentRep.getParty().toLowerCase().contains("new democrat")){
                root.setBackgroundResource(R.drawable.item_border_n);
            }
            else {
                root.setBackgroundResource(R.drawable.item_border);
            }


            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentRep.getImg_url())
                    .error(R.drawable.default_politician)
                    .into(mRepsImage);
        }

        /**
         * Method opens rep's web page when button is clicked.
         * If url is absent will display message telling that this rep doesn't have a website.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            if(mLink.isEmpty()){
                Toast toast = Toast.makeText(context, R.string.missing_url_message, Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                // Parse the URI and create the intent.
                Uri webpage = Uri.parse(mLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Find an activity to hand the intent and start that activity.
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Log.d("ImplicitIntents", "Can't handle this intent!");
                }
            }
        }
    }
}

