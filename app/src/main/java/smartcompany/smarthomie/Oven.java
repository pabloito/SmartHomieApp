package smartcompany.smarthomie;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

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
        this.convection=convection;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_convection_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_convection_array);
        this.convection = convection;
        if(convection.equals(array[0]) || convection.equals(array2[0])){
            convectionIndex=0;
        }
        if(convection.equals(array[1]) || convection.equals(array2[1])){
            convectionIndex=1;
        }
        if(convection.equals(array[2]) || convection.equals(array2[2])){
            convectionIndex=2;
        }

        List<Object> param = new LinkedList<>();

        switch(convectionIndex){
            case 0:
                param.add("normal");
                API.SendEventWithParameters(this,"setConvection",param);
                break;
            case 1:
                param.add("eco");
                API.SendEventWithParameters(this,"setConvection",param);
                break;
            case 2:
                param.add("off");
                API.SendEventWithParameters(this,"setConvection",param);
                break;
        }
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        List<Object> param = new LinkedList<>();
        param.add(temperature);
        API.SendEventWithParameters(this,"setTemperature",param);
    }

    public void setGrill(String grill) {
        this.grill=grill;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_grill_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_grill_array);
        this.grill = grill;
        if(grill.equals(array[0]) || grill.equals(array2[0])){
            grillIndex=0;
        }
        if(grill.equals(array[1]) || grill.equals(array2[1])){
            grillIndex=1;
        }
        if(grill.equals(array[2]) || grill.equals(array2[2])){
            grillIndex=2;
        }

        List<Object> param = new LinkedList<>();

        switch(grillIndex) {
            case 0:
                param.add("large");
                API.SendEventWithParameters(this, "setGrill", param);
                break;
            case 1:
                param.add("eco");
                API.SendEventWithParameters(this, "setGrill", param);
                break;
            case 2:
                param.add("off");
                API.SendEventWithParameters(this, "setGrill", param);
                break;
        }
    }

    public void setHeat(String heat) {
        this.heat=heat;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_heat_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_heat_array_L);
        this.heat = heat;
        if(heat.equals(array[0]) || heat.equals(array2[0])){
            heatIndex=0;
        }
        if(heat.equals(array[1])|| heat.equals(array2[1])){
            heatIndex=1;
        }
        if(heat.equals(array[2])|| heat.equals(array2[2])){
            heatIndex=2;
        }

        List<Object> param = new LinkedList<>();

        switch(heatIndex) {
            case 0:
                param.add("conventional");
                API.SendEventWithParameters(this, "setHeat", param);
                break;
            case 1:
                param.add("superior");
                API.SendEventWithParameters(this, "setHeat", param);
                break;
            case 2:
                param.add("inferior");
                API.SendEventWithParameters(this, "setHeat", param);
                break;
        }
    }

    public void setState(String state) {
        this.state=state;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_state_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_state_array_L);
        this.state = state;
        if(state.equals(array[0]) || state.equals(array2[0])){
            API.SendEvent(this,"turnOn");
            stateIndex=0;
        }
        if(state.equals(array[1]) || state.equals(array2[1])){
            API.SendEvent(this,"turnOff");
            stateIndex=1;
        }
    }

    public void setTemperature_Api(int temperature) {
        this.temperature = temperature;
        List<Object> param = new LinkedList<>();
        param.add(temperature);
    }


    public void setGrill_Api(String grill) {
        this.grill=grill;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_grill_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_grill_array);
        this.grill = grill;
        if(grill.equals(array[0]) || grill.equals(array2[0])){
            grillIndex=0;
        }
        if(grill.equals(array[1]) || grill.equals(array2[1])){
            grillIndex=1;
        }
        if(grill.equals(array[2]) || grill.equals(array2[2])){
            grillIndex=2;
        }

    }

    public void setHeat_Api(String heat) {
        this.heat=heat;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_heat_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_heat_array_L);
        this.heat = heat;
        if(heat.equals(array[0]) || heat.equals(array2[0])){
            heatIndex=0;
        }
        if(heat.equals(array[1])|| heat.equals(array2[1])){
            heatIndex=1;
        }
        if(heat.equals(array[2])|| heat.equals(array2[2])){
            heatIndex=2;
        }
    }

    public void setState_Api(String state) {
        this.state=state;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_state_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_state_array_L);
        this.state = state;
        if(state.equals(array[0]) || state.equals(array2[0])){
            stateIndex=0;
        }
        if(state.equals(array[1]) || state.equals(array2[1])){
            stateIndex=1;
        }
    }
    public void setConvection_Api(String convection) {
        this.convection=convection;
        String[] array= API.getContext().getResources().getStringArray(R.array.oven_convection_array);
        String[] array2= API.getContext().getResources().getStringArray(R.array.oven_convection_array);
        this.convection = convection;
        if(convection.equals(array[0]) || convection.equals(array2[0])){
            convectionIndex=0;
        }
        if(convection.equals(array[1]) || convection.equals(array2[1])){
            convectionIndex=1;
        }
        if(convection.equals(array[2]) || convection.equals(array2[2])){
            convectionIndex=2;
        }

    }




    public void updateStatus() {
        API.OvenUpdateState(this);
    }
}
