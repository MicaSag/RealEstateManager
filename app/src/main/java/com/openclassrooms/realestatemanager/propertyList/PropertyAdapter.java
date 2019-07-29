package com.openclassrooms.realestatemanager.propertyList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyViewHolder> {

    // CALLBACK
    public interface Listener { void onClickDeleteButton(int position); }
    private final Listener mCallback;

    // FOR DATA
    private List<Property> mListProperty;

    // CONSTRUCTOR
    public PropertyAdapter(List<Property> listProperty, Listener callback) {
        mListProperty = listProperty;
        mCallback = callback;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_property, parent, false);

        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder viewHolder, int position) {
        viewHolder.updateWithProperty(mListProperty.get(position), mCallback);
    }

    @Override
    public int getItemCount() {
        return mListProperty.size();
    }

    public Property getProperty(int position){
        return mListProperty.get(position);
    }

    public void updateData(List<Property> property){
        this.mListProperty = property;
        this.notifyDataSetChanged();
    }
}
