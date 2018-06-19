package smartcompany.smarthomie;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class Light extends Device {

    private String state;
    private int stateIndex;
    private int brightness;
    private String color;
    private int colorIndex;

    public Light(String id, String name, String meta) {
        super(id, name, DevicesTypes.LAMP.TypeId(), meta);
        stateIndex=0;
        brightness=50;
        colorIndex=0;
    }

    public Light(String id, String name) {
        super(id, name, DevicesTypes.LAMP.TypeId(),null);
        stateIndex=0;
        brightness=50;
        colorIndex=0;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getStateIndex() {
        return stateIndex;
    }

    public String getState() {
        return state;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public String getColor() {
        return color;
    }

    public void setState(String state) {
        this.state=state;
        String[] array= API.getContext().getResources().getStringArray(R.array.lamp_state_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.lamp_state_array_L);

        if(state.equals(array[0])||color.equals(array2[0])){
            API.SendEvent(this,"turnOn");
            this.stateIndex=0;
        }else{
            API.SendEvent(this,"turnOff");
            this.stateIndex=1;
        }

    }

    public void setBrightness(int brightness) {
        this.brightness=brightness;
        List<Object> param = new LinkedList<>();
        param.add(brightness);
        API.SendEventWithParameters(this,"setBrightness",param);
    }

    public void setColor(String color) {
        this.color=color;
        String[] array= API.getContext().getResources().getStringArray(R.array.lamp_color_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.lamp_color_array_L);
        this.color = color;
        if(color.equals(array[0])||color.equals(array2[0])){
            colorIndex=0;
        }
        if(color.equals(array[1])||color.equals(array2[1])){
            colorIndex=1;
        }
        if(color.equals(array[2])||color.equals(array2[2])){
            colorIndex=2;
        }
        if(color.equals(array[3])||color.equals(array2[3])){
            colorIndex=3;
        }

        List<Object> param = new LinkedList<>();

        switch(colorIndex){
            case 0:
                param.add("EFF70A");
                API.SendEventWithParameters(this,"setColor",param);
                break;
            case 1:
                param.add("F70A22");
                API.SendEventWithParameters(this,"setColor",param);
                break;
            case 2:
                param.add("1E07F0");
                API.SendEventWithParameters(this,"setColor",param);
                break;
            case 3:
                param.add("07F04D");
                API.SendEventWithParameters(this,"setColor",param);
                break;
        }
    }

    public void updateStatus(){
        API.LightUpdateState(this);
    }
}
