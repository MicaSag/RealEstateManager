package com.openclassrooms.realestatemanager.controllers.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.openclassrooms.realestatemanager.controllers.bases.BaseActivity;
import com.openclassrooms.realestatemanager.controllers.fragments.MapFragment;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;

public class MapActivity extends BaseActivity {

    // For Debug
    private static final String TAG = MapActivity.class.getSimpleName();

    // Location
    private Location mLocation;

    // Fragments Declarations
    private MapFragment mMapFragment;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_map_constraint_layout) ConstraintLayout mConstraintLayout;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_map;
    }

    // BASE METHOD Implementation
    // Get the constraint layout
    // CALLED BY BASE METHOD
    @Override
    protected View getConstraintLayout() {
        return mConstraintLayout;
    }

    // BASE METHOD Implementation
    // Get the menu toolbar Layout
    // CALLED BY BASE METHOD
    @Override
    protected int getToolbarMenu() {
        return R.menu.menu_activity_map;
    }

    // ---------------------------------------------------------------------------------------------
    //                                OVERRIDE BASE METHODS
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void configureToolbar(){
        super.configureToolbar();
        Log.d(TAG, "configureToolbar: ");
        // Enable the Up button
        super.mActionBar.setDisplayHomeAsUpEnabled(true);

    }
    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        // Recover intent of the Caller
        Intent intent = getIntent();
        if (intent != null) {
            Location mLocation = intent.getParcelableExtra(RealEstateManagerActivity.KEY_LOCATION);
            if (mLocation != null) {
                Log.d(TAG, "onCreate: mLocation.getLatitude()  = " + mLocation.getLatitude());
                Log.d(TAG, "onCreate: mLocation.getLongitude() = " + mLocation.getLongitude());
                // Configuring Estate DetailsMap Fragment (accessible in the navigation drawer)
                this.configureMapFragment(mLocation);
            }
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    private void configureMapFragment(Location location) {
        Log.d(TAG, "configureMapFragment: ");
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mMapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);

        if (mMapFragment == null) {
            // Create new main fragment
            mMapFragment = MapFragment.newInstance(location);
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_map, mMapFragment)
                    .commit();
        }
    }
}
