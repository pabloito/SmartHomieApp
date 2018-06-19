package smartcompany.smarthomie;

import android.os.AsyncTask;
import android.util.Log;

public class Task extends AsyncTask{
    @Override
    protected Object doInBackground(Object[] objects) {
        Log.d("dev","sleepin");
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException e){

        }
        Log.d("dev","wakin");
        return objects[0];
    }

    @Override
    protected void onPostExecute(Object o) {
        ((HomeFragment)o).drawDevicesRemote();
        ((HomeFragment)o).drawRoutinesRemote();
    }
}
