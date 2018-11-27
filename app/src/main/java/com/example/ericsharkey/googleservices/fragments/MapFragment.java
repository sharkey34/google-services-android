// Eric Sharkey
// MDF3 - 1812
// MapFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericsharkey.googleservices.R;
import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;
import com.example.ericsharkey.googleservices.interfaces.MainInterface;
import com.example.ericsharkey.googleservices.utilities.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.InfoWindowAdapter,
        GoogleMap.OnInfoWindowClickListener, LocationListener {

    private boolean mRequestingLocation;
    private boolean mEnabled = false;
    LocationManager mManager;
    private GoogleMap mMap;
    private double mLatitude;
    private double mLongitude;
    private MainInterface mListener;
    private ArrayList<MapItem> mList = new ArrayList<>();
    private HashMap<String,Integer> mHashMap = new HashMap<>();


    public static MapFragment newInstance(){
        return new MapFragment();
    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof MainInterface) {
            mListener = (MainInterface)context;
        }
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);

        getMapAsync(this);
        mList = Utils.read(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        // Reloading the map.
        if(mMap != null) {
            mList = Utils.read(getContext());
            mMap.clear();
            addMarkers();
            zoomToUserLocation();
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == Const.REQUEST_LOCATION){
            if(grantResults.length > 0){

                    // Permission is granted.
                if(getActivity() != null){
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                        mManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                        if (mManager != null){
                            addMarkers();

                            Location lastKnown = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(lastKnown != null) {
                                mLatitude = lastKnown.getLatitude();
                                mLongitude = lastKnown.getLongitude();

                                mEnabled = true;
                                getActivity().invalidateOptionsMenu();

                                zoomToUserLocation();
                            } else {
                                mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10.0f, this);
                                mRequestingLocation = true;

                                Toast.makeText(getActivity(), R.string.no_location, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.add_btn);
        item.setEnabled(mEnabled);
    }

    // Inflating the menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);
    }


    // Displaying the Form screen when the add button is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();

        if(itemID == R.id.add_btn){
            if(mListener != null) {
                mListener.displayForm(mLatitude, mLongitude, mList);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        if(getActivity() == null){
            return;
        }
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            mManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (mManager != null){
                Location lastKnown = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                addMarkers();

                if(lastKnown != null) {
                    mLatitude = lastKnown.getLatitude();
                    mLongitude = lastKnown.getLongitude();

                    mEnabled = true;
                    getActivity().invalidateOptionsMenu();

                    zoomToUserLocation();
                } else {
                    mManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10.0f, this);
                    mRequestingLocation = true;

                    Toast.makeText(getActivity(), R.string.no_location, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            requestPermissions(new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, Const.REQUEST_LOCATION);
        }
    }

    private void zoomToUserLocation(){
        if(mMap != null) {
            LatLng userLocation = new LatLng(mLatitude, mLongitude);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, 16);
            mMap.animateCamera(update);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        if (mListener != null){
            mListener.displayForm(latLng.latitude, latLng.longitude, mList);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View contents = LayoutInflater.from(getActivity()).inflate(R.layout.info_window, null);

        ((TextView)contents.findViewById(R.id.info_title)).setText(marker.getTitle());
        ((TextView)contents.findViewById(R.id.info_desc)).setText(marker.getSnippet());

        return contents;
    }

    // Getting index and opening Details
    @Override
    public void onInfoWindowClick(Marker marker) {

        String key = marker.getTitle() + marker.getSnippet();

        Integer i = mHashMap.get(key);
        int index = i;

        if(mListener != null){
            mListener.displayDetails(mList,index);
        }
    }

    private void addMarkers(){
        if(mMap != null){

            for (int i = 0; i < mList.size(); i++) {

                MarkerOptions options = new MarkerOptions();
                options.title(mList.get(i).getmTitle());
                options.snippet(mList.get(i).getmDesc());
                LatLng officeLocation = new LatLng(mList.get(i).getmLat(), mList.get(i).getmLon());
                options.position(officeLocation);

                // Setting the values for the hashMap to get the index later.
                String key = options.getTitle() + options.getSnippet();
                mHashMap.put(key, i);

                mMap.addMarker(options);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();

        zoomToUserLocation();

        if(mRequestingLocation){
            mRequestingLocation = false;
            mManager.removeUpdates(this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
