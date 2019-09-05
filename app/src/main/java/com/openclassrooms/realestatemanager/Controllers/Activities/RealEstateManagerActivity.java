package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Controllers.Fragments.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.Controllers.Fragments.EstateListFragment;
import com.openclassrooms.realestatemanager.EstateList.EstateListAdapter;
import com.openclassrooms.realestatemanager.EstateList.EstateListViewModel;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.RealEstateAgent.RealEstateAgentViewModel;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentRepository;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

public class RealEstateManagerActivity extends BaseActivity implements EstateListAdapter.OnEstateClick {

    // For debugging Mode
    private static final String TAG = RealEstateManagerActivity.class.getSimpleName();

    // Declare EstateListViewModel
    private RealEstateAgentViewModel mRealEstateAgentViewModel;

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
    private static Estate newEstateFlat
            = new Estate("Flat", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75001","France","1er arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID_1);
    private static Estate newEstateHouse
            = new Estate("House", 10000000, 750,
            5, 2, 4, "Beautifull FLAT in Paris",
            new ArrayList<>(Arrays.asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")),
            new ArrayList<>(Arrays.asList("3 way of the temple","","PARIS","75010","France","10ème arrond")),
            new ArrayList<>(Arrays.asList("School Jean Baptiste" , "Super Market Lidl")),
            (LocalDateTime.now().withDayOfMonth(10).withYear(2019).withMonth(8)),
            LocalDateTime.now(),
            REAL_ESTATE_AGENT_ID_1);
    private static Estate newEstatePenthouse
            = new Estate("Penthouse", 10000000, 750,
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
    private EstateDetailsFragment mEstateDetailsFragment;
    private EstateListFragment mEstateListFragment;

    // Data
    private long mCurrentRealEstateAgent_Id;

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

        mCurrentRealEstateAgent_Id = REAL_ESTATE_AGENT_ID_1;

        // Configure RealEstateViewModel
        this.configureRealEstateAgentViewModel();

        // FOR TEST, create RealEstateAgent
        //createRealEstateAgent(REAL_ESTATE_AGENT_2);


        // FOR TEST, create Estate
        /*newEstateFlat.setRealEstateAgent_Id(2);
        createEstate(newEstateFlat);
        newEstateHouse.setRealEstateAgent_Id(2);
        createEstate(newEstateHouse);
        newEstatePenthouse.setRealEstateAgent_Id(2);
        createEstate(newEstatePenthouse);
        */
        //deleteRealEstateAgent(REAL_ESTATE_AGENT_ID_2);


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

        // BEGIN -- INITIALIZE CURRENT REAL ESTATE AGENT
        // == Delete RealEstateAgent and Estates
        /*deleteEstates(REAL_ESTATE_AGENT_ID_1);
        deleteRealEstateAgent(REAL_ESTATE_AGENT_ID_1);
        deleteEstates(REAL_ESTATE_AGENT_ID_2);
        deleteRealEstateAgent(REAL_ESTATE_AGENT_ID_2);*/

        // == Create RealEstateAgent and Estates
        //createRealEstateAgent(REAL_ESTATE_AGENT_1);
        //createEstate(newEstateFlat);
        //createEstate(newEstateHouse);
        //createEstate(newEstatePenthouse);
        // END -- INITIALIZE CURRENT REAL ESTATE AGENT

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
            mEstateListFragment = EstateListFragment.newInstance(mCurrentRealEstateAgent_Id);
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
        if (mEstateDetailsFragment == null && findViewById(R.id.fragment_estate_details) != null) {
            // Create new main fragment
            mEstateDetailsFragment = EstateDetailsFragment.newInstance(mCurrentRealEstateAgent_Id,10);
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
    // Create a new RealEstateAgent
    private void createRealEstateAgent(RealEstateAgent realEstateAgent){
        Log.d(TAG, "createRealestateAgent: ");
        mRealEstateAgentViewModel.createRealEstateAgent(realEstateAgent);
    }
    // Delete a RealEstateAgent
    private void deleteRealEstateAgent(long realEstateAgent_Id){
        Log.d(TAG, "deleteRealEstateAgent: ");
        mRealEstateAgentViewModel.deleteRealEstateAgent(realEstateAgent_Id);
    }
/*    // --------------------
    // >>>> Propertys <<<<<
    // --------------------
    // Create a new property
    private void createEstate(Estate estate){
        Log.d(TAG, "createProperty: ");
        mEstateListViewModel.createEstate(estate);
    }

    // Delete an Estate
    private void deleteEstate(long estate_Id){
        Log.d(TAG, "deleteProperty: ");
        mEstateListViewModel.deleteEstate(estate_Id);
    }

    // Delete Propertys of an RealEstateAgent
    private void deleteEstates(long realEstateAgent_Id){
        Log.d(TAG, "deleteProperty: ");
        mEstateListViewModel.deleteEstates(realEstateAgent_Id);
    }

    // Update an property (selected or not)
    private void updateEstate(Estate estate){
        Log.d(TAG, "updateProperty: ");
        mEstateListViewModel.updateEstate(estate);
    }*/
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_real_estate_manager_search:
                //showSnackBar("Search Button Activated");
                //mRealEstateAgentViewModel.setCurrentRealEstateAgent(2);
                CurrentRealEstateAgentRepository.getInstance().setCurrentRealEstateAgent_Id(2);
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
    public void onEstateClick(Estate estate) {
        Log.d(TAG, "onEstateClick: ");
        estate.setType(estate.getType().equals("House") ? "Penthouse": "House");
       // updateProperty(estate);
    }
    // Update the RealEstateAgent Data
    private void updateCurrentRealEstateAgent_Id(RealEstateAgent realEstateAgent){
        Log.d(TAG, "updateRealEstateAgent: ");
        this.showSnackBar("RealEstateAgent = "+realEstateAgent.getUserName());
        mCurrentRealEstateAgent_Id = realEstateAgent.getRealEstateAgent_Id();
    }
}
