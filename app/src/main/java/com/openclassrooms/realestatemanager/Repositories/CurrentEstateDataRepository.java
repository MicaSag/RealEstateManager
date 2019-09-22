package com.openclassrooms.realestatemanager.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.Database.Dao.EstateDao;

public class CurrentEstateDataRepository {

    public final EstateDao mEstateDao;

    private static CurrentEstateDataRepository sInstance;

    // CurrentEstate_Id
    private MutableLiveData<Long> mCurrentEstate_Id = new MutableLiveData<>();

    public static CurrentEstateDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEstateDataRepository(null);
        }
        return sInstance;
    }

    public CurrentEstateDataRepository(EstateDao estateDao) {
        mEstateDao = estateDao;

        // The default CurrentEstate_Id is number 1
        mCurrentEstate_Id.setValue(1L);
    }
    // --- GET ---
    // ===========

    // Return the CurrentEstate_Id
    public LiveData<Long> getCurrentEstate_Id(){
        return mCurrentEstate_Id;
    }

    // --- SET ---
    // ===========

    // Set the CurrentEstate_Id
    public void setCurrentEstate_Id(long currentEstate_Id) {
        mCurrentEstate_Id.setValue(currentEstate_Id);
    }
}
