// Eric Sharkey
// MDF3 - 1812
// MainInterface.java

package com.example.ericsharkey.googleservices.interfaces;

import com.example.ericsharkey.googleservices.data.MapItem;

import java.util.ArrayList;

public interface MainInterface {

    void displayForm(double lat, double lon, ArrayList<MapItem> list);
    void displayDetails(ArrayList<MapItem> list, int index);

}
