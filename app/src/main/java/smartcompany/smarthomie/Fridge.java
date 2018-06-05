package smartcompany.smarthomie;

public class Fridge extends Device {

    private int freezerTemperature;
    private int refridgeratorTemperature;
    private String mode;

    public Fridge(String name) {
        super(name);
    }

    public Fridge(String name, String type) {
        super(name, type);
    }

    public void setFreezerTemperature(int freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
    }

    public void setRefridgeratorTemperature(int refridgeratorTemperature) {
        this.refridgeratorTemperature = refridgeratorTemperature;
    }
    public void setMode(String mode){
        this.mode=mode;
    }
}
