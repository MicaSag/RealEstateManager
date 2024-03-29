package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    // For ContentProvider
    @Query("SELECT * FROM Estate WHERE realEstateAgent_Id = :realEstateAgent_Id")
    Cursor getEstatesWithCursor(long realEstateAgent_Id);

    @Query("SELECT * FROM Estate WHERE realEstateAgent_Id = :realEstateAgent_Id")
    LiveData<List<Estate>> getEstates(long realEstateAgent_Id);

    @RawQuery(observedEntities = Estate.class)
    LiveData<List<Estate>> getEstates(SupportSQLiteQuery query);

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
