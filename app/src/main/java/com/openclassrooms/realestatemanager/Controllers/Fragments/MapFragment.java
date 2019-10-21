package com.openclassrooms.realestatemanager.Controllers.Fragments;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openclassrooms.realestatemanager.Controllers.Activities.MapActivity;
import com.openclassrooms.realestatemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements    OnMapReadyCallback,
                                                        GoogleMap.OnInfoWindowClickListener {

    // For Debug
    private static final String TAG = MapFragment.class.getSimpleName();

    // For add Google Map in Fragment
    private SupportMapFragment mMapFragment;

    // ==> For use Api Google Play Service : map
    private GoogleMap mGoogleMap;

    // ==> For update UI Location
    private static final float DEFAULT_ZOOM = 13f;

    //==> For use Api Google Play Service : Location
    // Parameter for the construction of the fragment
    private static final String KEY_LOCATION = "KEY__LOCATION";
    private Location mLocation;

    public MapFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                               FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static MapFragment newInstance(Location location) {
        Log.d(TAG, "newInstance() called with: location = [" + location + "]");

        // Create new fragment
        MapFragment mapFragment = new MapFragment();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putParcelable(KEY_LOCATION, location);

        mapFragment.setArguments(args);

        return mapFragment;
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        // Get data from Bundle (created in method newInstance)
        mLocation = getArguments().getParcelable(KEY_LOCATION);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // Configure the Maps Service of Google
        configurePlayServiceMaps();

        return rootView;
    }
    // ---------------------------------------------------------------------------------------------
    //                            GOOGLE PLAY SERVICE : MAPS
    // ---------------------------------------------------------------------------------------------
    public void configurePlayServiceMaps() {
        Log.d(TAG, "configurePlayServiceMaps: ");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(this);
        }
        // Build the map
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_map, mMapFragment).commit();
    }
    /**
     * Manipulates the map when it's available
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: ");
        mGoogleMap = googleMap;

        // Disable 3D Building
        mGoogleMap.setBuildingsEnabled(false);

        // Activate OnMarkerClickListener
        mGoogleMap.setOnInfoWindowClickListener(this);

        // Show current Location
        showCurrentLocation();

        // Display Restaurants Markers and activate Listen on the participants number
        //displayAndListensMarkers();
    }
    // ---------------------------------------------------------------------------------------------
    //                                       METHODS
    // ---------------------------------------------------------------------------------------------
    /**
     * Method that places the map on a current location
     */
    private void showCurrentLocation() {
        Log.d(TAG, "showCurrentLocation: ");
        if (mLocation != null) {
            Log.d(TAG, "showCurrentLocation: mLocation.getLatitude()  = " + mLocation.getLatitude());
            Log.d(TAG, "showCurrentLocation: mLocation.getLongitude() = " + mLocation.getLongitude());
        }
        //LatLng mDefaultLocation = new LatLng(48.844304, 2.374377);
        //mLocation = new Location("");
        //mLocation.setLatitude(mDefaultLocation.latitude);
        //mLocation.setLongitude(mDefaultLocation.longitude);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng( mLocation.getLatitude(),
                            mLocation.getLongitude()), DEFAULT_ZOOM));

        // Update Location UI
        //updateLocationUI();
    }
    // ---------------------------------------------------------------------------------------------
    //                                       ACTIONS
    // ---------------------------------------------------------------------------------------------
    // Click on Restaurants Map Marker
    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: ");

        //Launch Restaurant Card Activity with restaurantIdentifier
        //Toolbox.startActivity(getActivity(),RestaurantCardActivity.class,
        //        RestaurantCardActivity.KEY_DETAILS_RESTAURANT_CARD,
        //        marker.getTag().toString());
    }
}
