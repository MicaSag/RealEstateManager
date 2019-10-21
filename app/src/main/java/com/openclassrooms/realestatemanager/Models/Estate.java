package com.openclassrooms.realestatemanager.Models;

import android.content.ContentValues;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


import com.openclassrooms.realestatemanager.Utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity(foreignKeys = @ForeignKey(entity = RealEstateAgent.class,
        parentColumns = "realEstateAgent_Id",
        childColumns = "realEstateAgent_Id"))

public class Estate {

    // For Debug
    private static final String TAG = Estate.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    private long estate_Id;
    private String type;
    private Integer price;
    private Integer area;
    private Integer numberOfParts;
    private Integer numberOfBathrooms;
    private Integer numberOfBedrooms;
    private String  description;
    private ArrayList<String> photos;
    private ArrayList<String> photosDescription;
    private ArrayList<String> address;
    private Map<String,String> pointOfInterest;
    private Boolean status;                     // True = sold, False = for sale
    private LocalDateTime dateEntryOfTheMarket;
    private LocalDateTime dateOfSale;
    private long realEstateAgent_Id;

    // --- CONSTRUCTOR ---

    public Estate(){}

    public Estate(long estate_Id){
        this.estate_Id = estate_Id;
    }

    public Estate(String type, Integer price, Integer area,
                  Integer numberOfParts, Integer numberOfBathrooms, Integer numberOfBedrooms,
                  String description, ArrayList<String> photos, ArrayList<String> photosDescription,  ArrayList<String> address,
                  Map<String,String> pointOfInterest, LocalDateTime dateEntryOfTheMarket,
                  LocalDateTime dateOfSale, long realEstateAgent_Id) {
        this.type = type;
        this.price = price;
        this.area = area;
        this.numberOfParts = numberOfParts;
        this.numberOfBathrooms = numberOfBathrooms;
        this.numberOfBedrooms = numberOfBedrooms;
        this.description = description;
        this.photos = photos;
        this.photosDescription = photosDescription;
        this.address = address;
        this.pointOfInterest = pointOfInterest;
        this.status = false;
        this.dateEntryOfTheMarket = dateEntryOfTheMarket;
        this.dateOfSale = dateOfSale;
        this.realEstateAgent_Id = realEstateAgent_Id;
    }

    // --- GETTER ---

    public long getEstate_Id() { return estate_Id; }
    public String getType() { return type; }
    public Integer getPrice() { return price; }
    public Integer getArea() { return area; }
    public Integer getNumberOfParts() { return numberOfParts; }
    public Integer getNumberOfBathrooms() { return numberOfBathrooms; }
    public Integer getNumberOfBedrooms() { return numberOfBedrooms; }
    public String getDescription() { return description; }
    public ArrayList<String> getPhotos() { return photos; }
    public ArrayList<String> getPhotosDescription() { return photosDescription; }
    public ArrayList<String> getAddress() { return address; }
    public Map<String,String> getPointOfInterest() { return pointOfInterest; }
    public Boolean getStatus() { return status; }
    public LocalDateTime getDateEntryOfTheMarket() { return dateEntryOfTheMarket; }
    public LocalDateTime getDateOfSale() { return dateOfSale; }
    public long getRealEstateAgent_Id() { return realEstateAgent_Id; }

    // --- SETTER ---

    public void setEstate_Id(long estate_Id) { this.estate_Id = estate_Id;  }
    public void setType(String type) {  this.type = type;  }
    public void setPrice(Integer price) {  this.price = price;  }
    public void setArea(Integer area) {  this.area = area;  }
    public void setNumberOfParts(Integer numberOfParts) {  this.numberOfParts = numberOfParts;  }
    public void setNumberOfBathrooms(Integer numberOfBathrooms) { this.numberOfBathrooms = numberOfBathrooms;  }
    public void setNumberOfBedrooms(Integer numberOfBedrooms) { this.numberOfBedrooms = numberOfBedrooms;  }
    public void setDescription(String description) {  this.description = description;  }
    public void setPhotos(ArrayList<String> photos) {  this.photos = photos;  }
    public void setPhotosDescription(ArrayList<String> photosDescription) {  this.photosDescription = photosDescription;  }
    public void setAddress(ArrayList<String> address) {  this.address = address;  }
    public void setPointOfInterest(Map<String,String> pointOfInterest) {  this.pointOfInterest = pointOfInterest;  }
    public void setStatus(Boolean status) {  this.status = status;  }
    public void setDateEntryOfTheMarket(LocalDateTime dateEntryOfTheMarket) {  this.dateEntryOfTheMarket = dateEntryOfTheMarket;  }
    public void setDateOfSale(LocalDateTime dateOfSale) {  this.dateOfSale = dateOfSale;  }
    public void setRealEstateAgent_Id(long realEstateAgent) {  this.realEstateAgent_Id = realEstateAgent;  }

    // --- ToSTRING ---

    @Override
    public String toString() {
        return "Estate{" +
                "estate_Id=" + estate_Id +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", numberOfParts=" + numberOfParts +
                ", numberOfBathrooms=" + numberOfBathrooms +
                ", numberOfBedrooms=" + numberOfBedrooms +
                ", description='" + description + '\'' +
                ", photos=" + photos +
                ", photosDescription='" + photosDescription + '\'' +
                ", address=" + address +
                ", pointOfInterest=" + pointOfInterest +
                ", status=" + status +
                ", dateEntryOfTheMarket=" + dateEntryOfTheMarket +
                ", dateOfSale=" + dateOfSale +
                ", realEstateAgent_Id=" + realEstateAgent_Id +
                '}';
    }

    // --- UTILS ---

    public static Estate fromContentValues(ContentValues values) {
        final Estate estate = new Estate();
        if (values.containsKey("estate_id")) estate.setEstate_Id(values.getAsLong("estate_id"));
        if (values.containsKey("type")) estate.setType(values.getAsString("type"));
        if (values.containsKey("price")) estate.setPrice(values.getAsInteger("price"));
        if (values.containsKey("area")) estate.setArea(values.getAsInteger("area"));
        if (values.containsKey("parts")) estate.setNumberOfParts(values.getAsInteger("parts"));
        if (values.containsKey("bathrooms")) estate.setNumberOfBathrooms(values.getAsInteger("bathrooms"));
        if (values.containsKey("bedrooms")) estate.setNumberOfBedrooms(values.getAsInteger("bedrooms"));
        if (values.containsKey("description")) estate.setDescription(values.getAsString("description"));
        if (values.containsKey("photos")) estate.setPhotos(Converters.fromString(values.getAsString("photos")));
        if (values.containsKey("photosDescription")) estate.setPhotosDescription(Converters.fromString(values.getAsString("photosDescription")));
        if (values.containsKey("address")) estate.setAddress(Converters.fromString(values.getAsString("address")));
        if (values.containsKey("pointOfInterest")) estate.setPointOfInterest(Converters.fromStringToMapStringString(values.getAsString("pointOfInterest")));
        if (values.containsKey("status")) estate.setStatus(values.getAsBoolean("status"));
        if (values.containsKey("dateEntryOfTheMarket")) estate.setDateEntryOfTheMarket(Converters.fromTimestamp(values.getAsLong("dateEntryOfTheMarket")));
        if (values.containsKey("dateOfSale")) estate.setDateOfSale(Converters.fromTimestamp(values.getAsLong("dateOfSale")));
        if (values.containsKey("realEstateAgentId")) estate.setRealEstateAgent_Id(values.getAsLong("realEstateAgentId"));
        return estate;
    }
}

