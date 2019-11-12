package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

public class CurrentEstateListDataRepository {

    private static CurrentEstateListDataRepository sInstance;

    // CurrentEstate_Id
    private MutableLiveData<List<Estate>> mCurrentEstateList = new MutableLiveData<>();

    public static CurrentEstateListDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEstateListDataRepository();
        }
        return sInstance;
    }

    public CurrentEstateListDataRepository() {
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
