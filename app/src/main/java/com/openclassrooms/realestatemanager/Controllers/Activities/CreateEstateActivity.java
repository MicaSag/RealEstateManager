package com.openclassrooms.realestatemanager.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.openclassrooms.realestatemanager.Controllers.Bases.BaseActivity;
import com.openclassrooms.realestatemanager.Injections.Injection;
import com.openclassrooms.realestatemanager.Injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.Models.views.EstateCreateViewModel;
import com.openclassrooms.realestatemanager.PhotoList.PhotoListAdapter;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentDataRepository;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.ZoneId;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateEstateActivity extends BaseActivity implements PhotoListAdapter.OnPhotoClick  {

    // For debugging Mode
    private static final String TAG = CreateEstateActivity.class.getSimpleName();

    // Adding @BindView in order to indicate to ButterKnife to get & serialise it
    // - Get Coordinator Layout
    @BindView(R.id.activity_create_estate_cl) ConstraintLayout mConstraintLayout;
    @BindView(R.id.estate_auto_type) AutoCompleteTextView mAutoCompleteTVType;
    @BindView(R.id.estate_ed_price) EditText mPrice;
    @BindView(R.id.estate_ed_description) EditText mDescription;
    @BindView(R.id.estate_include_address_card_view) CardView mAddress;
    @BindView(R.id.address_card_view_way) EditText mAddressWay;
    @BindView(R.id.address_card_view_complement) EditText mAddressComplement;
    @BindView(R.id.address_card_view_postal_code) EditText mAddressPostalCode;
    @BindView(R.id.address_card_view_city) EditText mAddressCity;
    @BindView(R.id.address_card_view_state) EditText mAddressState;
    @BindView(R.id.estate_ed_surface) EditText mSurface;
    @BindView(R.id.estate_ed_numbers_rooms) EditText mNumbersRooms;
    @BindView(R.id.estate_ed_numbers_bathrooms) EditText mNumbersBathrooms;
    @BindView(R.id.estate_ed_numbers_bedrooms) EditText mNumbersBedrooms;
    @BindView(R.id.estate_ed_date_entry) EditText mEntryDate;
    @BindView(R.id.estate_rv_photos) RecyclerView mRecyclerViewPhotos;
    @BindView(R.id.estate_chip_school) Chip mChipSchool;
    @BindView(R.id.estate_chip_garden) Chip mChipGarden;
    @BindView(R.id.estate_chip_library) Chip mChipLibrary;
    @BindView(R.id.estate_chip_restaurant) Chip mChipRestaurant;
    @BindView(R.id.estate_chip_town_hall) Chip mChipTownHall;
    @BindView(R.id.estate_chip_swimming_pool) Chip mChipSwimmingPool;

    private EstateCreateViewModel mEstateCreateViewModel;

    // Declarations for management of the date fields with a DatePickerDialog
    private DatePickerDialog mEntryDatePickerDialog;
    private SimpleDateFormat displayDateFormatter;
    private Calendar newCalendar;

    // For Display list of photos
    private PhotoListAdapter mPhotoListAdapter;
    private String currentPhotoPath;

    // For use intents to retrieve photos
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_GET = 2;

    // To return the result to the parent activity
    public static final String BUNDLE_CREATE_OK = "BUNDLE_CREATE_OK";

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

        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");

        // Configure AutoComplete Type
        this.configureAutoCompleteType();

        // Configure RealEstateViewModel
        this.configureEstateCreateViewModel();

        // Management of Date Fields
        this.manageDateFields();

        // Configure RecyclerView
        configureRecyclerView();

        // Update UI
        this.updateUI();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure RealEstateViewModel
    private void configureEstateCreateViewModel(){
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mEstateCreateViewModel = ViewModelProviders.of(this, modelFactory).get(EstateCreateViewModel.class);

        mEstateCreateViewModel.getViewActionLiveData().observe(this, new Observer<EstateCreateViewModel.ViewAction>() {
            @Override
            public void onChanged(EstateCreateViewModel.ViewAction viewAction) {
                if (viewAction == null)  {
                    return;
                }

                switch (viewAction) {
                    case INVALID_INPUT:
                        showSnackBar("Required data");
                        break;

                    case FINISH_ACTIVITY:
                        finish();
                        break;
                }
            }
        });
    }
    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------
    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // Create adapter passing the list of users
        mPhotoListAdapter = new PhotoListAdapter(this.getClass(),
                Glide.with(this),
                this);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerViewPhotos.setAdapter(mPhotoListAdapter);
        // Set layout manager to position the items
        mRecyclerViewPhotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Positions an observable on the list of photos displayed in the RecyclerView
        mEstateCreateViewModel.getPhotos().observe(this,this::notifyRecyclerView);
    }
    // Refreshes the RecyclerView with the new photo list
    private void notifyRecyclerView(List<String> photos) {
        Log.d(TAG, "notifyRecyclerView() called with: photos = [" + photos + "]");
        mPhotoListAdapter.setNewData(photos);
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTIONS
    // ---------------------------------------------------------------------------------------------
    // Click on EntryDate Field
    @OnClick(R.id.estate_ed_date_entry)
    public void onClickEntryDate(View view) {
        mEntryDatePickerDialog.show();
    }
    // Click on Take Photo
    @OnClick(R.id.estate_iv_take_photo)
    public void onTakePhotoClick(View view) {
        this.dispatchTakePictureIntent();
    }
    // Click on Validate Button
    @OnClick(R.id.estate_iv_select_photo)
    public void onSelectPhotoClick(View view) {
        this.selectImage();
    }
    // Click photo of the recycler view
    @Override
    public void onPhotoClick(int position,View view) {
        Log.d(TAG, "onPhotoClick: ");
        if (view.getId() == R.id.photo_list_image) Log.d(TAG, "onClick: image");
        if (view.getId() == R.id.photo_list_bt_delete) Log.d(TAG, "onClick: button delete");
    }
    // Click on Validate Button
    @OnClick(R.id.activity_create_estate_bt_validate)
    public void validate(View view) {
        Log.d(TAG, "validate: ");

        // TODO Ne pas construire d'ArrayList
        ArrayList<String> address = new ArrayList<>();
        address.add(mAddressWay.getText().toString());
        address.add(mAddressComplement.getText().toString());
        address.add(mAddressPostalCode.getText().toString());
        address.add(mAddressCity.getText().toString());
        address.add(mAddressState.getText().toString());



        mEstateCreateViewModel.createEstate(
                mAutoCompleteTVType.getText().toString(),
                mPrice.getText().toString(), // TODO Faire le parsing dans le ViewModel
                Integer.parseInt(mSurface.getText().toString())
        );

        /*
        if (validateRequiredData()) {
            mEstateCreateViewModel.getEstate().setType();
            mEstateCreateViewModel.getEstate().setPrice(Integer.parseInt(mPrice.getText().toString()));
            mEstateCreateViewModel.getEstate().setDescription(mDescription.getText().toString());

            mEstateCreateViewModel.getEstate().setAddress(address);
            mEstateCreateViewModel.getEstate().setArea(Integer.parseInt(mSurface.getText().toString()));
            mEstateCreateViewModel.getEstate().setNumberOfParts(Integer.parseInt(mNumbersRooms.getText().toString()));
            mEstateCreateViewModel.getEstate().setNumberOfBathrooms(Integer.parseInt(mNumbersBathrooms.getText().toString()));
            mEstateCreateViewModel.getEstate().setNumberOfBedrooms(Integer.parseInt(mNumbersBedrooms.getText().toString()));
            // Chips
            HashMap<String,String> pointsOfInterest = new HashMap<>();
            mEstateCreateViewModel.getEstate().setPointOfInterest(pointsOfInterest);
            if (mChipGarden.isChecked()) mEstateCreateViewModel.getEstate().getPointOfInterest().put("Garden","Garden");
            if (mChipLibrary.isChecked()) mEstateCreateViewModel.getEstate().getPointOfInterest().put("Library","Library");
            if (mChipRestaurant.isChecked()) mEstateCreateViewModel.getEstate().getPointOfInterest().put("Restaurant","Restaurant");
            if (mChipSchool.isChecked()) mEstateCreateViewModel.getEstate().getPointOfInterest().put("School","School");
            if (mChipSwimmingPool.isChecked()) mEstateCreateViewModel.getEstate().getPointOfInterest().put("Swimming Pool","Swimming Pool");
            if (mChipTownHall.isChecked()) mEstateCreateViewModel.getEstate().getPointOfInterest().put("Town Hall","Town Hall");
            // Current Agent Id
            mEstateCreateViewModel.getEstate().setRealEstateAgent_Id(CurrentRealEstateAgentDataRepository.
                    getInstance().getCurrentRealEstateAgent_Id().getValue());

            // Create Estate and save it in DataBase
            mEstateCreateViewModel.createEstate();

            // Notify the agent that the creative went well
            // Create a intent for call Activity
            // TODO NO MORE NEEDED IIRC
            Intent intent = new Intent();
            intent.putExtra(BUNDLE_CREATE_OK,true);
            setResult(RESULT_OK,intent);
            // Close Activity and go back to previous activity
            this.finish();
        }*/
    }
    // ---------------------------------------------------------------------------------------------
    //                                     MANAGE PHOTO LIST
    // ---------------------------------------------------------------------------------------------
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.openclassrooms.realestatemanager.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = photoFile.getAbsolutePath();

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    // Create à image File name
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "estate_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",    /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
    // Selects a photo in a device location
    public void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }
    // For Manage Intents Return
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Manage Photo Take
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            mEstateCreateViewModel.getPhotos().getValue().add(currentPhotoPath);
        }

        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            mEstateCreateViewModel.getPhotos().getValue().add(fullPhotoUri.toString());
        }
        mPhotoListAdapter.notifyDataSetChanged();
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

                // TODO Save date selected in the !!View!!Model
                /*mEstateCreateViewModel.getEstate()
                        .setDateEntryOfTheMarket(DateTimeUtils.toInstant(newDate.getTime())
                                .atZone(ZoneId.systemDefault()).toLocalDateTime());*/
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // ---------------------------------------------------------------------------------------------
    //                                 VALIDATE REQUIRED DATA
    // ---------------------------------------------------------------------------------------------
    // Check if the required criteria are filled
   /* protected boolean validateRequiredData() {
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
    }*/
    // ---------------------------------------------------------------------------------------------
    //                                            UI
    // ---------------------------------------------------------------------------------------------
    protected void updateUI() {
        Log.d(TAG, "updateUI: ");
    }
}
