package com.openclassrooms.realestatemanager.PropertyList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

    // For Debug
    private static final String TAG = PropertyAdapter.class.getSimpleName();

    // Declaring a Glide object
    private RequestManager mGlide;

    // For Data
    private List<Property> mListProperty;

    // CALLBACK
    private final OnPropertyClick mCallback;

    // CONSTRUCTOR
    public PropertyAdapter(List<Property> listProperty, RequestManager glide, OnPropertyClick callback) {
        mListProperty = listProperty;
        mGlide = glide;
        mCallback = callback;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_property, parent, false);

        return new PropertyViewHolder(view);
    }

    // For update View Holder with Property
    @Override
    public void onBindViewHolder(PropertyViewHolder viewHolder, int position) {
        viewHolder.updateWithProperty(mListProperty.get(position), mGlide, mCallback);
    }

    // Return the size of the recycler view
    @Override
    public int getItemCount() {
        return mListProperty.size();
    }

    // Returns the Property Identifier of the current position
    public Property getProperty(int position){
        return mListProperty.get(position);
    }

    // Update le recycler view data
    public void updateData(List<Property> property){
        this.mListProperty = property;
        this.notifyDataSetChanged();
    }

    public interface OnPropertyClick{
        void onPropertyClick(Property property);
    }
}
