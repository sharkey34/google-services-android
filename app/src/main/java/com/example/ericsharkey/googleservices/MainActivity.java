// Eric Sharkey
// MDF3 - 1812
// MainActivity.java

package com.example.ericsharkey.googleservices;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ericsharkey.googleservices.fragments.MapFragment;
import com.example.ericsharkey.googleservices.interfaces.MainInterface;
public class MainActivity extends AppCompatActivity implements MainInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, MapFragment.newInstance())
                .commit();
    }


    @Override
    public void addClicked() {

    }
}
