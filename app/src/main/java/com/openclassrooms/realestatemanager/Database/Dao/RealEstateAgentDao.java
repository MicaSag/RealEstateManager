package com.openclassrooms.realestatemanager.Database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.Models.RealEstateAgent;

@Dao
public interface RealEstateAgentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createRealEstateAgent(RealEstateAgent realEstateAgent);

    @Query("SELECT * FROM RealEstateAgent WHERE realEstateAgent_Id = :realEstateAgent_Id")
    LiveData<RealEstateAgent> getRealEstateAgent(long realEstateAgent_Id);

    @Query("DELETE FROM RealEstateAgent WHERE realEstateAgent_Id = :realEstateAgent_Id")
    int deleteRealEstateAgent(long realEstateAgent_Id);
}
