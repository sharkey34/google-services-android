// Eric Sharkey
// MDF3 - 1812
// Utils.java

package com.example.ericsharkey.googleservices.utilities;
import android.content.Context;

import com.example.ericsharkey.googleservices.constants.Const;
import com.example.ericsharkey.googleservices.data.MapItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Utils {


 // Function to write the ArrayList to the file.
    public static void write(ArrayList<MapItem> list, Context context){
        try {
            FileOutputStream fos = context.openFileOutput(Const.FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            fos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


// Function to read the object from the file.
    public static ArrayList<MapItem> read(Context context){
        ArrayList<MapItem> list = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(Const.FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<MapItem>) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
