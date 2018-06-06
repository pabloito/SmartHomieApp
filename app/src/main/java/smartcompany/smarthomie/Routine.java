package smartcompany.smarthomie;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Routine {
    String name;

    public Routine(String name){
        this.name = name;
    }

    public HashMap<String, Device> getRoutineDevices(){
        HashMap<String, Device> f = new HashMap<>();
        f.put("Cortina del quincho",new Curtain("Cortina del quincho","Curtain"));
        f.put("freezer",new Fridge("freezer","Fridge"));
        f.put("hornito ",new Oven("hornito ","Oven"));
        f.put("luz del pasillo",new Light("luz del pasillo","Light"));
        f.put("puertita",new Door("puertita","Door"));
        return f;
    }

    public void execute(){

    }

    public void delete(){

    }
}
