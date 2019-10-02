package com.openclassrooms.realestatemanager.Controllers.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Models.views.EstateDetailsViewModel;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michaël SAGOT on 23/07/2019.
 */
public class EstateDetailsFragment extends Fragment {

    // For debugging Mode
    private static final String TAG = EstateDetailsFragment.class.getSimpleName();

    // Declare EstateDetailsViewModel
    private EstateDetailsViewModel mEstateDetailsViewModel;

    // For Design
    @BindView(R.id.fragment_details_description_text) TextView mDescription;
    @BindView(R.id.fragment_details_surface_value) TextView mSurface;
    @BindView(R.id.fragment_details_rooms_value) TextView mRoomsNumber;
    @BindView(R.id.fragment_details_bathrooms_value) TextView mBathroomsNumber;
    @BindView(R.id.fragment_details_bedrooms_value) TextView mBedroomsNumber;
    @BindView(R.id.fragment_details_location_address_line_1) TextView mLocation_1;
    @BindView(R.id.fragment_details_location_address_line_2) TextView mLocation_2;
    @BindView(R.id.fragment_details_location_address_line_3) TextView mLocation_3;
    @BindView(R.id.fragment_details_location_address_line_4) TextView mLocation_4;
    @BindView(R.id.fragment_details_location_address_line_5) TextView mLocation_5;
    @BindView(R.id.fragment_details_static_map) ImageView mStaticMap;

    public EstateDetailsFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                                  FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static EstateDetailsFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        EstateDetailsFragment fragment = new EstateDetailsFragment();

        return fragment;
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estate_details, container, false);
        ButterKnife.bind(this, view);

        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        mEstateDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EstateDetailsViewModel.class);

        // Get and Observe Current Estate
        this.getCurrentEstate();

        return view;
    }
    // ---------------------------------------------------------------------------------------------
    //                                           DATA
    // ---------------------------------------------------------------------------------------------
    // Get Estate selected in EstateList
    private void getCurrentEstate() {
        Log.d(TAG, "getCurrentEstate: ");
        mEstateDetailsViewModel.getCurrentEstate().observe(this, this::updateUI);
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    public void updateUI(Estate estate){
        Log.d(TAG, "updateUI() called with: estate = [" + estate + "]");

        if (estate != null) {
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

            // Created Uri to recover the static map
            Uri.Builder uriStaticMap
                    = Uri.parse("https://maps.googleapis.com/maps/api/staticmap").buildUpon();
            // Add options
            uriStaticMap
                    .appendQueryParameter("size","300x300")
                    .appendQueryParameter("scale","2")
                    .appendQueryParameter("zoom","16")
                    .appendQueryParameter("key",BuildConfig.google_static_map_api);
            String markers = "size:mid|color:blue|label:E|"
                    + estate.getAddress().get(0)+"+"
                    + estate.getAddress().get(1)+"+"
                    + estate.getAddress().get(2)+"+"
                    + estate.getAddress().get(3)+"+"
                    + estate.getAddress().get(4);
            uriStaticMap.appendQueryParameter("markers",markers);
            // Display static Map depending on the address of the property
            Glide.with(this)
                    .load(uriStaticMap.build())
                    .apply(RequestOptions.centerCropTransform())
                    .into(mStaticMap);
        }
    }
}
