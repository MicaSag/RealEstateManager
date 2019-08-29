package com.openclassrooms.realestatemanager.Controllers.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Controllers.Activities.RealEstateManagerActivity;
import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 23/07/2019.
 */
public class DetailFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = DetailFragment.class.getSimpleName();

    // For Design
    @BindView(R.id.fragment_detail_text_description) TextView mDescription;
    @BindView(R.id.fragment_detail_text_surface) TextView mSurface;
    @BindView(R.id.fragment_detail_text_rooms_number) TextView mRoomsNumber;
    @BindView(R.id.fragment_detail_text_bathrooms_number) TextView mBathroomsNumber;
    @BindView(R.id.fragment_detail_text_bedrooms_number) TextView mBedroomsNumber;
    @BindView(R.id.fragment_detail_text_location_1) TextView mLocation_1;
    @BindView(R.id.fragment_detail_text_location_2) TextView mLocation_2;
    @BindView(R.id.fragment_detail_text_location_3) TextView mLocation_3;
    @BindView(R.id.fragment_detail_text_location_4) TextView mLocation_4;
    @BindView(R.id.fragment_detail_text_location_5) TextView mLocation_5;

    public DetailFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static DetailFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        return new DetailFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        // Call during UI creation
        //this.configureRecyclerView();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(Property property){

        mDescription.setText(property.getDescription());
        mSurface.setText(property.getArea().toString());
        mRoomsNumber.setText(property.getNumberOfParts().toString());
        mBathroomsNumber.setText(property.getNumberOfBathrooms().toString());
        mBedroomsNumber.setText(property.getNumberOfBedrooms().toString());
        mLocation_1.setText(property.getAddress().get(0));
        mLocation_2.setText(property.getAddress().get(1));
        mLocation_3.setText(property.getAddress().get(2));
        mLocation_4.setText(property.getAddress().get(3));
        mLocation_5.setText(property.getAddress().get(4));
    }
}
