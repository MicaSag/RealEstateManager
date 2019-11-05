package com.openclassrooms.realestatemanager.models;

import android.content.ContentValues;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RealEstateAgent {

    @PrimaryKey
    private long realEstateAgent_Id;
    private String userName;
    private String urlPicture;

    public RealEstateAgent() {
    }

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

    // --- UTILS ---

    public static RealEstateAgent fromContentValues(ContentValues values) {
        final RealEstateAgent realEstateAgent = new RealEstateAgent();
        if (values.containsKey("realEstateAgentId")) realEstateAgent.setRealEstateAgent_Id(values.getAsLong("realEstateAgentId"));
        if (values.containsKey("userName")) realEstateAgent.setUserName(values.getAsString("userName"));
        if (values.containsKey("urlPicture")) realEstateAgent.setUrlPicture(values.getAsString("urlPicture"));
        return realEstateAgent;
    }
}
