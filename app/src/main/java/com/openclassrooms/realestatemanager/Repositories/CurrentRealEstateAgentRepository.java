package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.Database.Dao.RealEstateAgentDao;

public class CurrentRealEstateAgentRepository {

    public final RealEstateAgentDao realEstateAgentDao;

    private static CurrentRealEstateAgentRepository sInstance;

    public static CurrentRealEstateAgentRepository getInstance() {
        if (sInstance==null)sInstance= new CurrentRealEstateAgentRepository(null);
        return sInstance;
    }

    private MutableLiveData<Long> mCurrentRealEstateAgent_Id = new MutableLiveData<>();

    public CurrentRealEstateAgentRepository(RealEstateAgentDao realEstateAgentDao) {
        this.realEstateAgentDao = realEstateAgentDao;
        mCurrentRealEstateAgent_Id.setValue(1L);
    }

    // -- GET RealEstateAgent
    public LiveData<Long> getRealEstateAgent_Id(){
        return mCurrentRealEstateAgent_Id;
    }

    public void setCurrentRealEstateAgent_Id(long mCurrentRealEstateAgent_Id) {
        this.mCurrentRealEstateAgent_Id.setValue(mCurrentRealEstateAgent_Id);
    }
}
