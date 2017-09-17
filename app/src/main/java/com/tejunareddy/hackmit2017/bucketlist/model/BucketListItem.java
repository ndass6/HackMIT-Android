package com.tejunareddy.hackmit2017.bucketlist.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

/**
 * Created by tejun on 9/16/2017.
 */
public class BucketListItem {
    private String endCity;
    private Date startDate;
    private Date endDate;
    private Date userSetStartDate;
    private Date userSetEndDate;
    private int duration = 1;
    private Bitmap cityPicture;
    private boolean alert = false;
    private int price;
    private boolean starred = false;
    private List<PlaceOfInterest> placesOfInterest;
    private String placeId = null;
    private List<Flight> flightInfo;
    public BucketListItem() {

        // Don't delete
    }

    public Date getUserSetStartDate() {
        return userSetStartDate;
    }

    public void setUserSetStartDate(Date userSetStartDate) {
        this.userSetStartDate = userSetStartDate;
    }

    public Date getUserSetEndDate() {
        return userSetEndDate;
    }

    public void setUserSetEndDate(Date userSetEndDate) {
        this.userSetEndDate = userSetEndDate;
    }

    public boolean getStarred() {
        return starred;
    }

    public List<PlaceOfInterest> getPlacesOfInterest() {
        return placesOfInterest;
    }

    public void setPlacesOfInterest(List<PlaceOfInterest> placesOfInterest) {
        this.placesOfInterest = placesOfInterest;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Bitmap getCityPicture() {
        return cityPicture;
    }

    public void setCityPicture(Bitmap cityPicture) {
        this.cityPicture = cityPicture;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<Flight> getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(List<Flight> flightInfo) {
        this.flightInfo = flightInfo;
    }
}