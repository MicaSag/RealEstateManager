package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.RealEstateAgentDao;
import com.openclassrooms.realestatemanager.Models.RealEstateAgent;

public class RealEstateAgentDataRepository {

    public final RealEstateAgentDao realEstateAgentDao;

    public RealEstateAgentDataRepository(RealEstateAgentDao realEstateAgentDao) {
        this.realEstateAgentDao = realEstateAgentDao;
    }

    // -- GET RealEstateAgent
    public LiveData<RealEstateAgent> getRealEstateAgent(long realEstateAgent_Id){
        return this.realEstateAgentDao.getRealEstateAgent(realEstateAgent_Id);
    }
}
