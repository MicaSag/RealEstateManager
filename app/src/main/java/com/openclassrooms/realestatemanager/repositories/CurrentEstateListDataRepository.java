package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

public class CurrentEstateListDataRepository {

    public final EstateDao mEstateDao;

    private static CurrentEstateListDataRepository sInstance;

    // CurrentEstate_Id
    private MutableLiveData<List<Estate>> mCurrentEstateList = new MutableLiveData<>();

    public static CurrentEstateListDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEstateListDataRepository(null);
        }
        return sInstance;
    }

    public CurrentEstateListDataRepository(EstateDao estateDao) {
        mEstateDao = estateDao;
    }

    // --- GET ---
    // ===========

    // Return the CurrentEstateList
    public LiveData<List<Estate>> getCurrentEstateList(){
        return mCurrentEstateList;
    }

    // --- SET ---
    // ===========

    // Set the CurrentEstateList
    public void setCurrentEstateList(List<Estate> estates) {
        mCurrentEstateList.setValue(estates);
    }
}