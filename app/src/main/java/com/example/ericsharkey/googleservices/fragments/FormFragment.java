// Eric Sharkey
// MDF3 - 1812
// FormFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

public class FormFragment extends Fragment {

    // Member Variables.
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

    // Getting the bundle arguments passed from the activity.
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

    // Inflating the menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_menu, menu);
    }

    // Saving the updated list and the image.
    // Opening the camera if camera button selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();

       switch (itemID){

           case R.id.save_btn:

               if(getView() != null){
                   EditText t = getView().findViewById(R.id.form_title);
                   EditText d = getView().findViewById(R.id.form_desc);
                   ImageView imageView = getView().findViewById(R.id.form_image);

                   if (!t.getText().toString().isEmpty() && !d.getText().toString().isEmpty()
                           && imageView.getDrawable() != null && getContext() != null) {

                       int readResult = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                       int writeResult = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                       if (readResult == PackageManager.PERMISSION_GRANTED && writeResult == PackageManager.PERMISSION_GRANTED){

                         saveFile(t.getText().toString(), d.getText().toString());

                         t.setText(null);
                         d.setText(null);

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

               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
               startActivityForResult(intent, Const.REQUEST_TAKE_PICTURE);
               break;
       }
        return super.onOptionsItemSelected(item);
    }


    // Getting the file Uri and passing to the saveImage Function.
    private Uri getUri(){

        File publicStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(publicStorage, Const.IMAGE_FILE+mList.size());
        Uri imageUri = null;

        if(getActivity() != null){
            try{
                if(imageFile.createNewFile()){
                    saveImage(imageFile);
                    imageUri = Uri.fromFile(imageFile);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return imageUri;
    }

    // Checking Permissions.
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

    // Saving list to the file system.
    private void saveFile(String t, String d){
        Uri imageUri = getUri();

        if(getContext() != null && imageUri != null){
            MapItem mapItem = new MapItem(t, d
                    , imageUri.toString(), mLat, mLon);
            mList.add(mapItem);

            Utils.write(mList, getContext());
            Toast.makeText(getContext(), R.string.save_successful, Toast.LENGTH_SHORT).show();
        }
    }

    // Saving hte image to hte public directory.
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

    // Getting teh image taken from the camera.
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
