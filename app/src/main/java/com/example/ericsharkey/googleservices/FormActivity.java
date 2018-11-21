// Eric Sharkey
// MDF3 - 1812
// FormActivity.java

package com.example.ericsharkey.googleservices;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.ericsharkey.googleservices.fragments.FormFragment;

public class FormActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.form_frame, FormFragment.newInstance())
                .commit();
    }
}
