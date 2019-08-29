package com.openclassrooms.realestatemanager.Repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.Dao.PropertyDao;
import com.openclassrooms.realestatemanager.Models.Property;

import java.util.List;

public class PropertyDataRepository {

    private final PropertyDao propertyDao;

    public PropertyDataRepository(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    // --- GET ---

    public LiveData<List<Property>> getPropertys(long realEstateAgent_Id){ return this.propertyDao.getPropertys(realEstateAgent_Id); }

    // --- CREATE ---

    public void createProperty(Property property){ propertyDao.insertProperty(property); }

    // --- DELETE/S ---
    public void deleteProperty(long property_Id){ propertyDao.deleteProperty(property_Id); }
    public void deletePropertys(long realEstateAgent_Id){ propertyDao.deletePropertys(realEstateAgent_Id); }

    // --- UPDATE ---
    public void updateProperty(Property property){ propertyDao.updateProperty(property); }

}
