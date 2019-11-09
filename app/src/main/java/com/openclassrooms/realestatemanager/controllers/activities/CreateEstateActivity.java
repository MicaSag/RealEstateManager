package com.openclassrooms.realestatemanager.controllers.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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
import com.openclassrooms.realestatemanager.controllers.bases.BaseActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.views.EstateCreateViewModel;
import com.openclassrooms.realestatemanager.adapters.photoList.PhotoListAdapter;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

        // Configure AutoComplete Type
        this.configureAutoCompleteType();

        // Management of Date Fields
        this.manageDateFields();

        // Configure RecyclerView
        configureRecyclerView();

        // Configure RealEstateViewModel
        this.configureEstateCreateViewModel();
    }
    // ---------------------------------------------------------------------------------------------
    //                                        VIEW MODEL
    // ---------------------------------------------------------------------------------------------
    // Configure ViewModel
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
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_CREATE_OK,true);
                        setResult(RESULT_OK,intent);
                        // Close Activity and go back to previous activity
                        finish();
                        break;
                }
            }
        });

        // Observe a change of Date of Entry on the Market
        mEstateCreateViewModel.getDateEntryOfTheMarket().observe(this,this::refreshDateEntryOfTheMarket);
        // Observe a change of the photo list
        mEstateCreateViewModel.getPhotos().observe(this,this::refreshPhotoList);
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

        // Create the list of the photo description
        ArrayList<String> photosDescription = new ArrayList<>();
        for (int i = 0; i < mPhotoListAdapter.getItemCount(); i++) {
            View v = mRecyclerViewPhotos.getChildAt(i);
            if (v != null) {
                TextView textView = v.findViewById(R.id.photo_list_et_room);
                photosDescription.add(textView.getText().toString());
            }
        }

        mEstateCreateViewModel.createEstate(
                mAutoCompleteTVType.getText().toString(),
                mPrice.getText().toString(),
                mSurface.getText().toString(),
                mDescription.getText().toString(),
                mAddressWay.getText().toString(),
                mAddressComplement.getText().toString(),
                mAddressPostalCode.getText().toString(),
                mAddressCity.getText().toString(),
                mAddressState.getText().toString(),
                mNumbersRooms.getText().toString(),
                mNumbersBathrooms.getText().toString(),
                mNumbersBedrooms.getText().toString(),
                photosDescription,
                // Point of Interest
                mChipGarden.isChecked(),
                mChipLibrary.isChecked(),
                mChipRestaurant.isChecked(),
                mChipSchool.isChecked(),
                mChipSwimmingPool.isChecked(),
                mChipTownHall.isChecked()
        );
        Log.d(TAG, "validate: mPhotoListAdapter = "+mPhotoListAdapter.getItemCount());
        Log.d(TAG, "validate: mPhotoListAdapter = "+mPhotoListAdapter.getPhoto(0));
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
            Log.d(TAG, "onActivityResult: currentPhotoPath = "+currentPhotoPath);
            mEstateCreateViewModel.addPhoto(currentPhotoPath);
            mEstateCreateViewModel.addPhotoDescription("");
        }

        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Log.d(TAG, "onActivityResult: fullPhotoUri = "+fullPhotoUri.toString());
            mEstateCreateViewModel.addPhoto(fullPhotoUri.toString());
            mEstateCreateViewModel.addPhotoDescription("");
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
                Calendar newDateEntryOfTheMarket = Calendar.getInstance();
                newDateEntryOfTheMarket.set(year, monthOfYear, dayOfMonth);

                Instant i = DateTimeUtils.toInstant(newDateEntryOfTheMarket);
                Timestamp ts = DateTimeUtils.toSqlTimestamp(i);
                LocalDateTime ldt = DateTimeUtils.toLocalDateTime(ts);

                // Update entryDate in ViewModel
                mEstateCreateViewModel.getDateEntryOfTheMarket().setValue(ldt);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    // ---------------------------------------------------------------------------------------------
    //                                            UI
    // ---------------------------------------------------------------------------------------------
    // Refresh the Entry date Field
    private void refreshDateEntryOfTheMarket(LocalDateTime dateEntryOfTheMarket){
        mEntryDate.setText(Utils.fromLocalDateTime(dateEntryOfTheMarket));
    }
    // refresh the Photo List
    private void refreshPhotoList(List<String> photos){
        mPhotoListAdapter.setNewPhotos(photos);
        mPhotoListAdapter.setNewPhotosDescription(mEstateCreateViewModel.getPhotoDescription());
    }
}
