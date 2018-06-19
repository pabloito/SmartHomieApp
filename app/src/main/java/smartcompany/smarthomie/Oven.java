package smartcompany.smarthomie;

import android.content.Context;

public class Oven extends Device {
    private String state;
    private int stateIndex;
    private int temperature;
    private String heat;
    private int heatIndex;
    private String convection;
    private int convectionIndex;
    private String grill;
    private int grillIndex;


    public Oven(String id, String name, String meta) {
        super(id, name, DevicesTypes.OVEN.TypeId(), meta);
        stateIndex=0;
        temperature=160;
        heatIndex=0;
        grillIndex=0;
        convectionIndex=0;
    }

    public Oven(String id, String name) {
        super(id, name, DevicesTypes.OVEN.TypeId(), null);
        stateIndex=0;
        temperature=160;
        heatIndex=0;
        grillIndex=0;
        convectionIndex=0;
    }

    public String getState() {
        return state;
    }

    public int getStateIndex() {
        return stateIndex;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHeatIndex() {
        return heatIndex;
    }

    public int getConvectionIndex() {
        return convectionIndex;
    }

    public int getGrillIndex() {
        return grillIndex;
    }

    public String getConvection() {
        return convection;
    }

    public String getGrill() {
        return grill;
    }

    public String getHeat() {
        return heat;
    }

    public void setConvection(String convection) {
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_convection_array);
        this.convection = convection;
        if(convection.equals(array[0])){
            convectionIndex=0;
        }
        if(convection.equals(array[1])){
            convectionIndex=1;
        }
        if(convection.equals(array[2])){
            convectionIndex=2;
        }
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setGrill(String grill) {
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_grill_array);
        this.grill = grill;
        if(grill.equals(array[0])){
            grillIndex=0;
        }
        if(grill.equals(array[1])){
            grillIndex=1;
        }
        if(grill.equals(array[2])){
            grillIndex=2;
        }
    }

    public void setHeat(String heat) {
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_heat_array);
        this.heat = heat;
        if(heat.equals(array[0])){
            heatIndex=0;
        }
        if(heat.equals(array[1])){
            heatIndex=1;
        }
        if(heat.equals(array[2])){
            heatIndex=2;
        }
    }

    public void setState(String state) {
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_state_array);
        this.state = state;
        if(state.equals(array[0])){
            stateIndex=0;
        }
        if(state.equals(array[1])){
            stateIndex=1;
        }
    }

    public void updateStatus() {
        API.OvenUpdateState(this);
    }
}
