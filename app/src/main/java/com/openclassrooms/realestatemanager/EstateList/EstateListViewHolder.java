package com.openclassrooms.realestatemanager.EstateList;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstateListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = EstateListViewHolder.class.getSimpleName();

    @BindView(R.id.fragment_list_estate_image) ImageView mImage;
    @BindView(R.id.fragment_list_estate_location) TextView mLocation;
    @BindView(R.id.fragment_list_estate_type) TextView mType;
    @BindView(R.id.fragment_list_estate_prize) TextView mPrize;

    private EstateListAdapter.OnEstateClick mOnEstateClick;
    private Estate mEstate;

    // Constructor
    public EstateListViewHolder(View estateView) {
        super(estateView);
        ButterKnife.bind(this, estateView);
        estateView.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithProperty(Estate estate, RequestManager glide, EstateListAdapter.OnEstateClick callback){
        mEstate = estate;
        mOnEstateClick = callback;

        // Display Estate Photo
        if (estate.getPhotos() !=null) if (estate.getPhotos().get(0) != null) glide.load(estate.getPhotos().get(0)).into(mImage);

        // Display Estate Type
        mType.setText(estate.getType());

        // Display Estate location
        if (estate.getAddress() != null) mLocation.setText(estate.getAddress().get(4));

        // Display Estate Price
        mPrize.setText("$"+Integer.toString(estate.getPrice()));

        // For Debug
        Log.d(TAG, "updateWithProperty: RealEstateAgent_Id  = "+ estate.getRealEstateAgent_Id());
        Log.d(TAG, "updateWithProperty: Estate_Id           = "+ estate.getEstate_Id());
        if (estate.getPhotos() != null ) Log.d(TAG, "updateWithProperty: Estate PhotoURL     = "+ estate.getPhotos().get(0));
        Log.d(TAG, "updateWithProperty: -------- END --------");
    }

    @Override
    public void onClick(View view) {
        if (mOnEstateClick != null) mOnEstateClick.onEstateClick(mEstate);
    }
}
