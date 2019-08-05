package com.openclassrooms.realestatemanager.PropertyList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.fragment_list_property_location) TextView mLocation;
    @BindView(R.id.fragment_list_property_type) TextView mType;
    @BindView(R.id.fragment_list_property_prize) TextView mPrize;

    // For data
    private WeakReference<PropertyAdapter.Listener> callbackWeakRef;

    // Constructor
    public PropertyViewHolder(View propertyView) {
        super(propertyView);
        ButterKnife.bind(this, propertyView);
    }

    // Method to update the current item
    public void updateWithProperty(Property property, PropertyAdapter.Listener callback){
        this.callbackWeakRef = new WeakReference<>(callback);
        this.mLocation.setText(property.getType());
    }

    @Override
    public void onClick(View view) {
        PropertyAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
