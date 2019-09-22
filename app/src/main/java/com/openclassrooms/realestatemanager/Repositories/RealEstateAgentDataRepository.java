package com.openclassrooms.realestatemanager.Repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.RealEstateAgentDao;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;

public class RealEstateAgentDataRepository {

    public final RealEstateAgentDao mRealEstateAgentDao;

    public RealEstateAgentDataRepository(RealEstateAgentDao realEstateAgentDao) {
        mRealEstateAgentDao = realEstateAgentDao;
    }

    // --- GET ---
    // ===========

    // Return an RealEstateAgent
    public LiveData<RealEstateAgent> getRealEstateAgent(long realEstateAgent_Id){
        return mRealEstateAgentDao.getRealEstateAgent(realEstateAgent_Id);
    }

    // --- CREATE ---
    // ==============

    // Create a new RealEstateAgent
    public void createRealEstateAgent(RealEstateAgent realEstateAgent) {
        mRealEstateAgentDao.createRealEstateAgent(realEstateAgent);
    }

    // --- DELETE/S ---
    // ================

    // Delete an RealEstateAgent
    public void deleteRealEstateAgent(long realEstateAgent_Id){
        mRealEstateAgentDao.deleteRealEstateAgent(realEstateAgent_Id);
    }
}
