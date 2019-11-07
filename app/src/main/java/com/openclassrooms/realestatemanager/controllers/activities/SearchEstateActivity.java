package com.openclassrooms.realestatemanager.controllers.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;
import com.openclassrooms.realestatemanager.controllers.bases.BaseActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.views.EstateSearchViewModel;
import com.openclassrooms.realestatemanager.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchEstateActivity extends BaseActivity {

    // For Debug
    private static final String TAG = SearchEstateActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.search_estate_cl) ConstraintLayout mConstraintLayout;
    // - search Criteria
    @BindView(R.id.search_estate_auto_type) AutoCompleteTextView mEstateType;
    @BindView(R.id.search_estate_ed_price_1) EditText mPrice1;
    @BindView(R.id.search_estate_ed_price_2) EditText mPrice2;
    @BindView(R.id.search_estate_ed_city) EditText mCity;
    @BindView(R.id.search_estate_ed_surface_1) EditText mSurface1;
    @BindView(R.id.search_estate_ed_surface_2) EditText mSurface2;
    @BindView(R.id.search_estate_ed_numbers_rooms_1) EditText mNumbersRooms1;
    @BindView(R.id.search_estate_ed_numbers_rooms_2) EditText mNumbersRooms2;
    @BindView(R.id.search_estate_ed_numbers_bathrooms_1) EditText mNumbersBathrooms1;
    @BindView(R.id.search_estate_ed_numbers_bathrooms_2) EditText mNumbersBathrooms2;
    @BindView(R.id.search_estate_ed_numbers_bedrooms_1) EditText mNumbersBedrooms1;
    @BindView(R.id.search_estate_ed_numbers_bedrooms_2) EditText mNumbersBedrooms2;
    @BindView(R.id.search_estate_ed_numbers_photo_1) EditText mNumbersPhoto1;
    @BindView(R.id.search_estate_ed_numbers_photo_2) EditText mNumbersPhoto2;
    @BindView(R.id.search_estate_ed_date_entry_1) EditText mEntryDate1;
    @BindView(R.id.search_estate_ed_date_entry_2) EditText mEntryDate2;
    @BindView(R.id.search_estate_ed_date_sale_1) EditText mSaleDate1;
    @BindView(R.id.search_estate_ed_date_sale_2) EditText mSaleDate2;
    // - search Criteria - Chips
    @BindView(R.id.estate_chip_school) Chip mChipSchool;
    @BindView(R.id.estate_chip_garden) Chip mChipGarden;
    @BindView(R.id.estate_chip_library) Chip mChipLibrary;
    @BindView(R.id.estate_chip_restaurant) Chip mChipRestaurant;
    @BindView(R.id.estate_chip_town_hall) Chip mChipTownHall;
    @BindView(R.id.estate_chip_swimming_pool) Chip mChipSwimmingPool;

    private EstateSearchViewModel mEstateSearchViewModel;

    // Declarations for management of the date fields with a DatePickerDialog
    private DatePickerDialog mEntryDatePickerDialog1;
    private DatePickerDialog mEntryDatePickerDialog2;
    private DatePickerDialog mSaleDatePickerDialog1;
    private DatePickerDialog mSaleDatePickerDialog2;
    private SimpleDateFormat displayDateFormatter;
    private Calendar newCalendar;


    // To return the result to the parent activity
    public static final String BUNDLE_SEARCH_OK = "BUNDLE_SEARCH_OK";

    // ---------------------------------------------------------------------------------------------
    //                                DECLARATION BASE METHODS
    // ---------------------------------------------------------------------------------------------
    // BASE METHOD Implementation
    // Get the activity layout
    // CALLED BY BASE METHOD 'onCreate(...)'
    @Override
    protected int getActivityLayout() {
        return R.layout.activity_search_estate;
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
        return R.menu.menu_activity_create_estate;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure AutoComplete Type
        this.configureAutoCompleteType();

        // Management of Date Fields
        this.manageDateFields();

        // Configure RealEstateViewModel
        this.configureEstateCreateViewModel();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure RealEstateViewModel
    private void configureEstateCreateViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mEstateSearchViewModel = ViewModelProviders.of(this, modelFactory).get(EstateSearchViewModel.class);

        mEstateSearchViewModel.getViewActionLiveData().observe(this, new Observer<EstateSearchViewModel.ViewAction>() {
            @Override
            public void onChanged(EstateSearchViewModel.ViewAction viewAction) {
                if (viewAction == null)  {
                    return;
                }

                switch (viewAction) {
                    case INVALID_INPUT:
                        showSnackBar("Invalid data search");
                        break;

                    case FINISH_ACTIVITY:
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_SEARCH_OK,true);
                        setResult(RESULT_OK,intent);
                        // Close Activity and go back to previous activity
                        finish();
                        break;

                    case SEARCH_NO_RESULT:
                        showSnackBar("Search No Result found");
                        break;
                }
            }
        });

        // Observe a change of Date of Entry1 on the Market
        mEstateSearchViewModel.getDateEntryOfTheMarket1().observe(this,this::refreshDateEntryOfTheMarket1);
        // Observe a change of Date of Entry2 on the Market
        mEstateSearchViewModel.getDateEntryOfTheMarket2().observe(this,this::refreshDateEntryOfTheMarket2);
        // Observe a change of Date of Sale1 on the Market
        mEstateSearchViewModel.getDateSale1().observe(this,this::refreshDateSale1);
        // Observe a change of Date of Sale2 on the Market
        mEstateSearchViewModel.getDateSale2().observe(this,this::refreshDateSale2);
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTIONS
    // ---------------------------------------------------------------------------------------------
    // Click on EntryDate1 Field
    @OnClick(R.id.search_estate_ed_date_entry_1)
    public void onClickEntryDate1(View view) {
        mEntryDatePickerDialog1.show();
    }
    // Click on EntryDate2 Field
    @OnClick(R.id.search_estate_ed_date_entry_2)
    public void onClickEntryDate2(View view) {
        mEntryDatePickerDialog2.show();
    }
    // Click on SaleDate1 Field
    @OnClick(R.id.search_estate_ed_date_sale_1)
    public void onClickSaleDate1(View view) {
        mSaleDatePickerDialog1.show();
    }
    // Click on SaleDate2 Field
    @OnClick(R.id.search_estate_ed_date_sale_2)
    public void onClickSaleDate2(View view) {
        mSaleDatePickerDialog2.show();
    }
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        // necessary so that onBackPressed does not destroy the previous application
        Intent intent = new Intent(this, RealEstateManagerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(TAG, "onOptionsItemSelected: ");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Click on Validate Button
    @OnClick(R.id.search_estate_bt_search)
    public void search(View view) {
        Log.d(TAG, "search: ");

        mEstateSearchViewModel.searchEstate(
                mEstateType.getText().toString(),
                mPrice1.getText().toString(),
                mPrice2.getText().toString(),
                mSurface1.getText().toString(),
                mSurface2.getText().toString(),
                mCity.getText().toString(),
                mNumbersRooms1.getText().toString(),
                mNumbersRooms2.getText().toString(),
                mNumbersBathrooms1.getText().toString(),
                mNumbersBathrooms2.getText().toString(),
                mNumbersBedrooms1.getText().toString(),
                mNumbersBedrooms2.getText().toString(),
                mNumbersPhoto1.getText().toString(),
                mNumbersPhoto2.getText().toString(),
                // Point of Interest
                mChipGarden.isChecked(),
                mChipLibrary.isChecked(),
                mChipRestaurant.isChecked(),
                mChipSchool.isChecked(),
                mChipSwimmingPool.isChecked(),
                mChipTownHall.isChecked()
        );
    }
    // ---------------------------------------------------------------------------------------------
    //                              AUTOCOMPLETE TYPE CONFIGURATION
    // ---------------------------------------------------------------------------------------------
    private void configureAutoCompleteType() {
        Log.d(TAG, "configureAutoCompleteType: ");

        final String[] TYPES = getResources().getStringArray(R.array.estate_type);
        Log.d(TAG, "configureAutoCompleteType: TYPES = "+ Arrays.toString(TYPES));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.dropdown_type,
                TYPES);
        mEstateType.setAdapter(adapter);
    }
    // ---------------------------------------------------------------------------------------------
    //                                     MANAGE DATE FIELDS
    // ---------------------------------------------------------------------------------------------
    private void manageDateFields() {
        newCalendar = Calendar.getInstance();
        displayDateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        setEntryDateField1();
        setEntryDateField2();
        setSaleDateField1();
        setSaleDateField2();
    }
    // Manage Entry Date1 Field
    private void setEntryDateField1() {
        // Create a DatePickerDialog and manage it
        mEntryDatePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDateEntryOfTheMarket = Calendar.getInstance();
                newDateEntryOfTheMarket.set(year, monthOfYear, dayOfMonth);

                // Update entryDate in ViewModel
                mEstateSearchViewModel.getDateEntryOfTheMarket1().setValue(newDateEntryOfTheMarket);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // Manage Entry Date2 Field
    private void setEntryDateField2() {
        // Create a DatePickerDialog and manage it
        mEntryDatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDateEntryOfTheMarket = Calendar.getInstance();
                newDateEntryOfTheMarket.set(year, monthOfYear, dayOfMonth);

                // Update entryDate in ViewModel
                mEstateSearchViewModel.getDateEntryOfTheMarket2().setValue(newDateEntryOfTheMarket);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // Manage Sale Date1 Field
    private void setSaleDateField1() {
        // Create a DatePickerDialog and manage it
        mSaleDatePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDateEntryOfTheMarket = Calendar.getInstance();
                newDateEntryOfTheMarket.set(year, monthOfYear, dayOfMonth);

                // Update entryDate in ViewModel
                mEstateSearchViewModel.getDateSale1().setValue(newDateEntryOfTheMarket);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // Manage Sale Date2 Field
    private void setSaleDateField2() {
        // Create a DatePickerDialog and manage it
        mSaleDatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDateEntryOfTheMarket = Calendar.getInstance();
                newDateEntryOfTheMarket.set(year, monthOfYear, dayOfMonth);

                // Update entryDate in ViewModel
                mEstateSearchViewModel.getDateSale2().setValue(newDateEntryOfTheMarket);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // ---------------------------------------------------------------------------------------------
    //                                            UI
    // ---------------------------------------------------------------------------------------------
    // Refresh the Entry date Field1
    private void refreshDateEntryOfTheMarket1(Calendar dateEntryOfTheMarket1){
        mEntryDate1.setText(displayDateFormatter.format(dateEntryOfTheMarket1.getTime()));
    }
    // Refresh the Entry date Field2
    private void refreshDateEntryOfTheMarket2(Calendar dateEntryOfTheMarket2){
        mEntryDate2.setText(displayDateFormatter.format(dateEntryOfTheMarket2.getTime()));
    }
    // Refresh the Sale date Field1
    private void refreshDateSale1(Calendar dateSale1){
        mSaleDate1.setText(displayDateFormatter.format(dateSale1.getTime()));
    }
    // Refresh the Sale date Field2
    private void refreshDateSale2(Calendar dateSale2){
        mSaleDate2.setText(displayDateFormatter.format(dateSale2.getTime()));
    }
}
