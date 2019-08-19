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

    public LiveData<List<Property>> getItems(long userId){ return this.propertyDao.getPropertys(userId); }

    // --- CREATE ---

    public void createProperty(Property property){ propertyDao.insertProperty(property); }

    // --- DELETE ---
    public void deleteProperty(long property_Id){ propertyDao.deleteProperty(property_Id); }

    // --- UPDATE ---
    public void updateProperty(Property property){ propertyDao.updateProperty(property); }

}
