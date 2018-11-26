// Eric Sharkey
// MDF3 - 1812
// FormFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ericsharkey.googleservices.R;
import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;
import com.example.ericsharkey.googleservices.utilities.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FormFragment extends Fragment {

    private double mLat;
    private double mLon;
    private Bitmap mImage;
    private ArrayList<MapItem> mList = new ArrayList<>();

    public static FormFragment newInstance(){
        return new FormFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if(bundle != null){
            mLat = bundle.getDouble(Const.EXTRA_LAT);
            mLon = bundle.getDouble(Const.EXTRA_LON);
            mList = (ArrayList<MapItem>)bundle.getSerializable(Const.EXTRA_LIST);
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

               //TODO: When save is selected get the returned image and uri and add to the list


               if(getView() != null){
                   EditText t = getView().findViewById(R.id.form_title);
                   EditText d = getView().findViewById(R.id.form_desc);
                   ImageView imageView = getView().findViewById(R.id.form_image);

                   if (!t.getText().toString().isEmpty() && !d.getText().toString().isEmpty()
                           && imageView.getDrawable() != null && getContext() != null) {


                       int readResult = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                       int writeResult = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                       if (readResult == PackageManager.PERMISSION_GRANTED && writeResult == PackageManager.PERMISSION_GRANTED){
                           Log.i("TAG", "saveFile: Permission granted");

                         saveFile(t.getText().toString(), d.getText().toString());

                       } else {
                           requestPermissions(new String[]
                                           {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                   Manifest.permission.READ_EXTERNAL_STORAGE},
                                   Const.REQUEST_EXTERNAL);
                       }
                   } else {
                       Toast.makeText(getContext(), R.string.empty_input, Toast.LENGTH_SHORT).show();
                   }
               }

               break;
           case R.id.camera_btn:
               // TODO: Open the device camera and get the returned image.

               // TODO: Get the proper URI from the provider.
               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//               intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
               intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
               startActivityForResult(intent, Const.REQUEST_TAKE_PICTURE);
               break;
       }
        return super.onOptionsItemSelected(item);
    }


    private Uri getUri(){

        File publicStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(publicStorage, Const.IMAGE_FILE+mList.size());
        Uri imageUri = null;

        if(getActivity() != null){
            try{
                if(imageFile.createNewFile()){
                    saveImage(imageFile);
                    imageUri = Uri.fromFile(imageFile);
//                    imageUri = FileProvider.getUriForFile(getActivity(), Const.AUTHORITY, imageFile);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return imageUri;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(grantResults.length > 1 && requestCode == Const.REQUEST_EXTERNAL) {

            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                if(getView() != null){
                    EditText t = getView().findViewById(R.id.form_title);
                    EditText d = getView().findViewById(R.id.form_desc);

                    saveFile(t.getText().toString(), d.getText().toString());
                }
            }
        }
    }

    private void saveFile(String t, String d){
        // ADD item to the list.
        // ADD item to the list.
        Uri imageUri = getUri();

        if(getContext() != null && imageUri != null){
            MapItem mapItem = new MapItem(t, d
                    , imageUri.toString(), mLat, mLon);
            mList.add(mapItem);

            Utils.write(mList, getContext());

            Toast.makeText(getContext(), R.string.save_successful, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage(File imageFile){
        if(getContext() != null){
            try{
                FileOutputStream fos = new FileOutputStream(imageFile);
                mImage.compress(Bitmap.CompressFormat.PNG, 85, fos);
                fos.flush();
                fos.close();
            } catch (IOException e ){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Const.REQUEST_TAKE_PICTURE && resultCode == Activity.RESULT_OK){

            if(data.getExtras() != null){

                mImage = (Bitmap)data.getExtras().get("data");

                if(getView() != null){
                    ImageView imageView = getView().findViewById(R.id.form_image);
                    imageView.setImageBitmap(mImage);
                }
            }
        }
    }
}
