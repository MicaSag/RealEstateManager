package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.RealEstateAgent;

@Dao
public interface RealEstateAgentDao {

    // For ContentProvider
    @Query("SELECT * FROM RealEstateAgent WHERE realEstateAgent_Id = :realEstateAgent_Id")
    Cursor getRealEstateAgentWithCursor(long realEstateAgent_Id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createRealEstateAgent(RealEstateAgent realEstateAgent);

    @Query("SELECT * FROM RealEstateAgent WHERE realEstateAgent_Id = :realEstateAgent_Id")
    LiveData<RealEstateAgent> getRealEstateAgent(long realEstateAgent_Id);

    @Query("DELETE FROM RealEstateAgent WHERE realEstateAgent_Id = :realEstateAgent_Id")
    int deleteRealEstateAgent(long realEstateAgent_Id);
}
