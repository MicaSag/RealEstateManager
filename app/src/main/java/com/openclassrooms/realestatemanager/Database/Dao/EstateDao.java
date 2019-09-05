package com.openclassrooms.realestatemanager.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.Models.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    @Query("SELECT * FROM Estate WHERE realEstateAgent_Id = :realEstateAgent_Id")
    LiveData<List<Estate>> getEstates(long realEstateAgent_Id);

    @Query("SELECT * FROM Estate WHERE estate_Id = :estate_Id")
    LiveData<Estate> getEstate(long estate_Id);

    @Query("SELECT * FROM Estate")
    LiveData<List<Estate>> getEstates();

    @Insert
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

    @Query("DELETE FROM Estate WHERE estate_Id = :estate_Id")
    int deleteEstate(long estate_Id);

    @Query("DELETE FROM Estate WHERE realEstateAgent_Id = :realEstateAgent_Id")
    int deleteEstates(long realEstateAgent_Id);
}
