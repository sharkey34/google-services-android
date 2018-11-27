// Eric Sharkey
// MDF3 -1812
// DetailsActivity.java

package com.example.ericsharkey.googleservices;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;
import com.example.ericsharkey.googleservices.fragments.DetailsFragment;
import com.example.ericsharkey.googleservices.fragments.FormFragment;
import com.example.ericsharkey.googleservices.interfaces.DetailsInterface;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements DetailsInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        if(intent != null ) {

            String action = intent.getAction();

            if(action != null && action.equals(Const.DETAILS_ACTION)){

                int index = intent.getIntExtra(Const.INDEX, 0);
                ArrayList<MapItem> list = (ArrayList<MapItem>) intent.getSerializableExtra(Const.EXTRA_LIST);

                Bundle bundle = new Bundle();
                bundle.putInt(Const.INDEX, index);
                bundle.putSerializable(Const.EXTRA_LIST, list);

                Fragment fragment = DetailsFragment.newInstance();
                fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details_frame, fragment)
                        .commit();
            }
        }
    }

    @Override
    public void closeDetails() {
        finish();
    }
}
