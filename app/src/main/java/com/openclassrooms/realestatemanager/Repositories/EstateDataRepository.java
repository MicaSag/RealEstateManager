package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.EstateDao;
import com.openclassrooms.realestatemanager.Models.Estate;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao estateDao;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }

    // --- GET ---

    public LiveData<List<Estate>> getEstates(long realEstateAgent_Id){ return this.estateDao.getEstates(realEstateAgent_Id); }
    public LiveData<List<Estate>> getEstates(){ return this.estateDao.getEstates(); }

    public LiveData<Estate> getEstate(long estate_Id){ return this.estateDao.getEstate(estate_Id); }

    // --- CREATE ---

    public void createEstate(Estate estate){ estateDao.insertEstate(estate); }

    // --- DELETE/S ---
    public void deleteEstate(long estate_Id){ estateDao.deleteEstate(estate_Id); }
    public void deleteEstates(long realEstateAgent_Id){ estateDao.deleteEstates(realEstateAgent_Id); }

    // --- UPDATE ---
    public void updateEstate(Estate estate){ estateDao.updateEstate(estate); }

}
