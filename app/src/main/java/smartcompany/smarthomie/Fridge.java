package smartcompany.smarthomie;

import android.content.Context;
import android.provider.ContactsContract;

public class Fridge extends Device {

    private int freezerTemperature;
    private int refridgeratorTemperature;
    private String mode;
    private Context context;
    private int modeIndex;

    public Fridge(String name) {
        super(name);
    }

    public Fridge(String name, String type, Context context) {
        super(name, type);
        freezerTemperature= -14;
        refridgeratorTemperature=5;
        mode=context.getResources().getStringArray(R.array.fridge_mode_array)[0];
        this.context=context;
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
        String[] array=context.getResources().getStringArray(R.array.fridge_mode_array);
        this.mode=mode;
        if(mode.equals(array[0])){
            modeIndex=0;
        }
        if(mode.equals(array[1])){
            modeIndex=1;
        }
        if(mode.equals(array[2])){
            modeIndex=2;
        }
    }
}
