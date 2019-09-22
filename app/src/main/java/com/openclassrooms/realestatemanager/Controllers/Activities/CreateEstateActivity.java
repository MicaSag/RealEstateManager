package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.views.EstateCreateViewModel;
import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateEstateActivity extends BaseActivity {

    // For debugging Mode
    private static final String TAG = CreateEstateActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_create_estate_CL) ConstraintLayout mCoordinatorLayout;
    @BindView(R.id.activity_create_estate_auto_type) AutoCompleteTextView mAutoCompleteTVType;
    @BindView(R.id.activity_create_estate_ed_price) EditText mPrice;
    @BindView(R.id.activity_create_estate_ed_description) EditText mDescription;

    private EstateCreateViewModel mEstateCreateViewModel;

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

        // Configure AutoComplete Type
        this.configureAutoCompleteType();

        // Configure RealEstateViewModel
        this.configureEstateCreateViewModel();

        // Get current RealEstateAgent & Estates from Database
        //this.getCurrentRealEstateAgent();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure RealEstateViewModel
    private void configureEstateCreateViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mEstateCreateViewModel = ViewModelProviders.of(this, modelFactory).get(EstateCreateViewModel.class);

        mPrice.setText(mEstateCreateViewModel.getEstate().getPrice().toString());
        Log.d(TAG, "configureEstateCreateViewModel: price = " + mEstateCreateViewModel.getEstate().getPrice().toString());
        Log.d(TAG, "configureEstateCreateViewModel: description = " + mEstateCreateViewModel.getEstate().getDescription());

    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTIONS
    // ---------------------------------------------------------------------------------------------
    @OnClick(R.id.activity_create_estate_ed_price)
    public void setPrice(View view) {

        mEstateCreateViewModel.getEstate().setPrice(Integer.parseInt(mPrice.getText().toString()));
        Log.d(TAG, "setPrice : price = " + mEstateCreateViewModel.getEstate().getPrice().toString());
    }
    @OnClick(R.id.activity_create_estate_ed_description)
    public void setDescription(View view) {

        mEstateCreateViewModel.getEstate().setDescription(mDescription.getText().toString());
        Log.d(TAG, "setDescription : description = " + mEstateCreateViewModel.getEstate().getDescription());
    }
    // ---------------------------------------------------------------------------------------------
    //                              AUTOCOMPLETE TYPE CONFIGURATION
    // ---------------------------------------------------------------------------------------------
    private void configureAutoCompleteType() {
        Log.d(TAG, "configureAutoCompleteType: ");

        String[] countries = {"Flat" , "House" , "Duplex" , "Penthouse" };
        // String[] countries = getResources().getStringArray(R.array.list_of_countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, countries);
        mAutoCompleteTVType.setThreshold(1);//will start working from first character
        mAutoCompleteTVType.setTextColor(Color.RED);
        mAutoCompleteTVType.setAdapter(adapter);
    }
}
