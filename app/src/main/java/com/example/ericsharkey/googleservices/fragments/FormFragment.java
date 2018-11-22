// Eric Sharkey
// MDF3 - 1812
// FormFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.ericsharkey.googleservices.R;
import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;

import java.io.File;
import java.util.ArrayList;

public class FormFragment extends Fragment {

    private double mLat;
    private double mLon;
    private ArrayList<MapItem> mList = new ArrayList<>();

    public static FormFragment newInstance(){
        return new FormFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null){
            mLat = bundle.getDouble(Const.EXTRA_LAT);
            mLon = bundle.getDouble(Const.EXTRA_LON);
        }

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
               intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
               intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
               startActivityForResult(intent, Const.REQUEST_TAKE_PICTURE);
               break;
       }
        return super.onOptionsItemSelected(item);
    }


    private Uri getUri(){

        File publicStorage = Environment.getExternalStoragePublicDirectory(Const.IMAGE_FOLDER);
        File imageFile = new File(publicStorage, Const.IMAGE_FILE+mList.size());
        Uri imageUri = null;

        if(getActivity() != null){
            try{
                if(imageFile.createNewFile()){
                    imageUri = FileProvider.getUriForFile(getActivity(), Const.AUTHORITY, imageFile);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return imageUri;
    }
}
