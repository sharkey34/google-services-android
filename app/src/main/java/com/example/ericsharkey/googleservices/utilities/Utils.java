// Eric Sharkey
// MDF3 - 1812
// Utils.java

package com.example.ericsharkey.googleservices.utilities;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.example.ericsharkey.googleservices.R;

public class Utils {


    public static boolean connected(Context context){
        boolean status = false;

        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager != null){
            NetworkInfo info = manager.getActiveNetworkInfo();

            if(info != null) {
                status = true;
            }
        }  else {
            Toast toast = Toast.makeText(context, R.string.network_unavailable, Toast.LENGTH_LONG);
            toast.show();
        }
        return status;
    }


//    // Function to write the ArrayList to the file.
//    public static void write(ArrayList<Person> list, Context context){
//
//        try {
//            FileOutputStream fos = context.openFileOutput(Const.FILE_NAME, Context.MODE_PRIVATE);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(list);
//            fos.close();
//            oos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // Function to read the object from the file.
//    public static ArrayList<Person> read(Context context){
//
//        ArrayList<Person> list = new ArrayList<>();
//
//        try {
//            FileInputStream fis = context.openFileInput(Const.FILE_NAME);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            list = (ArrayList<Person>) ois.readObject();
//            fis.close();
//            ois.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
