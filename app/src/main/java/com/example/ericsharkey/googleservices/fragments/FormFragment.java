// Eric Sharkey
// MDF3 - 1812
// FormFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.ericsharkey.googleservices.R;
import com.example.ericsharkey.googleservices.constants.Const;

public class FormFragment extends Fragment {



    public static FormFragment newInstance(){
        return new FormFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();

       switch (itemID){

           case R.id.save_btn:
               // TODO: Validate input
               // Save details and imageURI to File System
               // Save Image to Public Storage.
               break;
           case R.id.camera_btn:
               // TODO: Open the device camera and get the returned image.

               // TODO: Get the proper URI from the provider.
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//               intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputUri());
               intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
               startActivityForResult(intent, Const.REQUEST_TAKE_PICTURE);

               break;
       }


        return super.onOptionsItemSelected(item);
    }
}
