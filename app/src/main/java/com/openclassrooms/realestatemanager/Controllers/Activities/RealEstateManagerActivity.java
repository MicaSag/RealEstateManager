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
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.RealEstateAgent.RealEstateAgentViewModel;
import com.openclassrooms.realestatemanager.Repositories.CurrentEstateDataRepository;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentDataRepository;
import com.openclassrooms.realestatemanager.Utils.Utils;

import butterknife.BindView;

public class RealEstateManagerActivity extends BaseActivity implements EstateListAdapter.OnEstateClick {

    // For debugging Mode
    private static final String TAG = RealEstateManagerActivity.class.getSimpleName();

    // Declare EstateListViewModel
    private RealEstateAgentViewModel mRealEstateAgentViewModel;

    // --> New RealEstateAgent
    private static long REAL_ESTATE_AGENT_ID_1 = 1;
    private static long REAL_ESTATE_AGENT_ID_2 = 2;

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
    // ---------------------------------------------------------------------------------------------
    //                                             UI
    // ---------------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_real_estate_manager_search:
                //Log.d(TAG, "onOptionsItemSelected: Search Button Activated");
                long agent_Id;
                agent_Id = (CurrentRealEstateAgentDataRepository.getInstance().
                        getCurrentRealEstateAgent_Id().getValue() == 2) ? 1 : 2;
                CurrentRealEstateAgentDataRepository.getInstance().setCurrentRealEstateAgent_Id(agent_Id);
                return true;
            case R.id.menu_activity_real_estate_manager_edit:
                // Go to CreateEstateActivity
                Utils.startActivity(this, CreateEstateActivity.class);

                return true;
            case R.id.menu_activity_real_estate_manager_add:
                Log.d(TAG, "onOptionsItemSelected: Add Button Activated");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onEstateClick(Estate estate) {
        Log.d(TAG, "onEstateClick: ");
        CurrentEstateDataRepository.getInstance().setCurrentEstate_Id(estate.getEstate_Id());

        this.showSnackBar("Estate_Id = "+estate.getEstate_Id());
    }

    // Update the RealEstateAgent Data
    private void updateCurrentRealEstateAgent_Id(RealEstateAgent realEstateAgent){
        Log.d(TAG, "updateRealEstateAgent: ");
        this.showSnackBar("RealEstateAgent = "+realEstateAgent.getUserName());
        mCurrentRealEstateAgent_Id = realEstateAgent.getRealEstateAgent_Id();
    }
}
