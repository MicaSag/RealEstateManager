package com.openclassrooms.realestatemanager.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

@Entity(foreignKeys = @ForeignKey(entity = RealEstateAgent.class,
        parentColumns = "realEstateAgent_Id",
        childColumns = "realEstateAgent_Id"))

public class Property {

    @PrimaryKey(autoGenerate = true)
    private long property_Id;
    private String type;
    private Integer price;
    private Integer area;
    private Integer numberOfParts;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private String  description;
    private ArrayList<String> photos;
    private String address;
    private ArrayList<String> pointOfInterest;
    private Boolean status;                     // True = sold, False = for sale
    private LocalDateTime dateEntryOfTheMarket;
    private LocalDateTime dateOfSale;
    private long realEstateAgent_Id;

    public Property(){}
    public Property(String type, Integer price, Integer area,
                    Integer numberOfParts, Integer numberOfBathrooms, Integer numberOfBedrooms,
                    String description, ArrayList<String> photos, String address,
                    ArrayList<String> pointOfInterest, LocalDateTime dateEntryOfTheMarket,
                    LocalDateTime dateOfSale, long realEstateAgent_Id) {
        this.type = type;
        this.price = price;
        this.area = area;
        this.numberOfParts = numberOfParts;
        this.numberOfBathrooms = numberOfBathrooms;
        this.numberOfBedrooms = numberOfBedrooms;
        this.description = description;
        this.photos = photos;
        this.address = address;
        this.pointOfInterest = pointOfInterest;
        this.status = false;
        this.dateEntryOfTheMarket = dateEntryOfTheMarket;
        this.dateOfSale = dateOfSale;
        this.realEstateAgent_Id = realEstateAgent_Id;
    }

    // --- GETTER ---

    public long getProperty_Id() { return property_Id; }
    public String getType() { return type; }
    public Integer getPrice() { return price; }
    public Integer getArea() { return area; }
    public Integer getNumberOfParts() { return numberOfParts; }
    public Integer getNumberOfBathrooms() { return numberOfBathrooms; }
    public Integer getNumberOfBedrooms() { return numberOfBedrooms; }
    public String getDescription() { return description; }
    public ArrayList<String> getPhotos() { return photos; }
    public String getAddress() { return address; }
    public ArrayList<String> getPointOfInterest() { return pointOfInterest; }
    public Boolean getStatus() { return status; }
    public LocalDateTime getDateEntryOfTheMarket() { return dateEntryOfTheMarket; }
    public LocalDateTime getDateOfSale() { return dateOfSale; }
    public long getRealEstateAgent_Id() { return realEstateAgent_Id; }

    // --- SETTER ---

    public void setProperty_Id(long propertyId) { this.property_Id = propertyId;  }
    public void setType(String type) {  this.type = type;  }
    public void setPrice(Integer price) {  this.price = price;  }
    public void setArea(Integer area) {  this.area = area;  }
    public void setNumberOfParts(Integer numberOfParts) {  this.numberOfParts = numberOfParts;  }
    public void setNumberOfBathrooms(Integer numberOfBathrooms) { this.numberOfBathrooms = numberOfBathrooms;  }
    public void setNumberOfBedrooms(Integer numberOfBedrooms) { this.numberOfBedrooms = numberOfBedrooms;  }
    public void setDescription(String description) {  this.description = description;  }
    public void setPhotos(ArrayList<String> photos) {  this.photos = photos;  }
    public void setAddress(String address) {  this.address = address;  }
    public void setPointOfInterest(ArrayList<String> pointOfInterest) {  this.pointOfInterest = pointOfInterest;  }
    public void setStatus(Boolean status) {  this.status = status;  }
    public void setDateEntryOfTheMarket(LocalDateTime dateEntryOfTheMarket) {  this.dateEntryOfTheMarket = dateEntryOfTheMarket;  }
    public void setDateOfSale(LocalDateTime dateOfSale) {  this.dateOfSale = dateOfSale;  }
    public void setRealEstateAgent_Id(long realEstateAgent) {  this.realEstateAgent_Id = realEstateAgent;  }
}

