package com.openclassrooms.realestatemanager.Models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Repositories.CurrentEstateDataRepository;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentDataRepository;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class EstateCreateViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateCreateViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    private MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private MutableLiveData<LocalDateTime> mDateEntryOfTheMarket = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> mPhotos = new MutableLiveData<>();

    public enum ViewAction {
        INVALID_INPUT,
        FINISH_ACTIVITY
    }

    public EstateCreateViewModel(EstateDataRepository estateDataSource,
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
                numberOfRooms, numberOfBathrooms, numberOfBedrooms, addressWay, addressComplement,
                addressPostalCode, addressCity, addressState, chipGarden, chipLibrary, chipRestaurant,
                chipSchool, chipSwimmingPool, chipTownHall);

        if (estate != null) {

            // Change Current estate Id
            CurrentEstateDataRepository.getInstance().setCurrentEstate_Id(estate.getEstate_Id());

            mExecutor.execute(() -> {
                mEstateDataSource.createEstate(estate);
            });

            mViewActionLiveData.postValue(ViewAction.FINISH_ACTIVITY);
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
        if (chipGarden) pointsOfInterest.put("Garden","Garden");
        if (chipLibrary) pointsOfInterest.put("Library","Library");
        if (chipRestaurant) pointsOfInterest.put("Restaurant","Restaurant");
        if (chipSchool) pointsOfInterest.put("School","School");
        if (chipSwimmingPool) pointsOfInterest.put("Swimming Pool","Swimming Pool");
        if (chipTownHall) pointsOfInterest.put("Town Hall","Town Hall");
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

       return estate;
    }

    public LiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }

    public LiveData<ArrayList<String>> getPhotos() {
        return mPhotos;
    }

    public void addPhoto(String photo) {
        Log.d(TAG, "addPhoto: ");
        ArrayList<String> l = mPhotos.getValue();
        l.add(photo);
        this.mPhotos.postValue(l);
    }

    public MutableLiveData<LocalDateTime> getDateEntryOfTheMarket() {
        return mDateEntryOfTheMarket;
    }
}


