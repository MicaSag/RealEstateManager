package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Controllers.Fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;

public class DetailsEstateActivity extends BaseActivity {

    private static final String TAG = DetailsEstateActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_details_coordinator_layout) CoordinatorLayout mCoordinatorLayout;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_details_estate;
    }

    // BASE METHOD Implementation
    // Get the coordinator layout
    // CALLED BY BASE METHOD
    @Override
    protected View getCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    // BASE METHOD Implementation
    // Get the menu toolbar Layout
    // CALLED BY BASE METHOD
    @Override
    protected int getToolbarMenu() {
        return R.menu.menu_activity_details_estate;
    }
    // ---------------------------------------------------------------------------------------------
    //                                OVERLOAD BASE METHODS
    // ---------------------------------------------------------------------------------------------
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

        // Configuring Toolbar
        this.configureToolbar();

        // Configure EstateDetailsFragment
        this.configureAndShowEstateDetailsFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENT
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowEstateDetailsFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        // Declare fragment
        EstateDetailsFragment mEstateDetailsFragment = (EstateDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_estate_details);

        // Create new Estate Details fragment
        mEstateDetailsFragment = EstateDetailsFragment.newInstance();
        // Add it to FrameLayout container
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_estate_details, mEstateDetailsFragment)
                .commit();
    }
}
