package com.openclassrooms.realestatemanager.PropertyList;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = PropertyViewHolder.class.getSimpleName();

    @BindView(R.id.fragment_list_property_image) ImageView mImage;
    @BindView(R.id.fragment_list_property_location) TextView mLocation;
    @BindView(R.id.fragment_list_property_type) TextView mType;
    @BindView(R.id.fragment_list_property_prize) TextView mPrize;

    private PropertyAdapter.OnPropertyClick mOnPropertyClick;
    private Property mProperty;

    // Constructor
    public PropertyViewHolder(View propertyView) {
        super(propertyView);
        ButterKnife.bind(this, propertyView);
        propertyView.setOnClickListener(this);
    }

    // Method to update the current item
    public void updateWithProperty(Property property, RequestManager glide, PropertyAdapter.OnPropertyClick callback){
        mProperty = property;
        mOnPropertyClick = callback;

        // Display Property Photo
        if (property.getPhotos().get(0) != null) glide.load(property.getPhotos().get(0)).into(mImage);

        // Display Property Type
        mType.setText(property.getType());

        // Display Property location
        mLocation.setText(property.getAddress().get(5));

        // Display Property Price
        mPrize.setText("$"+Integer.toString(property.getPrice()));

        // For Debug
        Log.d(TAG, "updateWithProperty: RealEstateAgent_Id  = "+property.getRealEstateAgent_Id());
        Log.d(TAG, "updateWithProperty: Property_Id         = "+property.getProperty_Id());
        Log.d(TAG, "updateWithProperty: Property PhotoURL   = "+property.getPhotos().get(0));
        Log.d(TAG, "updateWithProperty: -------- END --------");
    }

    @Override
    public void onClick(View view) {
        if (mOnPropertyClick != null) mOnPropertyClick.onPropertyClick(mProperty);
    }
}
