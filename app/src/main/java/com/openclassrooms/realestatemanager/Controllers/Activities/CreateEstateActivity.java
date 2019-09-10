package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;

public class CreateEstateActivity extends BaseActivity {

    // For debugging Mode
    private static final String TAG = CreateEstateActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_create_estate_CL) ConstraintLayout mCoordinatorLayout;

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_create_estate;
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
        return R.menu.menu_activity_create_estate;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuring Toolbar
        this.configureToolbar();

        // Configure RealEstateViewModel
        //this.configureRealEstateAgentViewModel();

        // Get current RealEstateAgent & Estates from Database
        //this.getCurrentRealEstateAgent();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure RealEstateViewModel
    private void configureRealEstateAgentViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        //mRealEstateAgentViewModel = ViewModelProviders.of(this, modelFactory).get(RealEstateAgentViewModel.class);

        //mRealEstateAgentViewModel.init(mCurrentRealEstateAgent_Id);
    }
}
