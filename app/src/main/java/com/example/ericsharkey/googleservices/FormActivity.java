// Eric Sharkey
// MDF3 - 1812
// FormActivity.java

package com.example.ericsharkey.googleservices;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.fragments.FormFragment;
public class FormActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent intent = getIntent();

        if(intent != null ) {

            String action = intent.getAction();

                double lat = intent.getDoubleExtra(Const.EXTRA_LAT, 0);
                double lon = intent.getDoubleExtra(Const.EXTRA_LON, 0);

                // TODO: Pass bundle with lat long to fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.form_frame, FormFragment.newInstance())
                    .commit();
        }
    }
}
