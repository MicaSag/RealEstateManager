package com.openclassrooms.realestatemanager.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.Models.Property;

import java.util.List;

@Dao
public interface PropertyDao {

    @Query("SELECT * FROM Property WHERE realEstateAgent_Id = :realEstateAgent_Id")
    LiveData<List<Property>> getPropertys(long realEstateAgent_Id);

    @Insert
    long insertProperty(Property property);

    @Update
    int updateProperty(Property property);

    @Query("DELETE FROM Property WHERE property_Id = :property_Id")
    int deleteProperty(long property_Id);

    @Query("DELETE FROM Property WHERE realEstateAgent_Id = :realEstateAgent_Id")
    int deletePropertys(long realEstateAgent_Id);
}
