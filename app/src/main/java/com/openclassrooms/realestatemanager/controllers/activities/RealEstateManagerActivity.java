package com.openclassrooms.realestatemanager.controllers.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.controllers.bases.BaseActivity;
import com.openclassrooms.realestatemanager.controllers.fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.EstateListFragment;
import com.openclassrooms.realestatemanager.adapters.estateList.EstateListAdapter;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.RealEstateAgent;
import com.openclassrooms.realestatemanager.models.views.RealEstateAgentViewModel;
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
    private RealEstateAgentViewModel mRealEstateAgentViewModel;

    // --> New RealEstateAgent
    private static long REAL_ESTATE_AGENT_ID_1 = 1;

    // Fragments Declarations
    private EstateDetailsFragment mEstateDetailsFragment;
    private EstateListFragment mEstateListFragment;

    // For call Activities
    public static final int CREATE_ACTIVITY_REQUEST_CODE = 10;
    public static final int UPDATE_ACTIVITY_REQUEST_CODE = 20;
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 30;
    // For transmission data to activity
    public static final String KEY_LOCATION = "KEY_LOCATION";

    // Data
    private long mCurrentRealEstateAgent_Id;

    // For use LOCATION permission
    // ---------------------------
    // 1 _ Request Code
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // For determinate Location
    // -------------------------
    // Default location if not permission granted ( Paris )
    private final LatLng mDefaultLocation = new LatLng(48.844304, 2.374377);
    // The geographical location where the device is currently located.
    // That is, the last-known location retrieved by the Fused Location Provider.
    // OR the default Location if permission not Granted
    private MutableLiveData<Location> mLastKnownLocation = new MutableLiveData<>();
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

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

        // Observer which launches the map activity
        // if we search for the current position or the last known position
        mLastKnownLocation.observe(this,this::startMapActivity);

        // Configure the Navigation Drawer
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureNavigationMenuItem();

        // Configure RealEstateViewModel
        this.configureRealEstateAgentViewModel();

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
    // Configure RealEstateViewModel
    private void configureRealEstateAgentViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mRealEstateAgentViewModel = ViewModelProviders.of(this, modelFactory).get(RealEstateAgentViewModel.class);

        mRealEstateAgentViewModel.init(mCurrentRealEstateAgent_Id);
    }
    // ---------------------------------------------------------------------------------------------
    //                                        FRAGMENTS
    // ---------------------------------------------------------------------------------------------
    private void configureAndShowEstateListFragment() {
        // Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mEstateListFragment = (EstateListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_estate_list);

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
        mEstateDetailsFragment = (EstateDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_estate_details);

        // We only add DetailsFragment in Tablet mode (If found frame_layout_detail)
        if (mEstateDetailsFragment == null && getResources().getBoolean(R.bool.is_tablet)) {
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
        mRealEstateAgentViewModel.getCurrentRealEstateAgent().observe(this, this::updateCurrentRealEstateAgent_Id);
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
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
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

    // >> ACTIONS <-------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: ");

        // Handle Navigation Item Click
        int id = item.getItemId();

        switch (id) {
            case R.id.activity_real_estate_manager_map:
                this.getLocationAndInitializeApi();
                break;
            case R.id.activity_real_estate_manager_search:
                // Start Search Estate Activity
                startSearchEstateActivity();
                break;
            default:
                break;
        }
        // Close menu drawer
        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
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
    public void startMapActivity(Location lastKnowLocation){
        Log.d(TAG, "startMapActivity: ");

        if (lastKnowLocation != null) {
            Log.d(TAG, "startMapActivity: getLastKnownCurrentLocationDevice: lastKnownLocation.getLatitude()  = " + lastKnowLocation.getLatitude());
            Log.d(TAG, "startMapActivity: getLastKnownCurrentLocationDevice: lastKnownLocation.getLongitude() = " + lastKnowLocation.getLongitude());
            // Call Map Activity
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra(KEY_LOCATION, lastKnowLocation);
            startActivity(intent);
        }
    }
    public void startSearchEstateActivity(){
        Log.d(TAG, "startSearchEstateActivity: ");


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
    private void getLocationAndInitializeApi() {
        Log.d(TAG, "getLocationPermission: ");

        // Check if permissions are already authorized
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
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
                    mLastKnownLocation.setValue(lastKnownLocation);
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
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            // Retrieves information if existing
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    Location lastKnownLocation;
                    lastKnownLocation = task.getResult();
                    if (lastKnownLocation != null) {
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: getLastLocation EXIST");
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: lastKnownLocation.getLatitude()  = " + lastKnownLocation.getLatitude());
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: lastKnownLocation.getLongitude() = " + lastKnownLocation.getLongitude());
                    } else {
                        Log.d(TAG, "getLastKnownCurrentLocationDevice: getLastLocation NO EXIST");
                        // The last know location will be the default position
                        lastKnownLocation = new Location("");
                        lastKnownLocation.setLatitude(mDefaultLocation.latitude);
                        lastKnownLocation.setLongitude(mDefaultLocation.longitude);
                    }
                    // Set last Know Location
                    mLastKnownLocation.setValue(lastKnownLocation);
                }
            });
        } catch (SecurityException e) {
            Log.e("getDeviceLocation %s", e.getMessage());
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                                         ACTIONS
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onEstateClick(Estate estate) {
        Log.d(TAG, "onEstateClick: ");
        CurrentEstateDataRepository.getInstance().setCurrentEstate_Id(estate.getEstate_Id());

        // We only add DetailsFragment in Tablet mode (If found frame_layout_detail)
        if (findViewById(R.id.fragment_estate_details) == null)
            Utils.startActivity(this, DetailsEstateActivity.class);
    }
    // ---------------------------------------------------------------------------------------------
    //                                         TEST
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }
}