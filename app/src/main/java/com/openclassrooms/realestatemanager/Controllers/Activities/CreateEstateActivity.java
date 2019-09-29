package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;
import com.openclassrooms.realestatemanager.Models.views.EstateCreateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentDataRepository;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EmptyStackException;
import java.util.LinkedHashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateEstateActivity extends BaseActivity {

    // For debugging Mode
    private static final String TAG = CreateEstateActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_create_estate_cl) ConstraintLayout mCoordinatorLayout;
    @BindView(R.id.activity_create_estate_auto_type) AutoCompleteTextView mAutoCompleteTVType;
    @BindView(R.id.activity_create_estate_ed_price) EditText mPrice;
    @BindView(R.id.activity_create_estate_ed_description) EditText mDescription;
    @BindView(R.id.activity_create_estate_include_address_card_view) CardView mAddress;
    @BindView(R.id.address_card_view_way) EditText mAddressWay;
    @BindView(R.id.address_card_view_complement) EditText mAddressComplement;
    @BindView(R.id.address_card_view_postal_code) EditText mAddressPostalCode;
    @BindView(R.id.address_card_view_city) EditText mAddressCity;
    @BindView(R.id.address_card_view_state) EditText mAddressState;
    @BindView(R.id.activity_create_estate_ed_surface) EditText mSurface;
    @BindView(R.id.activity_create_estate_ed_numbers_rooms) EditText mNumbersRooms;
    @BindView(R.id.activity_create_estate_ed_numbers_bathrooms) EditText mNumbersBathrooms;
    @BindView(R.id.activity_create_estate_ed_numbers_bedrooms) EditText mNumbersBedrooms;
    @BindView(R.id.activity_create_estate_ed_date_entry) EditText mEntryDate;
    @BindView(R.id.activity_create_estate_bt_validate) Button mValidate;

    private EstateCreateViewModel mEstateCreateViewModel;

    // Declarations for management of the date fields with a DatePickerDialog
    private DatePickerDialog mEntryDatePickerDialog;
    private SimpleDateFormat displayDateFormatter;
    private Calendar newCalendar;

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

        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        // Configuring Toolbar
        this.configureToolbar();

        // Configure AutoComplete Type
        this.configureAutoCompleteType();

        // Configure RealEstateViewModel
        this.configureEstateCreateViewModel();

        // Management of Date Fields
        this.manageDateFields();

        // Update UI
        this.updateUI();

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
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTIONS
    // ---------------------------------------------------------------------------------------------
    @OnClick(R.id.activity_create_estate_ed_price)
    public void setPrice(View view) {
    }
    @OnClick(R.id.activity_create_estate_ed_description)
    public void setDescription(View view) {
    }
    // Click on EntryDate Field
    @OnClick(R.id.activity_create_estate_ed_date_entry)
    public void onClickEntryDate(View v) {
        mEntryDatePickerDialog.show();
    }

    // Click on Validate Button
    @OnClick(R.id.activity_create_estate_bt_validate)
    public void validate(View view) {
        Log.d(TAG, "validate: ");
        if (validateRequiredData()) {
            mEstateCreateViewModel.getEstate().setType(mAutoCompleteTVType.getText().toString());
            mEstateCreateViewModel.getEstate().setPrice(Integer.parseInt(mPrice.getText().toString()));
            mEstateCreateViewModel.getEstate().setDescription(mDescription.getText().toString());
            ArrayList<String> address = new ArrayList<>();
            address.add(mAddressWay.getText().toString());
            address.add(mAddressComplement.getText().toString());
            address.add(mAddressPostalCode.getText().toString());
            address.add(mAddressCity.getText().toString());
            address.add(mAddressState.getText().toString());
            mEstateCreateViewModel.getEstate().setAddress(address);
            mEstateCreateViewModel.getEstate().setArea(Integer.parseInt(mSurface.getText().toString()));
            mEstateCreateViewModel.getEstate().setNumberOfParts(Integer.parseInt(mNumbersRooms.getText().toString()));
            mEstateCreateViewModel.getEstate().setNumberOfBathrooms(Integer.parseInt(mNumbersBathrooms.getText().toString()));
            mEstateCreateViewModel.getEstate().setNumberOfBedrooms(Integer.parseInt(mNumbersBedrooms.getText().toString()));
            mEstateCreateViewModel.getEstate().setPhotos(new ArrayList<>(Arrays
                    .asList("https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg",
                    "https://i.ebayimg.com/images/g/kvQAAOSwEwVcxXKq/s-l500.jpg")));
            mEstateCreateViewModel.getEstate().setPointOfInterest(new ArrayList<>(Arrays
                    .asList("School Yves Eriose", "Super Market Franprix")));
            mEstateCreateViewModel.getEstate().setRealEstateAgent_Id(CurrentRealEstateAgentDataRepository.
                    getInstance().getCurrentRealEstateAgent_Id().getValue());

            // Create Estate and save it in DataBase
            mEstateCreateViewModel.createEsate();

            // Close Activity and go back to previous activity
            this.finish();
        }
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
        mAutoCompleteTVType.setAdapter(adapter);
    }
    // ---------------------------------------------------------------------------------------------
    //                                     MANAGE DATE FIELDS
    // ---------------------------------------------------------------------------------------------
    private void manageDateFields() {
        newCalendar = Calendar.getInstance();
        displayDateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        setEntryDateField();
    }

    // Manage Entry Date Field
    private void setEntryDateField() {
        // Create a DatePickerDialog and manage it
        mEntryDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                // Display date selected
                mEntryDate.setText(displayDateFormatter.format(newDate.getTime()));

                // Save date selected in the Model
                mEstateCreateViewModel.getEstate()
                        .setDateEntryOfTheMarket(DateTimeUtils.toInstant(newDate.getTime())
                                .atZone(ZoneId.systemDefault()).toLocalDateTime());
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // ---------------------------------------------------------------------------------------------
    //                                 VALIDATE REQUIRED DATA
    // ---------------------------------------------------------------------------------------------
    // Check if the required criteria are filled
    protected boolean validateRequiredData() {
        // > Required data <
        // the list of keywords required for validate the estate creation
        if (    mAutoCompleteTVType.getText().toString().equals("") ||
                mPrice.getText().toString().equals("") ||
                mDescription.getText().toString().equals("") ||
                //mAddress.getText().toString().equals("") ||
                mSurface.getText().toString().equals("") ||
                mNumbersRooms.getText().toString().equals("") ||
                mNumbersBathrooms.getText().toString().equals("") ||
                mNumbersBedrooms.getText().toString().equals("") ||
                mEntryDate.getText().toString().equals("")
        ){
            showSnackBar("Required data");
            return false;
        } else return true;
    }
    // ---------------------------------------------------------------------------------------------
    //                                            UI
    // ---------------------------------------------------------------------------------------------
    protected void updateUI() {
        Log.d(TAG, "updateUI: ");
    }
}
