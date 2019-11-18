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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class CreateEstateActivity extends BaseActivity  implements  PhotoListAdapter.OnPhotoClick,
                                                                    PhotoListAdapter.OnTextChange{

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
    @BindView(R.id.estate_iv_video_preview) ImageView mVideoPreview;

    private EstateCreateViewModel mEstateCreateViewModel;

    // Declarations for management of the date fields with a DatePickerDialog
    private DatePickerDialog mEntryDatePickerDialog;
    private SimpleDateFormat displayDateFormatter;
    private Calendar newCalendar;

    // For Display list of photos
    private PhotoListAdapter mPhotoListAdapter;
    private String mCurrentPhotoPath;
    // For Display video
    private String mCurrentVideoPath;

    // For use intents to retrieve photos & video
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_GET = 10;
    static final int REQUEST_TAKE_VIDEO = 2;
    static final int REQUEST_VIDEO_GET = 20;

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
        mEstateCreateViewModel.getPhotos().observe(this,this::refreshPhotos);
        // Observe a change of the video
        mEstateCreateViewModel.getVideo().observe(this,this::refreshVideo);
    }
    // --------------------------------------------------------------------------------------------
    //                                    CONFIGURATION
    // --------------------------------------------------------------------------------------------
    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // Create adapter passing the list of users
        mPhotoListAdapter = new PhotoListAdapter(this.getClass(),
                Glide.with(this),
                this,
                this);
        // Attach the adapter to the recyclerView to populate items
        mRecyclerViewPhotos.setAdapter(mPhotoListAdapter);
        // Set layout manager to position the items
        mRecyclerViewPhotos.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
    }
    // ---------------------------------------------------------------------------------------------
    //                                           ACTIONS
    // ---------------------------------------------------------------------------------------------
    // Click on EntryDate Field
    @OnClick(R.id.estate_ed_date_entry)
    public void onClickEntryDate(View view) {
        mEntryDatePickerDialog.show();
    }

    // PHOTOS ACTIONS
    // ---------------
    // Click on Take Photo
    @OnClick(R.id.estate_iv_take_photo)
    public void onTakePhotoClick(View view) {
        this.dispatchTakePictureIntent();
    }
    // Click on Select Photo
    @OnClick(R.id.estate_iv_select_photo)
    public void onSelectPhotoClick(View view) {
        this.selectImage();
    }
    // Click photo of the recycler view
    @Override
    public void onPhotoClick(int position,View view) {
        Log.d(TAG, "onPhotoClick: ");
        if (view.getId() == R.id.photo_list_image) Log.d(TAG, "onClick: image");
        if (view.getId() == R.id.photo_list_bt_delete) {
            mEstateCreateViewModel.getPhotos().getValue().remove(position);
            mEstateCreateViewModel.getPhotoDescription().remove(position);
            this.refreshPhotos(mEstateCreateViewModel.getPhotos().getValue());
        }
    }
    // ---------------
    //  VIDEO ACTIONS
    // ---------------
    // Click on Take Video
    @OnClick(R.id.estate_iv_take_video)
    public void onTakeVideoClick(View view) {
        this.dispatchTakeVideoIntent();
    }
    // Click on Select Video
    @OnClick(R.id.estate_iv_select_video)
    public void onSelectVideoClick(View view) {
        this.selectVideo();
    }
    // Click Video View
    @OnClick(R.id.estate_iv_video_preview)
    public void onVideoPreviewClick(View view) {
        Log.d(TAG, "onVideoPreviewClick: ");

        // Start Video Activity
        Utils.startActivity(this,
                VideoActivity.class,
               VideoActivity.BUNDLE_VIDEO_ACTIVITY_URI,
                mEstateCreateViewModel.getVideo().getValue());
    }
    // ---------------

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

    @Override
    public void onTextChange(int position,String value) {
        Log.d(TAG, "onTextChange() called with: position = [" + position + "], " +
                "value = [" + value + "]");

        // Update PhotoDescription
        mEstateCreateViewModel.getPhotoDescription().set(position,value);
    }

    // Click on Validate Button
    @OnClick(R.id.activity_create_estate_bt_validate)
    public void validate(View view) {
        Log.d(TAG, "validate: ");

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
                Log.d(TAG, "dispatchTakePictureIntent: photoURI = "+photoURI);

                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                Log.d(TAG, "dispatchTakePictureIntent: mCurrentPhotoPath = "+mCurrentPhotoPath);

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
    // ---------------------------------------------------------------------------------------------
    //                                     MANAGE VIDEO
    // ---------------------------------------------------------------------------------------------
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File videoFile = null;
            try {
                videoFile = createVideoFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (videoFile != null) {
                Uri videoURI = FileProvider.getUriForFile(this,
                        "com.openclassrooms.realestatemanager.fileprovider",
                        videoFile);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);

                // Save a file: path for use with ACTION_VIEW intents
                mCurrentVideoPath = videoFile.getAbsolutePath();

                startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);
            }
        }
    }
    // Create à image File name
    private File createVideoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "estate_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File video = File.createTempFile(
                videoFileName,  /* prefix */
                ".mp4",    /* suffix */
                storageDir      /* directory */
        );
        return video;
    }
    // Selects a photo in a device location
    public void selectVideo() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/mp4");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_GET);
        }
    }
    // ---------------------------------------------------------------------------------------------
    //                              MANGE INTENTS RETURN
    // ---------------------------------------------------------------------------------------------
    // For Manage Intents Return
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], " +
                "resultCode = [" + resultCode + "], data = [" + data + "]");

        // Manage Photo Take
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: mCurrentPhotoPath = "+mCurrentPhotoPath);
            mEstateCreateViewModel.addPhoto(mCurrentPhotoPath);
            mEstateCreateViewModel.addPhotoDescription("");
        }

        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Log.d(TAG, "onActivityResult: fullPhotoUri = "+fullPhotoUri.toString());
            mEstateCreateViewModel.addPhoto(fullPhotoUri.toString());
            mEstateCreateViewModel.addPhotoDescription("");
        }

        if (requestCode == REQUEST_TAKE_VIDEO && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: mCurrentVideoPath = "+mCurrentVideoPath);
            mEstateCreateViewModel.setVideo(mCurrentVideoPath);
        }

        if (requestCode == REQUEST_VIDEO_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Log.d(TAG, "onActivityResult: fullPhotoUri = "+fullPhotoUri.toString());
            mEstateCreateViewModel.setVideo(fullPhotoUri.toString());
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
    private void refreshPhotos(List<String> photos){
        Log.d(TAG, "refreshPhotos() called with: photos = [" + photos + "]");
        mPhotoListAdapter.setNewPhotos(photos);
        mPhotoListAdapter.setNewPhotosDescription(mEstateCreateViewModel.getPhotoDescription());
    }
    // refresh the video
    private void refreshVideo(String video){
        Log.d(TAG, "refreshVideo() called with: video = [" + video + "]");
        Glide.with(this) //SHOWING PREVIEW OF VIDEO
                .load(video)
                .apply(RequestOptions.centerCropTransform())
                .into(this.mVideoPreview);
    }
}
