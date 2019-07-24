package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.MenuItem;
import android.view.View;

import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Controllers.Fragments.DetailFragment;
import com.openclassrooms.realestatemanager.Controllers.Fragments.ListFragment;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;

public class RealEstateManagerActivity extends BaseActivity {

    // For debugging Mode
    private static final String TAG = RealEstateManagerActivity.class.getSimpleName();

    // Declare fragments
    private DetailFragment mDetailFragment;
    private ListFragment mListFragment;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_real_estate_manager_coordinator_layout) CoordinatorLayout mCoordinatorLayout;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_real_estate_manager;
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
        return R.menu.menu_activity_real_estate_manager;
    }

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuring List Fragment (left position on Tablet)
        this.configureAndShowListFragment();

        // Configuring Detail Fragment (right position on Tablet)
        this.configureAndShowDetailFragment();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowListFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list);

        if (mListFragment == null) {
            // Create new main fragment
            mListFragment = ListFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_list, mListFragment)
                    .commit();
        }
    }
    private void configureAndShowDetailFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mDetailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);

        // We only add DetailsFragment in Tablet mode (If found frame_layout_detail)
            if (mDetailFragment == null && findViewById(R.id.fragment_detail) != null) {
            // Create new main fragment
            mDetailFragment = DetailFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail, mDetailFragment)
                    .commit();
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_real_estate_manager_search:
                showSnackBar("Search Button Activated");
                return true;
            case R.id.menu_activity_real_estate_manager_edit:
                showSnackBar("Edit Button Activated");
                return true;
            case R.id.menu_activity_real_estate_manager_add:
                showSnackBar("Add Button Activated");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
