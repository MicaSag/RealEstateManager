package com.openclassrooms.realestatemanager.Controllers.Fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.EstateList.EstateListViewModel;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 23/07/2019.
 */
public class EstateDetailsFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = EstateDetailsFragment.class.getSimpleName();

    // Declare EstateListViewModel
    private EstateListViewModel mEstateListViewModel;

    // Static variables for intent parameters
    public static final String BUNDLE_REAL_ESTATE_AGENT_ID = "AGENT_ID";
    public static final String BUNDLE_PROPERTY_ID = "PROPERTY_ID";

    //For Data
    private Long mRealEstateAgent_Id;
    private Long mProperty_Id;

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

    public EstateDetailsFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static EstateDetailsFragment newInstance(long realEstateAgent_Id, long estate_Id) {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        EstateDetailsFragment fragment = new EstateDetailsFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        // Put data un Bundle
        args.putLong(BUNDLE_REAL_ESTATE_AGENT_ID, realEstateAgent_Id);
        args.putLong(BUNDLE_PROPERTY_ID, estate_Id);
        fragment.setArguments(args);

        return fragment;
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estate_details, container, false);
        ButterKnife.bind(this, view);

        // Get data from Bundle (created in method newInstance)
        mRealEstateAgent_Id = getArguments().getLong(BUNDLE_REAL_ESTATE_AGENT_ID, 0);
        mProperty_Id = getArguments().getLong(BUNDLE_PROPERTY_ID, 0);

        ViewModelFactory mPropertyViewModelFactory = Injection.provideViewModelFactory(getContext());
        mEstateListViewModel = ViewModelProviders.of(this, mPropertyViewModelFactory).get(EstateListViewModel.class);

        int num = 1;
        // Call during UI creation
        //mEstateListViewModel.getEstate(num).observe(this, this::updateUI);

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                           DATA
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(Estate estate){

        mDescription.setText(estate.getDescription());
        mSurface.setText(estate.getArea().toString());
        mRoomsNumber.setText(estate.getNumberOfParts().toString());
        mBathroomsNumber.setText(estate.getNumberOfBathrooms().toString());
        mBedroomsNumber.setText(estate.getNumberOfBedrooms().toString());
        mLocation_1.setText(estate.getAddress().get(0));
        mLocation_2.setText(estate.getAddress().get(1));
        mLocation_3.setText(estate.getAddress().get(2));
        mLocation_4.setText(estate.getAddress().get(3));
        mLocation_5.setText(estate.getAddress().get(4));
    }
}
