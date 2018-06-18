package smartcompany.smarthomie;

import android.content.Context;
import android.provider.ContactsContract;

public class Fridge extends Device {

    private int freezerTemperature;
    private int refridgeratorTemperature;
    private String mode;
    private int modeIndex;

    public Fridge(String id, String name, String meta,Context context) {
        super(id , name, DevicesTypes.REFRIGERATOR.TypeId(), meta,context);
        freezerTemperature= -14;
        refridgeratorTemperature=5;
        modeIndex=0;
    }

    public Fridge(String id, String name,Context context) {
        super(id , name, DevicesTypes.REFRIGERATOR.TypeId(), null,context);
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
    }

    public void setRefridgeratorTemperature(int refridgeratorTemperature) {
        this.refridgeratorTemperature = refridgeratorTemperature;
    }
    public void setMode(String mode){
        /*String[] array=context.getResources().getStringArray(R.array.fridge_mode_array);
        this.mode=mode;
        if(mode.equals(array[0])){
            modeIndex=0;
        }
        if(mode.equals(array[1])){
            modeIndex=1;
        }
        if(mode.equals(array[2])){
            modeIndex=2;
        }*/
    }
}
