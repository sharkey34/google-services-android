// Eric Sharkey
// MDF3 - 1812
// MapFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.ericsharkey.googleservices.R;
import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.interfaces.MainInterface;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private Boolean mRequestingLocation;
    LocationManager mManager;
    private GoogleMap mMap;
    private double mLatitude;
    private double mLongitude;
    private MainInterface mListener;

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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == Const.REQUEST_LOCATION){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Permission is granted.
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        int itemID = item.getItemId();

        if(itemID == R.id.add_btn){
            if(mListener != null) {
                mListener.displayForm(mLatitude, mLongitude, false);
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
        mManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            Location lastKnown = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // TODO: Zoom in the screen and drop a pin at the location.
            if(lastKnown != null) {
                mLatitude = lastKnown.getLatitude();
                mLongitude = lastKnown.getLongitude();

                zoomToUserLocation();
//                addMarkers();
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, Const.REQUEST_LOCATION);
        }
    }

    private void zoomToUserLocation(){
        if(mMap != null) {

            LatLng userLocation = new LatLng(mLatitude, mLongitude);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, 100);
            mMap.animateCamera(update);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        //TODO: Open the form with the lat and long selected.

        if (mListener != null){
            mListener.displayForm(latLng.latitude, latLng.longitude, true);
        }
    }


//    private void addMarkers(){
//        if(mMap != null){
//
//            MarkerOptions options = new MarkerOptions();
//            options.title("Full Sail University");
//            options.snippet("Mobile Development Offices");
//
//            LatLng officeLocation = new LatLng(mOfficesLatitude, mOfficesLongitude);
//            options.position(officeLocation);
//
//            map.addMarker(options);
//        }
//    }

}
