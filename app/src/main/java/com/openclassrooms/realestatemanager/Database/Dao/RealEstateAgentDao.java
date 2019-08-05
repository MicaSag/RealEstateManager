package com.openclassrooms.realestatemanager.Database.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.openclassrooms.realestatemanager.Models.RealEstateAgent;

@Dao
public interface RealEstateAgentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createRealEstateAgent(RealEstateAgent realEstateAgent);

    @Query("SELECT * FROM RealEstateAgent WHERE realEstateAgent_Id = :realEstateAgent_Id")
    LiveData<RealEstateAgent> getRealEstateAgent_Id(long realEstateAgent_Id);
}
