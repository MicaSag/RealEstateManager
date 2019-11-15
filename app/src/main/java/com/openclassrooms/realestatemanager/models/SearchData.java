package com.openclassrooms.realestatemanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchData implements Parcelable {

    private String queryString;
    private List<Object> args ;
    private String city;
    private int photoMin;
    private int photoMax;
    private List<Boolean> chips;

    public SearchData() {
    }

    protected SearchData(Parcel in) {
        queryString = in.readString();
        args = in.readArrayList(null);
        city = in.readString();
        photoMin = in.readInt();
        photoMax = in.readInt();
        chips = in.readArrayList(null);
    }

    public void init(long realEstateAgent_Id) {
        queryString = "SELECT * FROM Estate WHERE realEstateAgent_Id =?";
        args = new ArrayList<>();
        args.add(realEstateAgent_Id);
        city = "";
        photoMin = 0;
        photoMax = 0;
        chips = new ArrayList<>();
        chips = Arrays.asList(false,false,false,false,false,false);
    }

    public static final Creator<SearchData> CREATOR = new Creator<SearchData>() {
        @Override
        public SearchData createFromParcel(Parcel in) {
            return new SearchData(in);
        }

        @Override
        public SearchData[] newArray(int size) {
            return new SearchData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(queryString);
        dest.writeList(args);
        dest.writeString(city);
        dest.writeInt(photoMin);
        dest.writeInt(photoMax);
        dest.writeList(chips);
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPhotoMin() {
        return photoMin;
    }

    public void setPhotoMin(int photoMin) {
        this.photoMin = photoMin;
    }

    public int getPhotoMax() {
        return photoMax;
    }

    public void setPhotoMax(int photoMax) {
        this.photoMax = photoMax;
    }

    public List<Boolean> getChips() {
        return chips;
    }

    public void setChips(ArrayList<Boolean> chips) {
        this.chips = chips;
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "queryString='" + queryString + '\'' +
                ", args=" + args +
                ", city='" + city + '\'' +
                ", photoMin=" + photoMin +
                ", photoMax=" + photoMax +
                ", chips=" + chips +
                '}';
    }
}
