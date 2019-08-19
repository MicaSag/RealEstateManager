package com.openclassrooms.realestatemanager.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RealEstateAgent {

    @PrimaryKey
    private long realEstateAgent_Id;
    private String userName;
    private String urlPicture;

    public RealEstateAgent(long realEstateAgent_Id, String userName, String urlPicture) {
        this.realEstateAgent_Id = realEstateAgent_Id;
        this.userName = userName;
        this.urlPicture = urlPicture;
    }

    // --- GETTER ---

    public long getRealEstateAgent_Id() { return realEstateAgent_Id; }
    public String getUserName() { return userName; }
    public String getUrlPicture() { return urlPicture; }

    // --- SETTER ---

    public void setRealEstateAgent_Id(long realEstateAgent_Id) { this.realEstateAgent_Id = realEstateAgent_Id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
}
