package smartcompany.smarthomie;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Routine {
    String name;
    String id;
    public List<RoutineAction> actions;

    // necesitamos un array temporal de action objects para una rutina que se está creando

    public Routine(String id, String name, List<RoutineAction> actions){
        this.name = name;
        this.id = id;
        this.actions = actions;
    }

    public HashMap<String, Device> getRoutineDevices(){
        HashMap<String, Device> f = new HashMap<>();

        // placeholder @nacho
        return f;
    }

    public void execute(){
        API.ExecuteRoutine(this);
    }

    public void delete(){
        API.RemoveRoutine(this);
    }

    public void addAction(){// recibe action object?

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
