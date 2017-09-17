package com.tejunareddy.hackmit2017.bucketlist.model;

import android.graphics.Bitmap;

/**
 * Created by tejun on 9/16/2017.
 */
public class PlaceOfInterest {
    private String city;
    private String description;
    private Bitmap image;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
