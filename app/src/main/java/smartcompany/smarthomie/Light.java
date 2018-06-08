package smartcompany.smarthomie;

import android.content.Context;

public class Light extends Device {

    private String state;
    private int stateIndex;
    private int brightness;
    private String color;
    private int colorIndex;
    private Context context;

    public Light(String name) {
        super(name);
        state="On";
        stateIndex=0;
        brightness=50;
        color="Default";
        colorIndex=0;
    }

    public Light(String name, String type, Context context) {
        super(name, type);
        state=context.getResources().getStringArray(R.array.lamp_state_array)[0];
        stateIndex=0;
        brightness=50;
        color=context.getResources().getStringArray(R.array.lamp_color_array)[0];
        colorIndex=0;
        this.context=context;
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
        String[] array= context.getResources().getStringArray(R.array.lamp_state_array);
        this.state = state;
        if(state.equals(array[0])){
            stateIndex=0;
        }
        else{
            stateIndex=1;
        }
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
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
    }
}
