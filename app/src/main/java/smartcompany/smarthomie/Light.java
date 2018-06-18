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

    public Light(String id, String name, String meta, Context context) {
        super(id, name, DevicesTypes.LAMP.TypeId(), meta,context);
        stateIndex=0;
        brightness=50;
        colorIndex=0;
    }

    public Light(String id, String name,Context context) {
        super(id, name, DevicesTypes.LAMP.TypeId(),null,context);
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
        if(state.equals("On")){
            API.SendEvent(this,"turnOn");
        }else{
            API.SendEvent(this,"turnOff");
        }
    }

    public void setBrightness(int brightness) {
        List<Object> param = new LinkedList<>();
        param.add(brightness);
        API.SendEventWithParameters(this,"setBrightness",param);
    }

    public void setColor(String color) {
        String[] array= context.getResources().getStringArray(R.array.lamp_color_array);
        this.color = color;
        if(color.equals(array[0])){
            colorIndex=0;
        }
        if(color.equals(array[1])){
            colorIndex=1;
        }
        if(color.equals(array[2])){
            colorIndex=2;
        }
        if(color.equals(array[3])){
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
}
