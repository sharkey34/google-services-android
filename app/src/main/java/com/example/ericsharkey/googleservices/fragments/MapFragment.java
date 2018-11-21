// Eric Sharkey
// MDF3 - 1812
// MapFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.ericsharkey.googleservices.R;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends SupportMapFragment {

    private Boolean mRequestingLocation;

    public static MapFragment newInstance(){

        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    // Inflating the menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);
    }

    // Displaying the Form screen when the add button is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO: Open the form screen.


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {




        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
