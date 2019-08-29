package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Controllers.Fragments.DetailFragment;
import com.openclassrooms.realestatemanager.Controllers.Fragments.ListFragment;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Property;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.PropertyList.PropertyAdapter;
import com.openclassrooms.realestatemanager.PropertyList.PropertyViewModel;
import com.openclassrooms.realestatemanager.R;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class RealEstateManagerActivity extends BaseActivity implements PropertyAdapter.OnPropertyClick {

    // For debugging Mode
    private static final String TAG = RealEstateManagerActivity.class.getSimpleName();

    // Declare PropertyViewModel
    private PropertyViewModel mPropertyViewModel;

    // --> New RealEstateAgent
    private static long REAL_ESTATE_AGENT_ID_1 = 1;
    private static RealEstateAgent REAL_ESTATE_AGENT_1
            = new RealEstateAgent(REAL_ESTATE_AGENT_ID_1,
            "Michaël",
            "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg");
    private static long REAL_ESTATE_AGENT_ID_2 = 2;
    private static RealEstateAgent REAL_ESTATE_AGENT_2
            = new RealEstateAgent(REAL_ESTATE_AGENT_ID_2,
            "Pierre",
            "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg");
    private static Property NEW_PROPERTY_FLAT
            = new Property("Flat", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75001","France","1er arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID_1);
    private static Property NEW_PROPERTY_HOUSE
            = new Property("House", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75010","France","10ème arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID_1);
    private static Property NEW_PROPERTY_PENTHOUSE
            = new Property("Penthouse", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75005","France","5ième arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID_1);

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

        long realEstateAgent_Id = REAL_ESTATE_AGENT_ID_2;

        // Configure PropertyViewModel
        this.configurePropertyViewModel(realEstateAgent_Id);

        /*
        // FOR TEST, create RealEstateAgent
        createRealEstateAgent(REAL_ESTATE_AGENT_2);
                // FOR TEST, create Property
        NEW_PROPERTY_FLAT.setRealEstateAgent_Id(2);
        createProperty(NEW_PROPERTY_FLAT);
        NEW_PROPERTY_HOUSE.setRealEstateAgent_Id(2);
        createProperty(NEW_PROPERTY_HOUSE);

        NEW_PROPERTY_PENTHOUSE.setRealEstateAgent_Id(2);
        createProperty(NEW_PROPERTY_PENTHOUSE);

        deletePropertys(REAL_ESTATE_AGENT_ID_2);
        deleteRealEstateAgent(REAL_ESTATE_AGENT_ID_2);
        */

        // Configuring List Fragment (left position on Tablet)
        this.configureAndShowListFragment();

        // Configuring Detail Fragment (right position on Tablet)
        this.configureAndShowDetailFragment();

        // Get current RealEstateAgent & propertys from Database
        this.getCurrentRealEstateAgent();
        this.getPropertys(realEstateAgent_Id);
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure PropertyViewModel
    private void configurePropertyViewModel(long realEstateAgent_Id){
        ViewModelFactory mPropertyViewModelFactory = Injection.provideViewModelFactory(this);
        mPropertyViewModel = ViewModelProviders.of(this, mPropertyViewModelFactory).get(PropertyViewModel.class);
        /*
        // BEGIN -- INITIALIZE CURRENT REAL ESTATE AGENT
        // == Delete RealEstateAgent and Propertys
        deletePropertys(REAL_ESTATE_AGENT_ID_1);
        deleteRealEstateAgent(REAL_ESTATE_AGENT_ID_1);
        // == Create RealEstateAgent and Propertys
        createRealEstateAgent(REAL_ESTATE_AGENT_1);
        createProperty(NEW_PROPERTY_FLAT);
        createProperty(NEW_PROPERTY_HOUSE);
        createProperty(NEW_PROPERTY_PENTHOUSE);
        // END -- INITIALIZE CURRENT REAL ESTATE AGENT
        */
        mPropertyViewModel.init(realEstateAgent_Id);
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
    //                                           DATA
    // ---------------------------------------------------------------------------------------------
    // Get Current RealEstateAgent
    private void getCurrentRealEstateAgent(){
        Log.d(TAG, "getCurrentRealEstateAgent: ");
       mPropertyViewModel.getCurrentRealEstateAgent().observe(this, this::updateRealEstateAgent);
    }

    // Create a new RealEstateAgent
    private void createRealEstateAgent(RealEstateAgent realEstateAgent){
        Log.d(TAG, "createRealestateAgent: ");
        mPropertyViewModel.createRealEstateAgent(realEstateAgent);
    }

    // Delete a RealEstateAgent
    private void deleteRealEstateAgent(long realEstateAgent_Id){
        Log.d(TAG, "deleteRealEstateAgent: ");
        mPropertyViewModel.deleteRealEstateAgent(realEstateAgent_Id);
    }

    // ---

    // Get all propertys for a RealEstateAgent
    private void getPropertys(long realEstateAgent_Id){
        Log.d(TAG, "getPropertys: ");
        mPropertyViewModel.getPropertys(realEstateAgent_Id).observe(this, this::updatePropertyList);
    }

    // Create a new property
    private void createProperty(Property property){
        Log.d(TAG, "createProperty: ");
        mPropertyViewModel.createProperty(property);
    }

    // Delete an Property
    private void deleteProperty(long property_Id){
        Log.d(TAG, "deleteProperty: ");
        mPropertyViewModel.deleteProperty(property_Id);
    }

    // Delete Propertys of an RealEstateAgent
    private void deletePropertys(long realEstateAgent_Id){
        Log.d(TAG, "deleteProperty: ");
        mPropertyViewModel.deletePropertys(realEstateAgent_Id);
    }

    // Update an property (selected or not)
    private void updateProperty(Property property){
        Log.d(TAG, "updateProperty: ");
        mPropertyViewModel.updateProperty(property);
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

    @Override
    public void onPropertyClick(Property property) {
        Log.d(TAG, "onPropertyClick: ");
        property.setType(property.getType().equals("House") ? "Penthouse": "House");
        updateProperty(property);
    }

    // Update the list of Property
    private void updatePropertyList(List<Property> propertys){
        Log.d(TAG, "updatePropertyList: ");
        Log.d(TAG, "updatePropertyList: propertys.size = "+ propertys.size());
        mListFragment.updateUI(propertys);
        //mDetailFragment.updateUI(propertys.get(mListFragment.getmAdapter().get));
        mDetailFragment.updateUI(propertys.get(0));
    }

    // Update the RealEstateAgent Data
    private void updateRealEstateAgent(RealEstateAgent realEstateAgent){
        Log.d(TAG, "updateRealEstateAgent: ");
        this.showSnackBar("RealEstateAgent = "+realEstateAgent.getUserName());
    }
}
