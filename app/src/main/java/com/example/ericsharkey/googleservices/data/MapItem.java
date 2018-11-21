// Eric Sharkey
// MDF3 - 1812
// MapItem.java

package com.example.ericsharkey.googleservices.data;

public class MapItem {

    private String mTitle;
    private String mDesc;
    private String mImageURI;


    public MapItem(String _title, String _desc, String _imageURI){
        mTitle = _title;
        mDesc = _desc;
        mImageURI = _imageURI;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getmImageURI() {
        return mImageURI;
    }

}
