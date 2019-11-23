package com.openclassrooms.realestatemanager.controllers.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.activities.MapActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.views.MapViewModel;
import com.openclassrooms.realestatemanager.repositories.CurrentEstateDataRepository;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

    // ==> For update UI Location
    private static final float DEFAULT_ZOOM = 13f;

     // For use LOCATION permission
    // ---------------------------
    // 1 _ Request Code
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // For determinate Location
    // -------------------------
    // Default location if not permission granted ( Paris )
    private final LatLng mDefaultLocation = new LatLng(48.844304, 2.374377);

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public MapFragment() {
        // Required empty public constructor
    }
    // ---------------------------------------------------------------------------------------------
    //                               FRAGMENT INSTANTIATION
    // ---------------------------------------------------------------------------------------------
    public static MapFragment newInstance() {
        Log.d(TAG, "newInstance: ");

        // Create new fragment
        MapFragment mapFragment = new MapFragment();

        return mapFragment;
    }
    // ---------------------------------------------------------------------------------------------
    //                                    ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        // Configure ViewModel
        configureViewModel();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // Configure the Maps Service of Google
        configurePlayServiceMaps();

        return rootView;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel(){
        Log.d(TAG, "configureViewModel: ");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        mMapViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MapViewModel.class);

        // Observe a change of Current Location
        mMapViewModel.getCurrentLocation().observe(this,this::showCurrentLocation);
        // Observe a change of Current Estates
        mMapViewModel.getCurrentEstates().observe(this, this::displayAndListensMarkers);
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
        mMapViewModel.setGoogleMap(googleMap);

        // Disable 3D Building
        mMapViewModel.getGoogleMap().setBuildingsEnabled(false);

        // Activate OnMarkerClickListener
        mMapViewModel.getGoogleMap().setOnInfoWindowClickListener(this);

        // Get Location Permission
        getLocationPermission();
    }
    // ---------------------------------------------------------------------------------------------
    //                                      LOCATION
    // ---------------------------------------------------------------------------------------------
    /**
     * Controls location permission.
     * If they aren't allowed, prompts the user for permission to use the device location.
     * The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: ");

        // Check if permissions are already authorized
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permissions Granted
            // Get the last know location of the phone
            Log.d(TAG, "getLocationPermission: Permission already granted by User");
            getLastKnownCurrentLocationDevice();
        } else {
            Log.d(TAG, "getLocationPermission: Build.VERSION.SDK_INT = "+ Build.VERSION.SDK_INT);
            Log.d(TAG, "getLocationPermission: Build.VERSION_CODES.M = "+Build.VERSION_CODES.M);
            // Request for unnecessary permission before version Android 6.0 (API level 23)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                // Permissions not Granted
                Log.d(TAG, ">>-- Ask the user for Location Permission --<<");
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }
    /**
     * Method that processes the response to a request for permission made
     * by the function "requestPermissions(..)"
     */
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permissions Granted
                    // Get the last know location of the phone
                    Log.d(TAG, "onRequestPermissionsResult: Permission Granted by User :-)");
                    getLastKnownCurrentLocationDevice();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Permission not Granted by User :-(");
                    // The last know location will be the default position
                    Location lastKnownLocation = new Location("");
                    lastKnownLocation.setLatitude(mDefaultLocation.latitude);
                    lastKnownLocation.setLongitude(mDefaultLocation.longitude);

                    // Set last Know Location
                    mMapViewModel.setLastKnownLocation(lastKnownLocation);
                }
            }
        }
    }
    /**
     * Retrieves Coordinates of the best and most recent device location information if Exists
     */
    private void getLastKnownCurrentLocationDevice() {
        Log.d(TAG, "getLastKnownCurrentLocationDevice: ");
        // Construct a FusedLocationProviderClient
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            // Retrieves information if existing
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(getActivity(), task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    Location lastKnownLocation;
                    lastKnownLocation = task.getResult();
                    if (lastKnownLocation != null) {
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: getLastLocation EXIST");
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: lastKnownLocation.getLatitude()  = "
                                + lastKnownLocation.getLatitude());
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: lastKnownLocation.getLongitude() = "
                                + lastKnownLocation.getLongitude());
                    } else {
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: getLastLocation NO EXIST");
                        // The last know location will be the default position
                        lastKnownLocation = new Location("");
                        lastKnownLocation.setLatitude(mDefaultLocation.latitude);
                        lastKnownLocation.setLongitude(mDefaultLocation.longitude);
                    }
                    // Set last Know Location
                    mMapViewModel.setLastKnownLocation(lastKnownLocation);
                }
            });
        } catch (SecurityException e) {
            Log.e("getDeviceLocation %s", e.getMessage());
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                           UI
    // ---------------------------------------------------------------------------------------------
    /**
     * Method that places the map on a current location
     */
    private void showCurrentLocation(Location currentLocation) {
        Log.d(TAG, "showCurrentLocation: ");
        if (currentLocation != null) {
            Log.d(TAG, "showCurrentLocation: currentLocation.getLatitude()  = "
                    + currentLocation.getLatitude());
            Log.d(TAG, "showCurrentLocation: currentLocation.getLongitude() = "
                    + currentLocation.getLongitude());
        }
        mMapViewModel.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng( currentLocation.getLatitude(),
                            currentLocation.getLongitude()), DEFAULT_ZOOM));
        // Add a Marker for current position
        Marker marker =  mMapViewModel.getGoogleMap().addMarker(new MarkerOptions()
                .position(new LatLng(currentLocation.getLatitude(),
                        currentLocation.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Current Position")
        );
        marker.setTag("current");
    }
    /**
     * Creating estate markers on the map and activating for each of them a listener
     */
    public void displayAndListensMarkers(List<Estate> estates) {
        Log.d(TAG, "displayAndListensMarkers: ");

        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        List<Address> list;
        try {
            for (Estate estate : estates){
                // Get the coordinates of the
                list = geocoder.getFromLocationName(estate.getAddress().get(0)+","+
                                                                estate.getAddress().get(1)+","+
                                                                estate.getAddress().get(2)+","+
                                                                estate.getAddress().get(3)+","+
                                                                estate.getAddress().get(4)
                                                    , 1);
                // Displays the Marker if the location was found
                if (!list.isEmpty()) {
                    // Declare a Marker for current Estate
                    Marker marker =  mMapViewModel.getGoogleMap().addMarker(new MarkerOptions()
                            .position(new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude()))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                            .title(estate.getAddress().get(0))
                            .snippet(snippet(estate))
                    );
                    marker.setTag(estate.getEstate_Id());
                    mMapViewModel.getGoogleMap().setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        @Override
                        public View getInfoWindow(Marker marker) {return null;}
                        @Override
                        public View getInfoContents(Marker marker) {

                            LinearLayout info = new LinearLayout(getActivity());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getActivity());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getActivity());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            info.addView(title);
                            info.addView(snippet);

                            return info;
                        }
                    });
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
            ((MapActivity)getActivity()).showSnackBar("geoLocate: IOException: " + e.getMessage());
        }
    }
    private static String snippet(Estate estate){
        DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.US));
        return    "Type: " + estate.getType()
                + "\nPrice: $" + decimalFormat.format(estate.getPrice())
                + "\nSurface: " + estate.getArea() + " sq m"
                + "\nNb of rooms: " + estate.getNumberOfParts()
                + "\nNb of Bathrooms: " + estate.getNumberOfBathrooms()
                + "\nNb of Bedroomss: " + estate.getNumberOfBedrooms();
    }
    // ---------------------------------------------------------------------------------------------
    //                                       ACTIONS
    // ---------------------------------------------------------------------------------------------
    // Click on Map Marker
    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: ");

        if (!marker.getTag().equals("current")) {
            Integer i = (Integer.parseInt(marker.getTag().toString()));
            Long estate_Id = i.longValue();
            Log.d(TAG, "onInfoWindowClick: estate_Id = " + estate_Id);

            // Change current Estate Id
            CurrentEstateDataRepository.getInstance().setCurrentEstate_Id(estate_Id);

            // Close Activity and go back to previous activity
            getActivity().finish();
        }
    }
}
