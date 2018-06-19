package smartcompany.smarthomie;

import android.content.Context;
import android.provider.ContactsContract;

import java.util.LinkedList;
import java.util.List;

public class Fridge extends Device {

    private int freezerTemperature;
    private int refridgeratorTemperature;
    private String mode;
    private int modeIndex;

    public Fridge(String id, String name, String meta) {
        super(id , name, DevicesTypes.REFRIGERATOR.TypeId(), meta);
        freezerTemperature= -14;
        refridgeratorTemperature=5;
        modeIndex=0;
    }

    public Fridge(String id, String name) {
        super(id , name, DevicesTypes.REFRIGERATOR.TypeId(), null);
        freezerTemperature= -14;
        refridgeratorTemperature=5;
        modeIndex=0;
    }

    public int getFreezerTemperature() {
        return freezerTemperature;
    }

    public int getRefridgeratorTemperature() {
        return refridgeratorTemperature;
    }

    public String getMode() {
        return mode;
    }

    public int getModeIndex() {
        return modeIndex;
    }

    public void setFreezerTemperature(int freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
        List<Object> param = new LinkedList<>();
        param.add(freezerTemperature);
        API.SendEventWithParameters(this,"setFreezerTemperature",param);
    }

    public void setRefridgeratorTemperature(int refridgeratorTemperature) {
        this.refridgeratorTemperature = refridgeratorTemperature;
        List<Object> param = new LinkedList<>();
        param.add(refridgeratorTemperature);
        API.SendEventWithParameters(this,"setTemperature",param);
    }

    public void setMode(String mode){
        String[] array= API.getContext().getResources().getStringArray(R.array.fridge_mode_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.fridge_mode_array_L);
        this.mode=mode;
        if(mode.equals(array[0])|| mode.equals(array2[0])){
            modeIndex=0;
        }
        if(mode.equals(array[1])|| mode.equals(array2[1])){
            modeIndex=1;
        }
        if(mode.equals(array[2])|| mode.equals(array2[2])){
            modeIndex=2;
        }


        List<Object> param = new LinkedList<>();

        switch(modeIndex){
            case 0:
                param.add("default");
                API.SendEventWithParameters(this,"setMode",param);
                break;
            case 1:
                param.add("party");
                API.SendEventWithParameters(this,"setMode",param);
                break;
            case 2:
                param.add("vacation");
                API.SendEventWithParameters(this,"setMode",param);
                break;
        }
    }


    public void setRefridgeratorTemperature_Api(int refridgeratorTemperature) {
        this.refridgeratorTemperature = refridgeratorTemperature;
        List<Object> param = new LinkedList<>();
        param.add(refridgeratorTemperature);
    }

    public void setFreezerTemperature_Api(int freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
        List<Object> param = new LinkedList<>();
        param.add(freezerTemperature);
    }

    public void setMode_Api(String mode){
        String[] array= API.getContext().getResources().getStringArray(R.array.fridge_mode_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.fridge_mode_array_L);
        this.mode=mode;
        if(mode.equals(array[0])|| mode.equals(array2[0])){
            modeIndex=0;
        }
        if(mode.equals(array[1])|| mode.equals(array2[1])){
            modeIndex=1;
        }
        if(mode.equals(array[2])|| mode.equals(array2[2])){
            modeIndex=2;
        }
    }


    public void updateStatus() {
        API.FridgeUpdateState(this);
    }
}
