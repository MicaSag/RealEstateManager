package com.openclassrooms.realestatemanager.models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.repositories.CurrentRealEstateAgentDataRepository;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

public class CreateEstateViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = CreateEstateViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    private MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private MutableLiveData<LocalDateTime> mDateEntryOfTheMarket = new MutableLiveData<>();
    // For Manage Photos
    private MutableLiveData<ArrayList<String>> mPhotos = new MutableLiveData<>();
    private ArrayList<String> mPhotoDescription = new ArrayList<>();
    // For Manage Video
    private MutableLiveData<String> mVideo = new MutableLiveData<>();

    public enum ViewAction {
        INVALID_INPUT,
        FINISH_ACTIVITY
    }

    public CreateEstateViewModel(EstateDataRepository estateDataSource,
                                 Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;
        mPhotos.setValue(new ArrayList<>());
        mDateEntryOfTheMarket.setValue(LocalDateTime.now());
    }

    public void createEstate(
            @NonNull String type,
            String price,
            String area,
            String description,
            String addressWay,
            String addressComplement,
            String addressPostalCode,
            String addressCity,
            String addressState,
            String numberOfRooms,
            String numberOfBathrooms,
            String numberOfBedrooms,
            Boolean chipGarden,
            Boolean chipLibrary,
            Boolean chipRestaurant,
            Boolean chipSchool,
            Boolean chipSwimmingPool,
            Boolean chipTownHall
    ) {
        Estate estate = validateData(type, price, area, description,
                numberOfRooms, numberOfBathrooms, numberOfBedrooms, addressWay,
                addressComplement, addressPostalCode, addressCity, addressState, chipGarden,
                chipLibrary, chipRestaurant,chipSchool, chipSwimmingPool, chipTownHall);

        if (estate != null) {

            mExecutor.execute(() -> {
                mEstateDataSource.createEstate(estate);
            });
            mViewActionLiveData.setValue(ViewAction.FINISH_ACTIVITY);
        } else {
            mViewActionLiveData.setValue(ViewAction.INVALID_INPUT);
        }
    }

    private Estate validateData(
            @NonNull   String type,
            String price,
            String area,
            String description,
            String numberOfRooms,
            String numberOfBathrooms,
            String numberOfBedrooms,
            String addressWay,
            String addressComplement,
            String addressPostalCode,
            String addressCity,
            String addressState,
            Boolean chipGarden,
            Boolean chipLibrary,
            Boolean chipRestaurant,
            Boolean chipSchool,
            Boolean chipSwimmingPool,
            Boolean chipTownHall
    ) {
        Estate estate = new Estate();
        // -------------------------
        // Type Control
        if(type.isEmpty()) {
            return null;
        }
        estate.setType(type);
        // -------------------------
        // Price Control
        int priceAsNumber;
        try {
            priceAsNumber = Integer.parseInt(price);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        estate.setPrice(priceAsNumber);
        // -------------------------
        // Area Control
        int areaAsNumber;
        try {
            areaAsNumber = Integer.parseInt(area);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        estate.setArea(areaAsNumber);
        // -------------------------
        // Description Control
        if(description.isEmpty()) {
            return null;
        }
        estate.setDescription(description);
        // -------------------------
        // Number of Rooms Control
        int numberOfRoomsAsNumber;
        try {
            numberOfRoomsAsNumber = Integer.parseInt(numberOfRooms);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        estate.setNumberOfParts(numberOfRoomsAsNumber);
        // -------------------------
        //  Number of Bathrooms Control
        int numberOfBathroomsAsNumber;
        try {
            numberOfBathroomsAsNumber = Integer.parseInt(numberOfBathrooms);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        estate.setNumberOfBathrooms(numberOfBathroomsAsNumber);
        // -------------------------
        //  Number of Bedrooms Control
        int numberOfBedroomsAsNumber;
        try {
            numberOfBedroomsAsNumber = Integer.parseInt(numberOfBedrooms);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        estate.setNumberOfBedrooms(numberOfBedroomsAsNumber);
        // -------------------------
        // Address
        // -- Way
        ArrayList<String> address = new ArrayList<>();
        if(addressWay.isEmpty()) {
            return null;
        }
        address.add(addressWay);
        // -- Complement Not required
        address.add(addressComplement);
        if(addressPostalCode.isEmpty()) {
            return null;
        }
        // -- City
        address.add(addressPostalCode);
        if(addressCity.isEmpty()) {
            return null;
        }
        address.add(addressCity);
        // -- State
        if(addressState.isEmpty()) {
            return null;
        }
        address.add(addressState);
        estate.setAddress(address);
        // -------------------------
        // Chips Not required
        HashMap<String,String> pointsOfInterest = new HashMap<>();
        if (chipSchool) pointsOfInterest.put("School","School");
        if (chipSwimmingPool) pointsOfInterest.put("Swimming Pool","Swimming Pool");
        if (chipTownHall) pointsOfInterest.put("Town Hall","Town Hall");
        if (chipLibrary) pointsOfInterest.put("Library","Library");
        if (chipGarden) pointsOfInterest.put("Garden","Garden");
        if (chipRestaurant) pointsOfInterest.put("Restaurant","Restaurant");
        estate.setPointOfInterest(pointsOfInterest);
        // -------------------------
        // Entry date of Market
        if(mDateEntryOfTheMarket.getValue() == null){
            return null;
        }
        estate.setDateEntryOfTheMarket(mDateEntryOfTheMarket.getValue());
        // -------------------------
        // Current Agent Id
        if(CurrentRealEstateAgentDataRepository.
                getInstance().getCurrentRealEstateAgent_Id().getValue().toString().isEmpty()){
            return null;
        }
        estate.setRealEstateAgent_Id(CurrentRealEstateAgentDataRepository.
                getInstance().getCurrentRealEstateAgent_Id().getValue());
        // -------------------------
        // Photo List
        if(mPhotos.getValue().size() == 0){
            return null;
        }
        estate.setPhotos(mPhotos.getValue());
        // -------------------------
        // PhotosDescription Not required
        // -------------------------
        // Photos Description
        estate.setPhotosDescription(mPhotoDescription);
        // -------------------------
        // Video Not required
        // -------------------------
        // Photos Description
        estate.setVideo(mVideo.getValue());

       return estate;
    }

    public LiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }

    // For Manage Photos
    public LiveData<ArrayList<String>> getPhotos() {
        return mPhotos;
    }

    public void addPhoto(String photo) {
        Log.d(TAG, "addPhoto: ");
        ArrayList<String> l = mPhotos.getValue();
        l.add(photo);
        this.mPhotos.postValue(l);
    }
    public ArrayList<String> getPhotoDescription() {
        return mPhotoDescription;
    }

    public void addPhotoDescription(String photoDescription) {
        this.mPhotoDescription.add(photoDescription);
    }

    // For Manage Video
    public LiveData<String> getVideo() {
        return mVideo;
    }
    public void setVideo(String video) {
        mVideo.setValue(video);
    }
    // Manage Date
    public MutableLiveData<LocalDateTime> getDateEntryOfTheMarket() {
        return mDateEntryOfTheMarket;
    }
}


