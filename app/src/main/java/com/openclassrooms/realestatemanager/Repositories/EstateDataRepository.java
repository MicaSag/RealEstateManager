package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.EstateDao;
import com.openclassrooms.realestatemanager.Models.Estate;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao mEstateDao;

    public EstateDataRepository(EstateDao estateDao) {
        mEstateDao = estateDao;
    }

    // --- GET ---
    // ===========

    // Return Estates managed by an Agent
    public LiveData<List<Estate>> getEstates(long realEstateAgent_Id){
        return mEstateDao.getEstates(realEstateAgent_Id);
    }

    // Return all Estates from database
    public LiveData<List<Estate>> getEstates(){
        return mEstateDao.getEstates();
    }

    // Returns a specific Estate
    public LiveData<Estate> getEstate(long estate_Id){
        return mEstateDao.getEstate(estate_Id);
    }

    // --- CREATE ---
    // ==============

    // Create a new Estate
    public void createEstate(Estate estate){
        mEstateDao.insertEstate(estate);
    }

    // --- DELETE/S ---
    // ================

    // Delete a specific Estate
    public void deleteEstate(long estate_Id){
        mEstateDao.deleteEstate(estate_Id);
    }

    // Delete Estates from an Agent
    public void deleteEstates(long realEstateAgent_Id){
        mEstateDao.deleteEstates(realEstateAgent_Id);
    }

    // --- UPDATE ---
    // ==============

    // Update a specific Estate
    public void updateEstate(Estate estate){
        mEstateDao.updateEstate(estate);
    }

}
