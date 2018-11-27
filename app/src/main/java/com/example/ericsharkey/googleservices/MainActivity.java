// Eric Sharkey
// MDF3 - 1812
// MainActivity.java

package com.example.ericsharkey.googleservices;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;
import com.example.ericsharkey.googleservices.fragments.MapFragment;
import com.example.ericsharkey.googleservices.interfaces.MainInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding the Map Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.map_frame, MapFragment.newInstance())
                .commit();
    }


    // Function to display the form and passing the necessary information.
    @Override
    public void displayForm(double lat, double lon, ArrayList<MapItem> list) {

        Intent intent = new Intent(this, FormActivity.class);

            intent.putExtra(Const.EXTRA_LAT, lat);
            intent.putExtra(Const.EXTRA_LON, lon);
            intent.putExtra(Const.EXTRA_LIST, list);
            intent.setAction(Const.FORM_ACTION);

            startActivity(intent);
    }

    // Function to display the Details Fragment and passing the necessary information.
    @Override
    public void displayDetails(ArrayList<MapItem> list, int index) {

        Intent intent = new Intent(this, DetailsActivity.class);

        intent.putExtra(Const.EXTRA_LIST, list);
        intent.putExtra(Const.INDEX, index);
        intent.setAction(Const.DETAILS_ACTION);

        startActivity(intent);
    }
}
