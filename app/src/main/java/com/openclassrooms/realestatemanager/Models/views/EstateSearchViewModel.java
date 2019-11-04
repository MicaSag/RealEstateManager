package com.openclassrooms.realestatemanager.Models.views;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.Models.Estate;
import com.openclassrooms.realestatemanager.Repositories.CurrentEstateDataRepository;
import com.openclassrooms.realestatemanager.Repositories.CurrentEstateListDataRepository;
import com.openclassrooms.realestatemanager.Repositories.CurrentRealEstateAgentDataRepository;
import com.openclassrooms.realestatemanager.Repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.Utils.Converters;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

public class EstateSearchViewModel extends ViewModel {

    // For debugging Mode
    private static final String TAG = EstateSearchViewModel.class.getSimpleName();

    // REPOSITORIES
    private final EstateDataRepository mEstateDataSource;
    private final Executor mExecutor;

    // DATA
    private MutableLiveData<ViewAction> mViewActionLiveData = new MutableLiveData<>();
    private MutableLiveData<Calendar> mDateEntryOfTheMarket1 = new MutableLiveData<>();
    private MutableLiveData<Calendar> mDateEntryOfTheMarket2 = new MutableLiveData<>();
    private MutableLiveData<Calendar> mDateSale1 = new MutableLiveData<>();
    private MutableLiveData<Calendar> mDateSale2 = new MutableLiveData<>();

    public enum ViewAction {
        INVALID_INPUT,
        FINISH_ACTIVITY,
        SEARCH_NO_RESULT
    }

    public EstateSearchViewModel(EstateDataRepository estateDataSource,
                                 Executor executor) {
        mEstateDataSource = estateDataSource;
        mExecutor = executor;
    }

    public void searchEstate(
            @NonNull String type,
            String price1, String price2,
            String area1,String area2,
            String city,
            String numberOfRooms1,String numberOfRooms2,
            String numberOfBathrooms1, String numberOfBathrooms2,
            String numberOfBedrooms1, String numberOfBedrooms2,
            String numberOfPhoto1, String numberOfPhoto2,
            Boolean chipGarden,
            Boolean chipLibrary,
            Boolean chipRestaurant,
            Boolean chipSchool,
            Boolean chipSwimmingPool,
            Boolean chipTownHall
    ) {
        Log.d(TAG, "searchEstate: ");

        // Control of digital data
        String controlDigitalDataResult = controlDigitalData(
                price1, price2,
                area1, area2,
                numberOfRooms1, numberOfRooms2,
                numberOfBathrooms1, numberOfBathrooms2,
                numberOfBedrooms1, numberOfBedrooms2,
                numberOfPhoto1, numberOfPhoto2);

        if (controlDigitalDataResult == null) {

            long agentId = CurrentRealEstateAgentDataRepository.getInstance().getCurrentRealEstateAgent_Id().getValue();
            int priceMin = Integer.parseInt(price1), priceMax = Integer.parseInt(price2);
            int areaMin = Integer.parseInt(area1), areaMax = Integer.parseInt(area2);
            int partMin = Integer.parseInt(numberOfRooms1), partMax = Integer.parseInt(numberOfRooms2);
            int bathroomMin = Integer.parseInt(numberOfBathrooms1), bathroomMax = Integer.parseInt(numberOfBathrooms2);
            int bedroomMin = Integer.parseInt(numberOfBedrooms1), bedroomMax = Integer.parseInt(numberOfBedrooms2);
            int photoMin = Integer.parseInt(numberOfPhoto1), photoMax = Integer.parseInt(numberOfPhoto2);

            // Query string
            String queryString = new String();
            List<Object> args = new ArrayList<>();
            queryString += "SELECT * FROM Estate WHERE realEstateAgent_Id =?";
            args.add(agentId);
            if (!type.isEmpty()){
                queryString += " AND type =?";
                args.add(type);
            }
            if (priceMax>0) {
                queryString += " AND price BETWEEN ? AND ?";
                args.add(priceMin);
                args.add(priceMax);
            }
            if (areaMax>0) {
                queryString += " AND area BETWEEN ? AND ?";
                args.add(areaMin);
                args.add(areaMax);
            }
            if (partMax>0) {
                queryString += " AND numberOfParts BETWEEN ? AND ?";
                args.add(partMin);
                args.add(partMax);
            }
            if (bathroomMax>0) {
                queryString += " AND numberOfBathrooms BETWEEN ? AND ?";
                args.add(bathroomMin);
                args.add(bathroomMax);
            }
            if (bedroomMax>0) {
                queryString += " AND numberOfBedrooms BETWEEN ? AND ?";
                args.add(bedroomMin);
                args.add(bedroomMax);
            }
            // DATES
            LocalDateTime localDT1;
            LocalDateTime localDT2;
            // Entry date of Market
            if (mDateEntryOfTheMarket1.getValue() !=null && mDateEntryOfTheMarket2.getValue() !=null ) {
                queryString += " AND dateEntryOfTheMarket > ? AND dateEntryOfTheMarket < ?";

                localDT1 = DateTimeUtils.toInstant(mDateEntryOfTheMarket1.getValue().getTime())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime();
                localDT2 = DateTimeUtils.toInstant(mDateEntryOfTheMarket2.getValue().getTime())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime();

                args.add(Converters.dateToTimestamp(localDT1));
                args.add(Converters.dateToTimestamp(localDT2));
                Log.d(TAG, "searchEstate: mDateEntryOfTheMarket1 = "+localDT1.toString()
                        +" : "+Converters.dateToTimestamp(localDT1));
                Log.d(TAG, "searchEstate: mDateEntryOfTheMarket2 = "+localDT2.toString()
                        +" : "+Converters.dateToTimestamp(localDT2));
            }
            // Date of Sale
            if (mDateSale1.getValue() !=null && mDateSale2.getValue() !=null ) {
                queryString += " AND dateOfSale BETWEEN ? AND ?";

                localDT1 = DateTimeUtils.toInstant(mDateSale1.getValue().getTime())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime();
                localDT2 = DateTimeUtils.toInstant(mDateSale2.getValue().getTime())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime();

                args.add(Converters.dateToTimestamp(localDT1));
                args.add(Converters.dateToTimestamp(localDT2));
            }

            Log.d(TAG, "searchEstate: queryString = "+queryString);
            // Build SimpleSQLiteQuery
            SimpleSQLiteQuery simpleQuery = new SimpleSQLiteQuery(queryString,args.toArray());
            // Submit Request and get the result
            mEstateDataSource.getEstates(simpleQuery).observeForever(new Observer<List<Estate>>() {
                @Override
                public void onChanged(List<Estate> estates) {

                    if (estates.size() == 0) mViewActionLiveData.postValue(ViewAction.SEARCH_NO_RESULT);
                    else {
                        List<Estate> localEstate = estates;
                        Log.d(TAG, "onChanged: 1:localEstate.size() = "+localEstate.size());
                        // Filter on City argument
                        if (!city.isEmpty()) localEstate = filterEstatesCity(estates,city);
                        // Filter on numberOfPhoto
                        if (photoMax > 0) localEstate = filterEstatesNumberOfPhoto(localEstate,photoMin,photoMax);
                        // Filter on interestPoint
                        if (chipGarden) localEstate = filterEstatesInterestPoint(localEstate,"Garden");
                        if (chipLibrary) localEstate = filterEstatesInterestPoint(localEstate,"Library");
                        if (chipRestaurant) localEstate = filterEstatesInterestPoint(localEstate,"Restaurant");
                        if (chipSchool) localEstate = filterEstatesInterestPoint(localEstate,"School");
                        if (chipSwimmingPool) localEstate = filterEstatesInterestPoint(localEstate,"Swimming Pool");
                        if (chipTownHall) localEstate = filterEstatesInterestPoint(localEstate,"Town Hall");

                        // Update Current Estate List
                        Log.d(TAG, "onChanged: estates.size() = "+estates.size());
                        Log.d(TAG, "onChanged: 2:localEstate.size() = "+localEstate.size());
                        if (localEstate.size() == 0) mViewActionLiveData.postValue(ViewAction.SEARCH_NO_RESULT);
                        else {
                            CurrentEstateListDataRepository.getInstance()
                                    .setCurrentEstateList(localEstate);
                            CurrentEstateDataRepository.getInstance()
                                    .setCurrentEstate_Id(localEstate.get(0).getEstate_Id());
                            mViewActionLiveData.postValue(ViewAction.FINISH_ACTIVITY);
                        }
                    }
                }
            });
        } else {
            Log.d(TAG, "searchEstate: Query is Empty");
            mViewActionLiveData.setValue(ViewAction.INVALID_INPUT);
        }
    }

    // Retrieves only the Estate list of the city specified as a parameter
    private List<Estate> filterEstatesCity(List<Estate> estates, String city) {
        List<Estate> localEstate = new ArrayList<>();

        for (Estate estate : estates) {
            if (estate.getAddress().get(3).equals(city)) {
                localEstate.add(estate);
            }
        }
        return localEstate;
    }
    // Retrieves only the Estate list of the number of photo specified as a parameter
    private List<Estate> filterEstatesNumberOfPhoto(List<Estate> estates, int photoMin, int photoMax) {
        List<Estate> localEstate = new ArrayList<>();

        for (Estate estate : estates) {
            if (    estate.getPhotos().size() >= photoMin &&
                    estate.getPhotos().size() <= photoMax   ) {
                localEstate.add(estate);
            }
        }
        return localEstate;
    }
    // Retrieves only the Estate list of the interest point specified as a parameter
    private List<Estate> filterEstatesInterestPoint(List<Estate> estates,String interestPoint)
    {
        List<Estate> localEstate = new ArrayList<>();

        for (Estate estate : estates) {
            if (estate.getPointOfInterest().containsKey(interestPoint)) localEstate.add(estate);
        }
        return localEstate;
    }
    // Control digital Data
    private String controlDigitalData(
            String price1,
            String price2,
            String area1,
            String area2,
            String numberOfRooms1,
            String numberOfRooms2,
            String numberOfBathrooms1,
            String numberOfBathrooms2,
            String numberOfBedrooms1,
            String numberOfBedrooms2,
            String numberOfPhotos1,
            String numberOfPhotos2
    ) {
        // -----------------------------------------------
        // Price1 Control
        int priceAsNumber1;
        try {
            priceAsNumber1 = Integer.parseInt(price1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "price Mini";
        }
        // Price2 Control
        int priceAsNumber2;
        try {
            priceAsNumber2 = Integer.parseInt(price2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "price Maxi";
        }
        // Price2 > Price1
        if (priceAsNumber1 > priceAsNumber2){
            return "price Mini > price Maxi";
        }
        // -----------------------------------------------
        // Area1 Control
        int areaAsNumber1;
        try {
            areaAsNumber1 = Integer.parseInt(area1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of part Mini";
        }
        // Area2 Control
        int areaAsNumber2;
        try {
            areaAsNumber2 = Integer.parseInt(area2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of part Maxi";
        }
        // Area2 > Area1
        if (areaAsNumber1 > areaAsNumber2){
            return "number of part Min > number of part Maxi";
        }
        // -----------------------------------------------
        // Number of Rooms1 Control
        int numberOfRoomsAsNumber1;
        try {
            numberOfRoomsAsNumber1 = Integer.parseInt(numberOfRooms1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of room Mini";
        }
        // Number of Rooms2 Control
        int numberOfRoomsAsNumber2;
        try {
            numberOfRoomsAsNumber2 = Integer.parseInt(numberOfRooms2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of room Max";
        }
        // numberOfRoomsAsNumber1 > numberOfRoomsAsNumber2
        if (numberOfRoomsAsNumber1 > numberOfRoomsAsNumber2){
            return "number of room Mini > number of room Max";
        }
        // -----------------------------------------------
        //  Number of Bathrooms1 Control
        int numberOfBathroomsAsNumber1;
        try {
            numberOfBathroomsAsNumber1 = Integer.parseInt(numberOfBathrooms1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of bathroom Mini";
        }
        //  Number of Bathrooms2 Control
        int numberOfBathroomsAsNumber2;
        try {
            numberOfBathroomsAsNumber2 = Integer.parseInt(numberOfBathrooms2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of bathroom Max";
        }
        // numberOfBathroomsAsNumber1 > numberOfBathroomsAsNumber2
        if (numberOfBathroomsAsNumber1 > numberOfBathroomsAsNumber2){
            return "number of bathroom Min > number of bathroom Max";
        }
        // -----------------------------------------------
        //  Number of Bedrooms1 Control
        int numberOfBedroomsAsNumber1;
        try {
            numberOfBedroomsAsNumber1 = Integer.parseInt(numberOfBedrooms1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of bedroom Mini";
        }
        //  Number of Bedrooms2 Control
        int numberOfBedroomsAsNumber2;
        try {
            numberOfBedroomsAsNumber2 = Integer.parseInt(numberOfBedrooms2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of bedroom Max";
        }
        // numberOfBedroomsAsNumber1 > numberOfBedroomsAsNumber2
        if (numberOfBedroomsAsNumber1 > numberOfBedroomsAsNumber2){
            return "number of bedroom Min > number of bedroom Max";
        }
        // -----------------------------------------------
        //  Number of Photos1 Control
        int numberOfPhotosAsNumber1;
        try {
            numberOfPhotosAsNumber1 = Integer.parseInt(numberOfPhotos1);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of photo Mini";
        }
        //  Number of Photos2 Control
        int numberOfPhotosAsNumber2;
        try {
            numberOfPhotosAsNumber2 = Integer.parseInt(numberOfPhotos2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "number of photo Max";
        }
        // numberOfPhotosAsNumber1 > numberOfPhotosAsNumber2
        if (numberOfPhotosAsNumber1 > numberOfPhotosAsNumber2){
            return "number of photo Min > number of photo Max";
        }
        // -----------------------------------------------
        // Entry date of the Market
        if (mDateEntryOfTheMarket1.getValue() != null && mDateEntryOfTheMarket2.getValue() != null) {
            if (mDateEntryOfTheMarket1.getValue().getTime()
                    .compareTo(mDateEntryOfTheMarket2.getValue().getTime()) > 0) {
                return "entry date Min > entry date Max";
            }
        }
        // -----------------------------------------------
        // Sale date
        if (mDateSale1.getValue() != null && mDateSale2.getValue() != null) {
            if (mDateSale1.getValue().getTime()
                    .compareTo(mDateSale2.getValue().getTime()) > 0) {
                return "sale date Min > sale date Max";
            }
        }
        return null;
    }

    public LiveData<ViewAction> getViewActionLiveData() {
        return mViewActionLiveData;
    }

    public MutableLiveData<Calendar> getDateEntryOfTheMarket1() {
        return mDateEntryOfTheMarket1;
    }

    public MutableLiveData<Calendar> getDateEntryOfTheMarket2() {return mDateEntryOfTheMarket2; }

    public MutableLiveData<Calendar> getDateSale1() {return mDateSale1; }

    public MutableLiveData<Calendar> getDateSale2() {return mDateSale2; }
}


