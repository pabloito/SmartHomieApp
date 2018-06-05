package smartcompany.smarthomie;

public class Oven extends Device {
    private String state;
    private int temperature;
    private String heat;
    private String convection;
    private String grill;

    public Oven(String name) {
        super(name);
    }

    public Oven(String name, String type) {
        super(name, type);
    }

    public void setConvection(String convection) {
        this.convection = convection;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setGrill(String grill) {
        this.grill = grill;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public void setState(String state) {
        this.state = state;
    }
}
