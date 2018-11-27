// Eric Sharkey
// MDF3 - 1812
// DetailsFragment.java

package com.example.ericsharkey.googleservices.fragments;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ericsharkey.googleservices.R;
import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;
import com.example.ericsharkey.googleservices.interfaces.DetailsInterface;
import com.example.ericsharkey.googleservices.utilities.Utils;
import java.io.File;
import java.util.ArrayList;

public class DetailsFragment extends Fragment {

    // Member Variables
    private ArrayList<MapItem> mList = new ArrayList<>();
    private int mIndex;
    private DetailsInterface mListener;

    // Returning a new Instance of the DetailsFragment.
    public static DetailsFragment newInstance(){
        return new DetailsFragment();
    }


    // Setting up the listener
    @Override
    public void onAttach(Context context) {
        if(context instanceof DetailsInterface) {
            mListener = (DetailsInterface)context;
        }

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting the index and list from the Bundle.
        Bundle bundle = getArguments();

        if(bundle != null){

            mIndex = bundle.getInt(Const.INDEX);
            mList = (ArrayList<MapItem>) bundle.getSerializable(Const.EXTRA_LIST);
        }
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    // Setting up the UI Elements.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getView() != null){
            TextView titleTV = getView().findViewById(R.id.details_title);
            TextView descTV = getView().findViewById(R.id.details_desc);
            ImageView imageView = getView().findViewById(R.id.details_image);

            Uri uri = Uri.parse(mList.get(mIndex).getmImageURI());

            imageView.setImageURI(uri);
            titleTV.setText(mList.get(mIndex).getmTitle());
            descTV.setText(mList.get(mIndex).getmDesc());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }


    // Checking that the delete btn was selected and displaying the dialog.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemID = item.getItemId();

        if(itemID == R.id.delete_btn){
            displayDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    // Setting up the dialog.
    private void displayDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.delete, deleteClicked)
                .setNegativeButton(R.string.cancel_btn, null);

        builder.show();
    }

    // Fucntion to delete when the delete button is selected.
    private final AlertDialog.OnClickListener deleteClicked = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(mListener != null && getContext() != null){

                Uri uri = Uri.parse(mList.get(mIndex).getmImageURI());

                if(uri != null && uri.getPath() != null) {
                    File file = new File(uri.getPath());

                    if(file.exists()){
                        if(file.delete()){
                            mList.remove(mIndex);
                            Utils.write(mList, getContext());
                            mListener.closeDetails();
                        }
                    }
                }
            }
        }
    };
}
