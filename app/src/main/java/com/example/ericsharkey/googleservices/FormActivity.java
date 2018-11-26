// Eric Sharkey
// MDF3 - 1812
// FormActivity.java

package com.example.ericsharkey.googleservices;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;
import com.example.ericsharkey.googleservices.fragments.FormFragment;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent intent = getIntent();

        if(intent != null ) {

            String action = intent.getAction();

            if(action != null && action.equals(Const.FORM_ACTION)){

                double lat = intent.getDoubleExtra(Const.EXTRA_LAT, 0);
                double lon = intent.getDoubleExtra(Const.EXTRA_LON, 0);
                ArrayList<MapItem> list = (ArrayList<MapItem>) intent.getSerializableExtra(Const.EXTRA_LIST);

                Bundle bundle = new Bundle();
                bundle.putDouble(Const.EXTRA_LAT, lat);
                bundle.putDouble(Const.EXTRA_LON, lon);
                bundle.putSerializable(Const.EXTRA_LIST, list);

                Fragment fragment = FormFragment.newInstance();
                fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.form_frame, fragment)
                        .commit();
            }
        }
    }
}
