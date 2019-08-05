package com.openclassrooms.realestatemanager.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RealEstateAgent {

    @PrimaryKey
    private long realEstateAgent_Id;
    private String username;
    private String urlPicture;

    public RealEstateAgent(long realEstateAgent_Id, String username, String urlPicture) {
        this.realEstateAgent_Id = realEstateAgent_Id;
        this.username = username;
        this.urlPicture = urlPicture;
    }

    // --- GETTER ---

    public long getRealEstateAgent_Id() { return realEstateAgent_Id; }
    public String getUsername() { return username; }
    public String getUrlPicture() { return urlPicture; }

    // --- SETTER ---

    public void setRealEstateAgent_Id(long realEstateAgent_Id) { this.realEstateAgent_Id = realEstateAgent_Id; }
    public void setUsername(String username) { this.username = username; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
}
