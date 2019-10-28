package com.openclassrooms.realestatemanager.Controllers.Fragments;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.Controllers.Activities.MapActivity;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Models.views.MapViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.CurrentEstateDataRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements    OnMapReadyCallback,
                                                        GoogleMap.OnInfoWindowClickListener {

    // For Debug
    private static final String TAG = MapFragment.class.getSimpleName();

    // Declare EstateListViewModel
    private MapViewModel mMapViewModel;

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


        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        mMapViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MapViewModel.class);

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

        // Display Estates Markers and activate Listen
        mMapViewModel.getCurrentEstates().observe(this, this::displayAndListensMarkers);
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
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng( mLocation.getLatitude(),
                            mLocation.getLongitude()), DEFAULT_ZOOM));
        // Declare a Marker for current position
        Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(mLocation.getLatitude(),
                        mLocation.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Current Position")
        );
    }
    /**
     * Creating estate markers on the map and activating for each of them a listener
     */
    public void displayAndListensMarkers(List<Estate> estates) {
        Log.d(TAG, "displayAndListensMarkers: ");
        //mGoogleMap.clear();
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        List<Address> list = new ArrayList<>();
        try {
            for (Estate estate : estates){
                // Get the coordinates of the address
                list = geocoder.getFromLocationName(estate.getAddress().get(0)+","+
                                                                estate.getAddress().get(1)+","+
                                                                estate.getAddress().get(2)+","+
                                                                estate.getAddress().get(3)+","+
                                                                estate.getAddress().get(4)
                                                    , 1);
                // Declare a Marker for current Estate
                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude()))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .title(estate.getAddress().get(0))
                );
                marker.setTag(estate.getEstate_Id());
            }
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
            ((MapActivity)getActivity()).showSnackBar("geoLocate: IOException: " + e.getMessage());
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                       ACTIONS
    // ---------------------------------------------------------------------------------------------
    // Click on Restaurants Map Marker
    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: ");

        Integer i = (Integer.parseInt(marker.getTag().toString()));
        Long estate_Id = i.longValue();
        Log.d(TAG, "onInfoWindowClick: estate_Id = "+estate_Id);

        // Change current Estate Id
        CurrentEstateDataRepository.getInstance().setCurrentEstate_Id(estate_Id);

        // Close Activity and go back to previous activity
        getActivity().finish();
    }
}
