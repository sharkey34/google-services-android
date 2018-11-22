// Eric Sharkey
// MDF3 -1812
// DetailsActivity.java

package com.example.ericsharkey.googleservices;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ericsharkey.googleservices.fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_frame, DetailsFragment.newInstance())
                .commit();
    }
}
