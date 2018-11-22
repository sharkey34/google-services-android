// Eric Sharkey
// MDF3 - 1812
// DetailsFragment.java

package com.example.ericsharkey.googleservices.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.ericsharkey.googleservices.R;

public class DetailsFragment extends Fragment {

    public static DetailsFragment newInstance(){

        return new DetailsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO: Delete the selected item.
        return super.onOptionsItemSelected(item);
    }
}
