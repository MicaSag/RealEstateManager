package com.openclassrooms.realestatemanager.Repositories;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.Database.Dao.EstateDao;
import com.openclassrooms.realestatemanager.Models.Estate;

import java.util.List;

public class EstateDataRepository {
    private static final String TAG = EstateDataRepository.class.getSimpleName();

    private final EstateDao mEstateDao;

    public EstateDataRepository(EstateDao estateDao) {
        mEstateDao = estateDao;
    }

    // --- GET ---
    // ===========

    // Returns a specific Estate
    public LiveData<Estate> getEstate(long estate_Id){
        return mEstateDao.getEstate(estate_Id);
    }

    // Return Estates managed by an Agent
    public LiveData<List<Estate>> getEstates(long realEstateAgent_Id){
        return mEstateDao.getEstates(realEstateAgent_Id);
    }

    // Return all Estates from database
    public LiveData<List<Estate>> getEstates(){
        return mEstateDao.getEstates();
    }

    // Returns all Estates from database corresponding at the query
    public LiveData<List<Estate>> getEstates(SupportSQLiteQuery query){
        return mEstateDao.getEstates(query);
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
