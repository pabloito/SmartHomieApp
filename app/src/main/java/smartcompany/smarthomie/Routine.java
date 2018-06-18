package smartcompany.smarthomie;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Routine {
    String name;
    Context context;

    // necesitamos un array temporal de action objects para una rutina que se está creando

    public Routine(String name, Context context){
        this.name = name;
        this.context =context;
    }

    public HashMap<String, Device> getRoutineDevices(){
        HashMap<String, Device> f = new HashMap<>();

        // placeholder @nacho
        return f;
    }

    public void execute(){

    }

    public void delete(){

    }

    public void addAction(){// recibe action object?

    }
}
