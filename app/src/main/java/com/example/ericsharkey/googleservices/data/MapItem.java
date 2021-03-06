// Eric Sharkey
// MDF3 - 1812
// MapItem.java

package com.example.ericsharkey.googleservices.data;
import java.io.Serializable;

public class MapItem implements Serializable {

    // Properties
    private final String mTitle;
    private final String mDesc;
    private final double mLat;
    private final double mLon;
    private final String mImageURI;

    // Constructor
    public MapItem(String _title, String _desc, String _imageURI, double _lat, double _lon){
        mTitle = _title;
        mDesc = _desc;
        mLat = _lat;
        mLon = _lon;
        mImageURI = _imageURI;
    }

    // Getters
    public String getmTitle() {
        return mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public double getmLat() {
        return mLat;
    }

    public double getmLon() {
        return mLon;
    }

    public String getmImageURI() {
        return mImageURI;
    }

}
