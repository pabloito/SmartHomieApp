package smartcompany.smarthomie;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Device {
    String name;
    String type;

    public Device(String name){
        this.name = name;
        this.type = "blank";
    }

    public Device(String name, String type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

}
