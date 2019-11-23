package com.openclassrooms.realestatemanager.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.controllers.bases.BaseActivity;
import com.openclassrooms.realestatemanager.controllers.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.EstateListFragment;
import com.openclassrooms.realestatemanager.adapters.estateList.EstateListAdapter;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.RealEstateAgent;
import com.openclassrooms.realestatemanager.models.SearchData;
import com.openclassrooms.realestatemanager.models.views.RealEstateManagerViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.repositories.CurrentEstateDataRepository;
import com.openclassrooms.realestatemanager.utils.Utils;

import butterknife.BindView;

public class RealEstateManagerActivity  extends BaseActivity
                                        implements  EstateListAdapter.OnEstateClick,
                                                    NavigationView.OnNavigationItemSelectedListener {
    // For debugging Mode
    private static final String TAG = RealEstateManagerActivity.class.getSimpleName();

    // Declare EstateListViewModel
    private RealEstateManagerViewModel mRealEstateManagerViewModel;

    // --> New RealEstateAgent
    private static long REAL_ESTATE_AGENT_ID_1 = 1;

    // Fragments Declarations
    private EstateDetailsFragment mEstateDetailsFragment;
    private EstateListFragment mEstateListFragment;

    // For call Activities
    public static final int CREATE_ACTIVITY_REQUEST_CODE = 10;
    public static final int UPDATE_ACTIVITY_REQUEST_CODE = 20;
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 30;

    // Data
    private long mCurrentRealEstateAgent_Id;

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_real_estate_manager_constraint_layout) ConstraintLayout mConstraintLayout;
    @BindView(R.id.activity_real_estate_manager_drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_real_estate_manager_nav_view) NavigationView mNavigationView;

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
        return R.menu.menu_activity_real_estate_manager;
    }

    // ---------------------------------------------------------------------------------------------
    //                                        ENTRY POINT
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentRealEstateAgent_Id = REAL_ESTATE_AGENT_ID_1;

        // Configure the Navigation Drawer
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureNavigationMenuItem();

        // Configure ViewModel
        this.configureViewModel();

        // Configuring Estate List Fragment (left position on Tablet)
        this.configureAndShowEstateListFragment();

        // Configuring Estate Details Fragment (right position on Tablet)
        this.configureAndShowEstateDetailsFragment();

        // Get current RealEstateAgent & Estates from Database
        this.getCurrentRealEstateAgent();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
    private void configureViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mRealEstateManagerViewModel = ViewModelProviders.of(this, modelFactory)
                .get(RealEstateManagerViewModel.class);

        mRealEstateManagerViewModel.init(mCurrentRealEstateAgent_Id);
    }

    public RealEstateManagerViewModel getRealEstateManagerViewModel() {
        return mRealEstateManagerViewModel;
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowEstateListFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mEstateListFragment = (EstateListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_estate_list);

        if (mEstateListFragment == null) {
            // Create new main fragment
            mEstateListFragment = EstateListFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_estate_list, mEstateListFragment)
                    .commit();
        }
    }
    private void configureAndShowEstateDetailsFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mEstateDetailsFragment = (EstateDetailsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_estate_details);

        // We only add DetailsFragment for wight 1280 (If found frame_layout_detail)
        if (mEstateDetailsFragment == null && getResources().getBoolean(R.bool.is_w1280)) {
            // Create new main fragment
            mEstateDetailsFragment = EstateDetailsFragment.newInstance();
            // Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_estate_details, mEstateDetailsFragment)
                    .commit();
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                           DATA
    // ---------------------------------------------------------------------------------------------
    // >>>> RealEstateAgent <<<<<
    // --------------------------
    // Get Current RealEstateAgent
    private void getCurrentRealEstateAgent(){
        Log.d(TAG, "getCurrentRealEstateAgent: ");
        mRealEstateManagerViewModel.getCurrentRealEstateAgent()
                .observe(this, this::updateCurrentRealEstateAgent_Id);
    }
    // Update the RealEstateAgent Data
    private void updateCurrentRealEstateAgent_Id(RealEstateAgent realEstateAgent){
        Log.d(TAG, "updateRealEstateAgent: ");
        mCurrentRealEstateAgent_Id = realEstateAgent.getRealEstateAgent_Id();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the return result comes from the create activity
        if (CREATE_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the result from the Intent
           if(data.getBooleanExtra(CreateEstateActivity.BUNDLE_CREATE_OK, false))
               showSnackBar("The creation of the estate was carried out");
        }
        // If the return result comes from the update activity
        if (UPDATE_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the result from the Intent
            if(data.getBooleanExtra(UpdateEstateActivity.BUNDLE_UPDATE_OK, false))
                showSnackBar("The update of the estate was carried out");
        }
        // If the return result comes from the search activity
        if (SEARCH_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the result from the Intent
            if(data.getBooleanExtra(SearchEstateActivity.BUNDLE_SEARCH_OK, false))
                showSnackBar("The search for estates has succeeded");
            SearchData searchData = data.getParcelableExtra(SearchEstateActivity.BUNDLE_SEARCH_DATA);
            mRealEstateManagerViewModel.setSearchData(searchData);
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                     NAVIGATION DRAWER
    // ---------------------------------------------------------------------------------------------
    // >> CONFIGURATION <-------
    // Configure Drawer Layout and connects him the ToolBar and the NavigationView
    private void configureDrawerLayout() {
        Log.d(TAG, "configureDrawerLayout: ");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView() {
        Log.d(TAG, "configureNavigationView: ");
        // Subscribes to listen the navigationView
        mNavigationView.setNavigationItemSelectedListener(this);
        // Mark as selected the menu item corresponding to First tab 'TOP STORIES'
        this.mNavigationView.getMenu().getItem(0).setChecked(true);
    }

    // Configure NavigationView
    private void configureNavigationMenuItem() {
        //Disable tint icons
        this.mNavigationView.setItemIconTintList(null);
    }
    // ---------------------------------------------------------------------------------------------
    //                                         ACTIONS
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onEstateClick(Estate estate) {
        Log.d(TAG, "onEstateClick: ");
        CurrentEstateDataRepository.getInstance().setCurrentEstate_Id(estate.getEstate_Id());

        // If wight < 1280 then call DetailsEstateActivity
        Log.d(TAG, "onEstateClick: is_w1280 = "+getResources().getBoolean(R.bool.is_w1280));
        if (!getResources().getBoolean(R.bool.is_w1280))
        Utils.startActivity(this, DetailsEstateActivity.class);
    }
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        // Close the menu so open and if the touch return is pushed
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: ");

        // Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_real_estate_manager_map:
                Utils.startActivity(this,MapActivity.class);
                break;
            case R.id.activity_real_estate_manager_search:
                // Create a intent for call Activity
                Intent searchIntent = new Intent(this, SearchEstateActivity.class);
                // Go to SearchEstateActivity
                startActivityForResult(searchIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                break;
            default:
                break;
        }
        // Close menu drawer
        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_real_estate_manager_search:
                // Create a intent for call Activity
                Intent searchIntent = new Intent(this, SearchEstateActivity.class);

                // Go to SearchEstateActivity
                startActivityForResult(searchIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_activity_real_estate_manager_edit:
                // Create a intent for call Activity
                Intent updateIntent = new Intent(this, UpdateEstateActivity.class);

                // Go to CreateEstateActivity
                startActivityForResult(updateIntent, UPDATE_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.menu_activity_real_estate_manager_add:
                // Create a intent for call Activity
                Intent createIntent = new Intent(this, CreateEstateActivity.class);

                // Go to CreateEstateActivity
                startActivityForResult(createIntent, CREATE_ACTIVITY_REQUEST_CODE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
