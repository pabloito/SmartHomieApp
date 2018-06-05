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
        f.put("heladera",new Fridge("heladera","Heladera"));
        f.put("helad2era",new Fridge("heladera","Heladera"));
        f.put("hela3dera",new Fridge("helasadera","Heladera"));
        f.put("hela4dera",new Fridge("helaasddera","Heladera"));
        f.put("hela5dera",new Fridge("heladadera","Heladera"));
        f.put("hela6dera",new Fridge("heladdsera","Heladera"));
        return f;
    }

    public void execute(){

    }

    public void delete(){

    }
}
