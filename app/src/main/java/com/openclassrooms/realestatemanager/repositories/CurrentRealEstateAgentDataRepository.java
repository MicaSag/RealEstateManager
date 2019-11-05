package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.database.dao.RealEstateAgentDao;

public class CurrentRealEstateAgentDataRepository {

    public final RealEstateAgentDao mRealEstateAgentDao;

    private static CurrentRealEstateAgentDataRepository sInstance;

    // CurrentRealEstateAgent_Id
    private MutableLiveData<Long> mCurrentRealEstateAgent_Id = new MutableLiveData<>();

    public static CurrentRealEstateAgentDataRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentRealEstateAgentDataRepository(null);
        }
        return sInstance;
    }

    public CurrentRealEstateAgentDataRepository(RealEstateAgentDao realEstateAgentDao) {
        mRealEstateAgentDao = realEstateAgentDao;

        // The default CurrentRealEstateAgent_Id is number 1
        mCurrentRealEstateAgent_Id.setValue(1L);
    }
    // --- GET ---
    // ===========

    // Return the CurrentRealEstateAgent_Id
    public LiveData<Long> getCurrentRealEstateAgent_Id(){
        return mCurrentRealEstateAgent_Id;
    }

    // --- SET ---
    // ===========

    // Set the CurrentRealEstateAgent_Id
    public void setCurrentRealEstateAgent_Id(long mCurrentRealEstateAgent_Id) {
        this.mCurrentRealEstateAgent_Id.setValue(mCurrentRealEstateAgent_Id);
    }
}
